package com.song.songup.shoppingapp.mvp.ui.weight;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/17 18:46
 * 修改备注：
 */
public class SwipeVerticalRefreshLayout extends SwipeRefreshLayout {
    private float startX;
    private float startY;
    private boolean mIsXMove;// 是否横向拖拽
    private final int mTouchSlop;// getScaledTouchSlop()得来的一个距离，表示滑动的时候，手势移动要大于这个距离才开始移动控件，ViewPager就是用这个距离来判断用户是否翻页

    public SwipeVerticalRefreshLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mIsXMove = false;
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果横向移动则不拦截，直接return false；
                if (mIsXMove) {
                    return false;
                }
                float endX = ev.getX();
                float endY = ev.getY();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果dx>xy，则认定为左右滑动，将事件交给viewPager处理，return false
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsXMove= true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsXMove= false;
                break;
        }
        // 如果dy>dx，则认定为下拉事件，交给swipeRefreshLayout处理并拦截
        return super.onInterceptTouchEvent(ev);
    }
}
