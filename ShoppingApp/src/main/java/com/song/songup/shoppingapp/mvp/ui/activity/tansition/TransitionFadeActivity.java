package com.song.songup.shoppingapp.mvp.ui.activity.tansition;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/6/12 13:44
 * 修改备注：
 */
public class TransitionFadeActivity extends AppCompatActivity {

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void start(Activity context, View view){
//        context.startActivity(new Intent(context,TransitionFadeActivity.class),
//                ActivityOptions.makeSceneTransitionAnimation(context).toBundle());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions.makeSceneTransitionAnimation(context,view, "");

        }


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
