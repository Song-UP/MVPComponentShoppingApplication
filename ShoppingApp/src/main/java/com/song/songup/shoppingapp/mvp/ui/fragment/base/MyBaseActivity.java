package com.song.songup.shoppingapp.mvp.ui.fragment.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/1 13:46
 * 修改备注：
 */
public abstract class MyBaseActivity<P extends IPresenter> extends BaseActivity<P> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersive();
    }


    public void setImmersive(){

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setBackgroundDrawable(null);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    @Override
    public boolean useEventBus() {
        return false;
    }
}
