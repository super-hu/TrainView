package com.suphu.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.suphu.demo.R;

/**
 * Created by huchao on 2017/8/8.
 * 简单的圆形进度条 文字显示进度
 */

public class CircleProgressView extends View{


    private int textColor;

    private int textSize;

    private int progress;//当前的进度

    private int progressColor;

    private int progressBgColor;

    private Paint mPaint;

    private Rect mTextRect;

    private RectF mRectF;

    private int progressWidth;//进度宽度

    private int maxProgress;


    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        textSize= (int) ta.getDimension(R.styleable.CircleProgressView_progress_text_size,13);
        textColor=ta.getColor(R.styleable.CircleProgressView_progress_text_color,Color.BLACK);
        progressBgColor=ta.getColor(R.styleable.CircleProgressView_progress_bg_colcor, Color.parseColor("#66CCCCCC"));
        progressColor=ta.getColor(R.styleable.CircleProgressView_progress_color,Color.RED);
        progress=ta.getInt(R.styleable.CircleProgressView_progress,0);
        progressWidth= (int) ta.getDimension(R.styleable.CircleProgressView_progress_width,5);
        maxProgress=ta.getInt(R.styleable.CircleProgressView_max_progress,100);
        ta.recycle();

        mPaint=new Paint();
        mPaint.setTextSize(textSize);
        mTextRect=new Rect();
        mRectF=new RectF();


        setProgress();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制背景圆弧
        mPaint.setColor(progressBgColor);
        mPaint.setStrokeWidth(progressWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        //圆心坐标
        int centre=getWidth()/2;
        //半径
        int radius=centre-progressWidth/2;

        mRectF.left=centre-radius;
        mRectF.top=centre-radius;
        mRectF.bottom=centre+radius;
        mRectF.right=centre+radius;
        canvas.drawArc(mRectF,-90,360,false,mPaint);

        //绘制进度圆
        mPaint.setColor(progressColor);
        canvas.drawArc(mRectF,-90,progress,false,mPaint);


        //绘制进度文字
        mPaint.setColor(textColor);

        mPaint.setStyle(Paint.Style.FILL);
        //按照100%的比例来显示
        String text=progress*maxProgress/360+"%";
        mPaint.getTextBounds(text,0,text.length(),mTextRect);

        canvas.drawText(text,0,text.length(),centre-mTextRect.width()/2,centre+mTextRect.height()/2,mPaint);

        Log.d("ondraw","draw");

    }


    private boolean isLoop;

    //测试函数
    public void setProgress(){
        isLoop=true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (isLoop){
                    if(progress<360){
                        progress++;

                        Log.d("setProgress","setProgress2:"+progress);
                        postInvalidate();

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        isLoop=false;
                    }
                }

            }
        }).start();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isLoop=false;
    }
}


