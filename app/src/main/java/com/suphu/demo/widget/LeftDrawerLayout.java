package com.suphu.demo.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huchao on 2017/8/10.
 * 核心是利用 onViewReleased后 settleCapturedViewAt确定view的位置(内部用了Scroller的startScroll去使view移动) 并调用invalidate刷新
 */

public class LeftDrawerLayout extends ViewGroup {


    private static final int MIN_DRAWER_MARGIN = 64; // dp

    /**
     * 菜单栏展开后距离右边的距离
     */
    private int mMinDrawerMargin;


    private static final String TAG="LeftDrawerLayout";


    private ViewDragHelper mDragHelper;

    //左侧的菜单 默认第一个view
    private View leftMenuView;

    //内容view 默认第0 个view
    private View contentView;

    //菜单显示出来的占自身宽度的百分比
    private float mLeftMenuOnScreen;


    public LeftDrawerLayout(Context context) {
        this(context,null);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LeftDrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final float density = getResources().getDisplayMetrics().density;
        mMinDrawerMargin = (int) (MIN_DRAWER_MARGIN * density + 0.5f);

        mDragHelper=ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child==leftMenuView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //left 横向移动距离  0~-child宽度之间
//                Log.d(TAG,"left:"+left);
//
//                Log.d(TAG,"child.getWidth:"+child.getWidth());
//
//                Log.d(TAG,"clampViewPositionHorizontal:"+Math.max(-child.getWidth(),Math.min(left,0)));

                return Math.max(-child.getWidth(),Math.min(left,0));
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                //在边界也可以操作菜单 否则当菜单栏不可见时候，会打开不了
                mDragHelper.captureChildView(leftMenuView,pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //释放view后判断偏移量 child是显示还是隐藏

                int childWidth=releasedChild.getWidth();
                //拖动view所滑的比例 width+left / width  其实就是菜单栏划出的宽度比例？
                float offsetF= childWidth+releasedChild.getLeft()*1.0f/childWidth;

                Log.d(TAG,"childWidth:"+childWidth+",getLeft():"+releasedChild.getLeft()+",offsetF:"+offsetF+"，xvel："+xvel);

                //xvel>=0 划出的动作
                mDragHelper.settleCapturedViewAt(xvel>0||xvel==0&&offsetF>0.5f ? 0 : -childWidth,releasedChild.getTop());
                //内部用了startScroll 需要重写computeScroll来触发刷新
                invalidate();
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

                int childWidth=changedView.getWidth();

                //划出占比
                float offset=(float) (childWidth+left)/childWidth;

                mLeftMenuOnScreen=offset;

//                Log.d(TAG,"positionChange,offset:"+offset+",left:"+left+",getLeft:"+changedView.getLeft());

                changedView.setVisibility(offset==0?INVISIBLE:VISIBLE);

                invalidate();

            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return leftMenuView==child?child.getWidth():0;
            }
        });
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
//        mDragHelper.setMinVelocity(minVel);

    }


    @Override
    public void computeScroll() {
        if(mDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        leftMenuView=getChildAt(1);
        contentView=getChildAt(0);
    }

    public void openDrawer()
    {
        View menuView = leftMenuView;
        mLeftMenuOnScreen = 1.0f;
        mDragHelper.smoothSlideViewTo(menuView, 0, menuView.getTop());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int widthSize=MeasureSpec.getSize(widthMeasureSpec);

        int heightSize=MeasureSpec.getSize(heightMeasureSpec);


//        int count = getChildCount();
//        for(int i=0;i<count;i++){
//            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
//        }

        //菜单栏的宽度不能完全覆盖 需要重新计算
        View mleftMenuView = getChildAt(1);
        MarginLayoutParams lp = (MarginLayoutParams)
                leftMenuView.getLayoutParams();

        Log.d(TAG,"onMeasure:"+lp.width);

        final int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                mMinDrawerMargin + lp.leftMargin + lp.rightMargin,
                lp.width);
        final int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                lp.topMargin + lp.bottomMargin,
                lp.height);
        leftMenuView.measure(drawerWidthSpec, drawerHeightSpec);


        View mcontentView = getChildAt(0);
        lp = (MarginLayoutParams) contentView.getLayoutParams();

        final int contentWidthSpec = MeasureSpec.makeMeasureSpec(
                widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
        final int contentHeightSpec = MeasureSpec.makeMeasureSpec(
                heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);

        contentView.measure(contentWidthSpec, contentHeightSpec);

        leftMenuView = mleftMenuView;
        contentView = mcontentView;



        setMeasuredDimension(widthSize,heightSize);

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        Log.d(TAG,"onLayout:");

        //
        MarginLayoutParams lp= (MarginLayoutParams) contentView.getLayoutParams();
        contentView.layout(lp.leftMargin,lp.topMargin,lp.leftMargin+contentView.getMeasuredWidth(),lp.topMargin+contentView.getMeasuredHeight());


        lp= (MarginLayoutParams) leftMenuView.getLayoutParams();

        int childLeft=-leftMenuView.getMeasuredWidth()+lp.leftMargin/*+(int)(leftMenuView.getMeasuredWidth()*mLeftMenuOnScreen)*/;
        leftMenuView.layout(childLeft,lp.topMargin,childLeft+leftMenuView.getMeasuredWidth(),lp.topMargin+leftMenuView.getMeasuredHeight());

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
