package com.song.songup.shoppingapp.util.netscroo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.ui.weight.NavigateBarView;
import com.song.songup.shoppingapp.util.ConvertUtils;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/9 19:42
 * 修改备注：
 */
public class MeNavigateBehavior extends CoordinatorLayout.Behavior<NavigateBarView> {
    private View linearLayout;

    private int barHeight = 0;

    private int startChangePosition = ConvertUtils.dp2px(70);

    public MeNavigateBehavior() {
    }

    public MeNavigateBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    //用来监听NetScroll上下滑动(只监听垂直方向滑动)
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NavigateBarView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) !=0;
    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NavigateBarView child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        changeView(child,target);


    }

    public void changeView(NavigateBarView child, View target){
        if (target.getTag(R.id.change_in_distance_id) == null){
            barHeight = child.getHeight();
            target.setTag(R.id.start_change_pos_id,startChangePosition);
            target.setTag(R.id.change_in_distance_id,barHeight);
//            int topPos = child.findViewById(R.id.ivIcon).getLayoutParams();
//            target.setTag(R.id.ivTopPostion, topPos);
        }

        int follow = target.getScrollY() - startChangePosition;

        float scaleY = ((float) follow)/barHeight;


        if (scaleY > 1){
            scaleY = 1;
        }
        if (scaleY<0){
            scaleY = 0;
        }

        if (child.getAlpha() == scaleY)
            return;
        child.setAlpha(scaleY);
    }
}
