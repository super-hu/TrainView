package com.suphu.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.suphu.demo.R;

/**
 * Created by huchao on 2017/8/9.
 * 类似爱奇艺的音量view 正式的还需要去考虑 数据保存 手势 图片微调等
 */

public class VolumeHorizontalView extends View {


    private Bitmap bitmap;

    private int progressHeight;//进度条的高度

    private int progressBgColor;//进度条的背景颜色

    private int progressColor;//进度条的颜色值

    private int currentProgress;//当前的进度

    private int totalProgress;//总进度值

    private Paint mPaint;

    private int offset=15;//图片和进度条与中心位置的偏移量

    public VolumeHorizontalView(Context context) {
        this(context,null);
    }

    public VolumeHorizontalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VolumeHorizontalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.VolumeHorizontalView);
        bitmap= BitmapFactory.decodeResource(getResources(),ta.getResourceId(R.styleable.VolumeHorizontalView_volume_horizontal_bitmap,0));
        progressHeight= (int) ta.getDimension(R.styleable.VolumeHorizontalView_volume_horizontal_height,10);
        progressBgColor=ta.getColor(R.styleable.VolumeHorizontalView_volume_horizontal_bgColor, Color.parseColor("#66000000"));
        progressColor=ta.getColor(R.styleable.VolumeHorizontalView_volume_horizontal_color,Color.RED);
        totalProgress=ta.getInt(R.styleable.VolumeHorizontalView_volume_horizontal_totalProgress,100);

        ta.recycle();

        mPaint=new Paint();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setProgress();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);

        //绘制图片
        canvas.drawBitmap(bitmap,getWidth()/2-bitmap.getWidth()/2-getPaddingLeft(),getHeight()/2-offset-bitmap.getHeight()+getPaddingTop(),mPaint);

        //绘制进度条背景
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(progressBgColor);
        mPaint.setStrokeWidth(progressHeight);
        canvas.drawLine(getPaddingLeft(),getHeight()/2+offset,getWidth()-getPaddingRight(),getHeight()/2+offset,mPaint);


        mPaint.setColor(progressColor);
        //绘制进度条
        int width=getWidth()-getPaddingRight()-getPaddingLeft();

        float itemProgress=(float)width/totalProgress;

        Log.d("draw","getWidth:"+getWidth()+"width:"+width+"=itemProgress=="+itemProgress+"===>"+currentProgress*itemProgress
                +"--->"+(getWidth()-getPaddingRight())+"=====>"+(currentProgress*itemProgress+getPaddingLeft()));

        canvas.drawLine(getPaddingLeft(),getHeight()/2+offset,currentProgress*itemProgress+getPaddingLeft(),getHeight()/2+offset,mPaint);


    }

    private boolean isLoop;

    //测试函数
    public void setProgress(){
        isLoop=true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (isLoop){
                    if(currentProgress<totalProgress){
                        currentProgress++;

//                        Log.d("setProgress","setProgress2:"+currentProgress);
                        postInvalidate();

                        try {
                            Thread.sleep(100);
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
