package com.suphu.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.suphu.demo.R;

/**
 * Created by huchao on 2017/8/9.
 * 在绘制图片时候 计算方式：https://github.com/hcadoid/CustomView/blob/master/img/VolumeView.png
 * 一个音量的view
 */

public class VolumeView extends View{


    private int volumeWidth;//音量的宽度 即 绘制圆弧的描边宽度值

    private int volumeCount;//音量的个数

    private int bgVolumeColor;//背景块的颜色

    private int volumeColor;//音量扫过的颜色值

    private int spacing;//间隙

    private Bitmap bitmap;//中间位置插入的图片

    private Paint mPaint;

    private RectF mImgRect;

    private RectF mRect;

    private int startArc=135;//起始绘制的角度

    private int totalArc=270;//总共绘制多少度

    private int currentVolume=3;//当前的音量进度

    public VolumeView(Context context) {
        this(context,null);
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.VolumeView);
        volumeWidth= (int) ta.getDimension(R.styleable.VolumeView_volume_width,10);
        volumeCount= (int) ta.getDimension(R.styleable.VolumeView_volume_count,12);
        spacing= (int) ta.getDimension(R.styleable.VolumeView_volume_spacing,10);
        bgVolumeColor=ta.getColor(R.styleable.VolumeView_volume_bg_color,Color.parseColor("#66000000"));
        volumeColor=ta.getColor(R.styleable.VolumeView_volume_color,Color.RED);
        bitmap= BitmapFactory.decodeResource(getResources(),ta.getResourceId(R.styleable.VolumeView_volume_bitmap,0));
        ta.recycle();

        mPaint=new Paint();
        mImgRect= new RectF();

        mRect=new RectF();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //绘制音量圆弧

        //1.外层圆弧
        mPaint.setAntiAlias(true);
        mPaint.setColor(bgVolumeColor);
        mPaint.setStrokeWidth(volumeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        int center=getWidth()/2;
        int radius=center-volumeWidth/2;
        mRect.set(center-radius,center-radius,center+radius,center+radius);

        //计算每个块所占的大小
        int itemSize=0;
        if(totalArc<360){//不是闭环的话，最后个间隙的个数比块的个数少1
            itemSize=(totalArc-spacing*(volumeCount-1))/volumeCount;
        }else{
            itemSize=(totalArc-spacing*volumeCount)/volumeCount;
        }

        for(int i=0;i<volumeCount;i++){
            canvas.drawArc(mRect,i * (itemSize + spacing)+startArc,itemSize,false,mPaint);
        }

        //2.绘制音量进度
        mPaint.setColor(volumeColor);
        for(int i=0;i<currentVolume;i++){
            canvas.drawArc(mRect,i * (itemSize + spacing)+startArc,itemSize,false,mPaint);
        }

        //3.绘制图片  判读图片的宽度和高度是否大于内切正方形的边长 todo 这里只判读了的宽度 严格上需要高度
        //内圆的半径
        int innerRadius=radius-volumeWidth/2;

        if(bitmap.getWidth()> (Math.sqrt(2)*innerRadius)){
            mImgRect.left= (float) (volumeWidth+innerRadius-Math.sqrt(2)/2*innerRadius);
            mImgRect.top=(float) (volumeWidth+innerRadius-Math.sqrt(2)/2*innerRadius);
            mImgRect.right= (float) (mImgRect.left+Math.sqrt(2)*innerRadius);
            mImgRect.bottom=(float) (mImgRect.top+Math.sqrt(2)*innerRadius);
            //图片最大不能超过内切的宽度
            canvas.drawBitmap(bitmap,null,mImgRect,mPaint);
        }else{
            //中心绘制图片
            canvas.drawBitmap(bitmap,getWidth()/2-bitmap.getWidth()/2,getHeight()/2-bitmap.getHeight()/2,mPaint);
        }



    }

    public void setCurrentVolume(int currentVolume){
        this.currentVolume=currentVolume;
        invalidate();
    }




}
