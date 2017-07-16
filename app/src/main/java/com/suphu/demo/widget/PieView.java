package com.suphu.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.suphu.demo.model.PieModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-7-16.
 */

public class PieView extends View {

    private Paint mPaint;

    private RectF mRectF;

    //数据源
    private ArrayList<PieModel> lists;

    //初始化弧度位置 270（时钟12点开始）
    private float mStartAngle = 270;

    //单前View的宽度
    private int width;
    //单前View的高度
    private int height;

    public void setLists(float mStartAngle,ArrayList<PieModel> lists) {
        if(lists==null || lists.size()==0){
            return;
        }
        this.mStartAngle=mStartAngle;
        //处理数据 把进度转百分比
        int sumProgress=0;
        for(PieModel pieModel:lists){
            sumProgress+=pieModel.getProgress();
        }
        for(PieModel pieModel:lists){
            pieModel.setPercentage(pieModel.getProgress()/sumProgress * 360);
        }
        this.lists = lists;
        invalidate();
    }

    public PieView(Context context) {
        this(context,null);
    }

    public PieView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mRectF=new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width=w;
        this.height=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(lists==null || lists.size()==0){
            return;
        }
        //内部圆的宽度的高度
        int arcWidth=width-getPaddingLeft()-getPaddingRight();
        int arcHeight=height-getPaddingTop()-getPaddingBottom();
        //因为要绘制饼状图是个圆，View的宽度和高度需要一致 - padding值
        //取最大的作为半径
        int radius=Math.max(arcWidth,arcHeight) /2 ;

        //设置圆弧的矩形以单前View的整个位置-padding
        mRectF.set(getPaddingLeft(),getPaddingTop(),radius*2+getPaddingLeft(),radius*2+getPaddingTop());

        for(PieModel pieModel:lists){
            mPaint.setColor(pieModel.getColor());
            float currentPercentage=pieModel.getPercentage();
            canvas.drawArc(mRectF,mStartAngle,currentPercentage,true,mPaint);
            //绘制后起始角度+
            mStartAngle+=currentPercentage;
        }
    }


}
