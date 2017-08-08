package com.suphu.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.suphu.demo.R;

/**
 * Created by huchao on 2017/8/8.
 * 系统的textview的drawable的效果
 */

public class TextDrawableView extends View {

    private static final int LEFT=0;
    private static final int RIGHT=1;
    private static final int TOP=2;
    private static final int BOTTOM=3;

    private String textValue;

    private int textColor;

    private int textSize;

    private Bitmap img;

    //图片的位置
    @Postion
    private int postion=BOTTOM;

    @IntDef({LEFT,RIGHT,TOP,BOTTOM})
    public  @interface Postion{
    }

    private Paint mPaint;

    private Rect mTextRect;

    private Rect mImgRect;

    private int spacing=10;//图片和文字的间距

    public TextDrawableView(Context context) {
        this(context,null);
    }

    public TextDrawableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextDrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.TextDrawableView);
        textValue=ta.getString(R.styleable.TextDrawableView_textdrawable_value);

        textSize= (int) ta.getDimension(R.styleable.TextDrawableView_textdrawable_size,15);

        textColor=ta.getColor(R.styleable.TextDrawableView_textdrawable_color, Color.BLACK);

        img= BitmapFactory.decodeResource(getResources(),ta.getResourceId(R.styleable.TextDrawableView_textdrawable_img,0));

        ta.recycle();

        mPaint=new Paint();

        mTextRect=new Rect();

        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
        mPaint.getTextBounds(textValue,0,textValue.length(),mTextRect);

        mImgRect=new Rect();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int width=0;
        int height=0;


        switch (postion){
            case LEFT://横向 宽度=图片+文字+间距   高度=取图片或者文字的高度最大值
            case RIGHT:
                width=img.getWidth()+mTextRect.width()+getPaddingLeft()+getPaddingRight()+spacing;
                height=Math.max(img.getHeight(),mTextRect.height())+getPaddingTop()+getPaddingBottom();
                break;
            case TOP://纵向 宽度=取图片或者文字的宽度最大值  高度=图片+文字+间距
            case BOTTOM:
                width=Math.max(img.getWidth(),mTextRect.width())+getPaddingLeft()+getPaddingRight();
                height=img.getHeight()+mTextRect.height()+getPaddingTop()+getPaddingBottom()+spacing;
                setMeasuredDimension(widthMode==MeasureSpec.EXACTLY?widthSize:width,heightMode==MeasureSpec.EXACTLY?heightSize:height);
                break;
        }
        Log.d("TextDrawable","width:"+width +",height:"+height);

        setMeasuredDimension(widthMode==MeasureSpec.EXACTLY?widthSize:width,heightMode==MeasureSpec.EXACTLY?heightSize:height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        switch (postion){
            case LEFT://先绘制图片左侧后绘制文字
                Log.d("TextDrawable","onDraw====>left");
                mImgRect.left=getPaddingLeft();
                mImgRect.top=getPaddingTop();
                mImgRect.right=getPaddingLeft()+img.getWidth();
                mImgRect.bottom=getPaddingTop()+img.getHeight();

                canvas.drawBitmap(img,null,mImgRect,mPaint);

                canvas.drawText(textValue,0,textValue.length(),mImgRect.right+spacing,
                       img.getHeight()/2+mTextRect.height()/2+getPaddingTop(),mPaint);

                break;
            case RIGHT:
                Log.d("TextDrawable","onDraw====>right");
                mImgRect.left=getPaddingLeft()+mTextRect.width()+spacing;
                mImgRect.top=getPaddingTop();
                mImgRect.right=getPaddingLeft()+img.getWidth()+mTextRect.width();
                mImgRect.bottom=getPaddingTop()+img.getHeight();

                canvas.drawBitmap(img,null,mImgRect,mPaint);

                canvas.drawText(textValue,0,textValue.length(),getPaddingLeft(),
                        img.getHeight()/2+mTextRect.height()/2+getPaddingTop(),mPaint);

                break;
            case TOP:
                Log.d("TextDrawable","onDraw====>top");
                if(img.getWidth()<mTextRect.width()){
                    mImgRect.left=getPaddingLeft()+mTextRect.width()/2-img.getWidth()/2;
                    mImgRect.right=mImgRect.left+img.getWidth();

                    canvas.drawText(textValue,0,textValue.length(),getPaddingLeft(),
                            getPaddingTop()+mTextRect.height(),mPaint);
                }else{
                    mImgRect.left=getPaddingLeft();
                    mImgRect.right=getPaddingLeft()+img.getWidth();

                    canvas.drawText(textValue,0,textValue.length(),getPaddingLeft()+img.getWidth()/2-mTextRect.width()/2,
                            getPaddingTop()+mTextRect.height(),mPaint);
                }

                mImgRect.top=getPaddingTop();
                mImgRect.bottom=getPaddingTop()+img.getHeight();
                canvas.drawBitmap(img,null,mImgRect,mPaint);
                break;
            case BOTTOM:
                Log.d("TextDrawable","onDraw====>bottom");
                //判断图片和文字的宽度  来达到图片和文字居中绘制效果
                if(img.getWidth()<mTextRect.width()){
                    mImgRect.left=getPaddingLeft()+mTextRect.width()/2-img.getWidth()/2;
                    mImgRect.right=mImgRect.left+img.getWidth();

                    canvas.drawText(textValue,0,textValue.length(),getPaddingLeft(),
                            getPaddingTop()+mTextRect.height(),mPaint);
                }else{
                    mImgRect.left=getPaddingLeft();
                    mImgRect.right=getPaddingLeft()+img.getWidth();

                    canvas.drawText(textValue,0,textValue.length(),getPaddingLeft()+img.getWidth()/2-mTextRect.width()/2,
                            getPaddingTop()+mTextRect.height(),mPaint);
                }
                mImgRect.top=getPaddingTop()+mTextRect.height();
                mImgRect.bottom=getPaddingTop()+mTextRect.height()+img.getHeight()+spacing;
                canvas.drawBitmap(img,null,mImgRect,mPaint);
                break;
        }


    }


    public void setPostion(@Postion int postion){

        this.postion=postion;

        requestLayout();

    }
}
