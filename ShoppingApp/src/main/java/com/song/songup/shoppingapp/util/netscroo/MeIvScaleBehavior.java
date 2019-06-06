package com.song.songup.shoppingapp.util.netscroo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.song.songup.shoppingapp.R;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/9 19:42
 * 修改备注：
 */
public class MeIvScaleBehavior extends CoordinatorLayout.Behavior<ImageView> {
    private int ivMinHeight = 0;
    private int ivMaxHeight = 0;

    private int dispatity = 0;
    private int barHeight =0;

    int startChangePosition = 0;

    public MeIvScaleBehavior() {
    }

    public MeIvScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    //用来监听NetScroll上下滑动(只监听垂直方向滑动)
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ImageView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) !=0;
    }


    @SuppressLint("TimberArgCount")
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ImageView child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (target.getTag(R.id.start_change_pos_id) != null && barHeight == 0){
            startChangePosition = (int) target.getTag(R.id.start_change_pos_id);
            barHeight = (int) target.getTag(R.id.change_in_distance_id);
            ivMaxHeight = child.getHeight();
            ivMinHeight = ivMaxHeight*3/5;
            dispatity = ivMaxHeight - ivMinHeight;

//            int topPos = (int) target.getTag(R.id.ivTopPostion);
//            child.setTop(topPos);


        }else if (target.getTag(R.id.start_change_pos_id) == null){
            return;

        }
        int follow = target.getScrollY() - startChangePosition;
        float scaleY = 1-((float)follow)/barHeight;
        int finalY = (int) (ivMinHeight + dispatity*scaleY);
        if (finalY > ivMaxHeight){
            finalY = ivMaxHeight;
        }
        if (finalY< ivMinHeight){
            finalY = ivMinHeight;
        }

        Log.d("IvScaleBehavor","finalY"+finalY);
        if (target.getHeight() == finalY)
            return;

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        layoutParams.height = finalY;
        layoutParams.width = finalY;
        child.setLayoutParams(layoutParams);

    }
}
