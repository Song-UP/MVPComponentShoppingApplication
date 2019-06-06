package com.song.songup.shoppingapp.util.netscroo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/9 20:30
 * 修改备注：
 */
public class MeRCBehivion extends CoordinatorLayout.Behavior<NestedScrollView> {
    public MeRCBehivion() {
    }

    public MeRCBehivion(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, NestedScrollView child, View dependency) {
        return dependency instanceof ImageView;
    }



}
