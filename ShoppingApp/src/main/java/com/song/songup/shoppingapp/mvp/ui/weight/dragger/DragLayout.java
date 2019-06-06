package com.song.songup.shoppingapp.mvp.ui.weight.dragger;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.song.songup.shoppingapp.R;

import me.jessyan.armscomponent.commonres.weight.BaseFramelayout;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/6/3 13:00
 * 修改备注：
 */
public class DragLayout extends BaseFramelayout {
    private ImageView iv1;
    private ImageView iv2;

//    可以用来 平移，缩放，旋转（围绕某个轴）
    ViewDragHelper viewDragHelper;
    ViewDragHelper.Callback callback;
    int width = 0;

    private Point oriPositon;
    private int finalXLeft;
    private int finalXRight;

    public DragLayout(Context context) {
        super(context);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //决定是否拦截当前事件
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //处理事件
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return onInterceptHoverEvent(event);
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        return super.onHoverEvent(event);
    }

    @Override
    protected void onFinishInflate() {
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        oriPositon = new Point();
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.oriPositon.x = iv1.getLeft();
        this.oriPositon.y = iv1.getRight();
        this.finalXLeft = getPaddingLeft();
        this.finalXRight = this.getRight()-getPaddingRight()- iv1.getWidth();

        width = this.getMeasuredWidth();
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    protected void initView(Context context) {
        callback = new DraggerCallBack();
        viewDragHelper = ViewDragHelper.create(this,1f,callback);
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    @Override
    protected void load(Context context) {

    }



    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    /**************** DraggerCallBack ****************/
//    补充说明：一旦移动（所有）view 的 tryCatureViewtrue的true,都会被移动

    class DraggerCallBack extends ViewDragHelper.Callback{
        //这个方法返回true,代表可以滑动，返回false代表不能滑动
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            if (view == iv1){
                return true;
            }
            return false;
        }


        /**
         * @param child  :触摸需要移动的view
         * @param left:将要移动的位置
         * @param dx
         * @return
         */
        int leftBound = 0;
        int rightBound = 0;
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if (touchView != iv1)
                return child.getLeft();

            if (leftBound == 0)
                leftBound = getPaddingLeft();
            if (rightBound == 0)
                rightBound = getWidth() - child.getWidth() -  getPaddingRight();
            //设置图片移动的位置在父元素内
            return Math.min(Math.max(leftBound,left),rightBound);
        }

        int topBound = 0;
        int bottomBound = 0;
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (touchView != iv1)
                return child.getTop();

            if (topBound == 0)
                topBound = getPaddingTop();
            if (bottomBound == 0)
                bottomBound = getHeight() - child.getHeight() -  getPaddingBottom();
            return Math.min(Math.max(topBound,top),bottomBound);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
//            super.onViewReleased(releasedChild, xvel, yvel);
            if (releasedChild == iv1){
//                viewDragHelper.settleCapturedViewAt(oriPositon.x,oriPositon.y);
                if (releasedChild.getLeft()<(width-releasedChild.getWidth())/2){
                    xvel=finalXLeft;
                }else {
                    xvel=finalXRight;
                }
                viewDragHelper.settleCapturedViewAt((int) xvel, releasedChild.getTop());
                invalidate();
            }
        }


//        horizontalDragRange 和verticalDragRange
//        大于0的时候 对应的move事件才会捕获。否则就是丢弃直接丢给子view自己处理了
//        子view滑动,必然走这两个方法，其他的就不一定走了
        View touchView;
        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            touchView = child;
            if (child == iv2)
                return -1;
            return getMeasuredWidth() - iv1.getWidth();
        }


        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            touchView = child;
            return getMeasuredHeight() - iv1.getHeight();
        }



        /**
         * 边缘时释放
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            viewDragHelper.captureChildView(iv1,pointerId);
        }
    }

}



