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

    //初始化弧度位置 默认从270度（时钟12点位置绘制）
    private float mStartAngle = 270;

    //单前View的宽度
    private int width;
    //单前View的高度
    private int height;

    public void setLists(ArrayList<PieModel> lists) {
        this.lists = lists;
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
        //因为要绘制饼状图是个圆，View的宽度和高度需要一致
        //取最大的作为半径
        int radius=Math.max(width,height) /2 ;

        //设置圆弧的矩形以单前View的整个位置
        mRectF.set(0,0,radius*2,radius*2);
        for(PieModel pieModel:lists){
            mPaint.setColor(pieModel.getColor());
            float currentProgress=pieModel.getProgress();
            canvas.drawArc(mRectF,mStartAngle,currentProgress,true,mPaint);
            mStartAngle+=currentProgress;
        }
    }


}
