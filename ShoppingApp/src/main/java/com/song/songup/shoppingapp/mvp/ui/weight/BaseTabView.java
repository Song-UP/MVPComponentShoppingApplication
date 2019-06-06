package com.song.songup.shoppingapp.mvp.ui.weight;

import android.content.Context;
import android.util.AttributeSet;

import me.jessyan.armscomponent.commonres.weight.BaseFramelayout;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/15 19:49
 * 修改备注：
 */
public abstract class BaseTabView extends BaseFramelayout {
    public BaseTabView(Context context) {
        super(context);
    }

    public BaseTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void change(boolean isSelect);



}
