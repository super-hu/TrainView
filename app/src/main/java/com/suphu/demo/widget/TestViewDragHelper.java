package com.suphu.demo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by huchao on 2017/8/9.
 */

public class TestViewDragHelper extends LinearLayout{

    private static final String TAG="TestViewDragHelper";

    private ViewDragHelper viewDragHelper;

    private View view1;

    private View view2;


    public TestViewDragHelper(Context context) {
        this(context,null);
    }

    public TestViewDragHelper(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestViewDragHelper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewDragHelper=ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.d(TAG,"tryCaptureView,是否可以拖拽的View:,pointerId:"+pointerId);
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.d(TAG,"clampViewPositionHorizontal是否可以《水平》移动的View,left:"+left+",dx:"+dx);
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.d(TAG,"clampViewPositionVertical是否可以《垂直》移动的View,top:"+top+",dx:"+dy);
                return top;
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
                //点击拖动时state=1  松开停止=0  ps：如果View被禁止，则不会触发
                Log.d(TAG,"onViewDragStateChanged状态变化,state:"+state);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                //对clampViewPositionVertical及Horizontal  变化的补充
                Log.d(TAG,"onViewPositionChanged状位置变化,left:"+left+"，top:"+top);
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                //view被拖动结算时调用
                Log.d(TAG,"onViewCaptured,activePointerId:"+activePointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                //View释放时调用
                Log.d(TAG,"onViewReleased,速度Xvel:"+xvel+",速度Yvel:"+yvel);
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
                //当view被禁止拖动后，且调用setEdgeTrackingEnabled后 touch边界时会触发
                Log.d(TAG,"onEdgeTouched,edgeFlags:"+edgeFlags+",pointerId:"+pointerId);
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                //同上，紧接着上面函数后被触发  此时不会使得View移动，需要 captureChildView方法把view和pointerId传入
                Log.d(TAG,"onEdgeDragStarted,edgeFlags:"+edgeFlags+",pointerId:"+pointerId);

                viewDragHelper.captureChildView(view2, pointerId);
            }

            @Override
            public boolean onEdgeLock(int edgeFlags) {
                Log.d(TAG,"onEdgeLock:"+edgeFlags);
                return super.onEdgeLock(edgeFlags);
            }

            @Override
            public int getOrderedChildIndex(int index) {
                Log.d(TAG,"getOrderedChildIndex,index:"+index);
                return super.getOrderedChildIndex(index);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                //大于0 才能被正常捕获 当子view有点击事件 需要返回>0的
                Log.d(TAG,"getViewHorizontalDragRange:"+super.getViewHorizontalDragRange(child));
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return super.getViewVerticalDragRange(child);
            }
        });

        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view1=getChildAt(0);

        view2=getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG,"onInterceptTouchEvent拦截");
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
//        Log.d(TAG,"onTouchEvent消费");
        return true;
    }
}
