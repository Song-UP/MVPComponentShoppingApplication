package com.song.songup.shoppingapp.mvp.ui.activity.animal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.jess.arms.di.component.AppComponent;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.ui.fragment.base.MyBaseActivity;

import butterknife.BindView;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/6/8 13:45
 * 修改备注：
 */
public class SplashActivity extends MyBaseActivity {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAnimal();
        ivIcon.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (animatorSet != null)
                    animatorSet.start();
            }
        }, 300);

    }


    AnimatorSet animatorSet;

    public void initAnimal() {
        ObjectAnimator alphaAnimal = ObjectAnimator.ofFloat(ivIcon, "alpha", 0f, 1f);
        ObjectAnimator scaleXAnimal = ObjectAnimator.ofFloat(ivIcon, "scaleX", 1f, 3f);
        ObjectAnimator scaleYAnimal = ObjectAnimator.ofFloat(ivIcon, "scaleY", 1f, 3f);

        animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnimal).with(scaleXAnimal).with(scaleYAnimal);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        animatorSet.setDuration(3000);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }).start();
            }
        });


    }

    @Override
    protected void onDestroy() {
        if (animatorSet != null && animatorSet.isRunning())
            animatorSet.cancel();

        super.onDestroy();

    }

}
