package com.suphu.demo.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.suphu.demo.R;

import java.util.Random;

/**
 * Created by huchao on 2017/8/7.
 */

public class MyTestView extends View implements View.OnClickListener{


    private int textSize;

    private String text;

    private int textColor;

    private Paint mPaint;

    private Rect rect;

    public MyTestView(Context context) {
        this(context,null);
    }

    public MyTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray td=context.obtainStyledAttributes(attrs, R.styleable.MyTestView);
        textSize= (int) td.getDimension(R.styleable.MyTestView_text_size,14);
        text=td.getString(R.styleable.MyTestView_text_value);
        textColor=td.getColor(R.styleable.MyTestView_text_color, Color.BLACK);
        td.recycle();

        mPaint=new Paint();

        rect=new Rect();
        mPaint.setTextSize(textSize);
        mPaint.getTextBounds(text,0,text.length(),rect);

        setOnClickListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);

        mPaint.setColor(textColor);
        mPaint.setAntiAlias(true);

        canvas.drawText(text,getWidth()/2-rect.width()/2,getHeight()/2+rect.height()/2,mPaint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int widthMode= MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int width=0;
        int height=0;

        if (widthMode==MeasureSpec.AT_MOST){
            width=rect.width()+getPaddingLeft()+getPaddingRight();
        }else{
            width=widthSize;
        }

        if(heightMode==MeasureSpec.AT_MOST){
            height=rect.height()+getPaddingTop()+getPaddingBottom();
        }else{
            height=heightSize;
        }
        Log.d("MyTestView","width:"+width);
        setMeasuredDimension(width,height);

    }


    @Override
    public void onClick(View view) {
        Random random= new Random();


//        Toast.makeText(getContext(),"click"+random.nextInt(),Toast.LENGTH_SHORT).show();


        text=String.valueOf(Math.abs(random.nextInt()));
        mPaint.getTextBounds(text,0,text.length(),rect);
//        postInvalidate();
        requestLayout();
    }
}
