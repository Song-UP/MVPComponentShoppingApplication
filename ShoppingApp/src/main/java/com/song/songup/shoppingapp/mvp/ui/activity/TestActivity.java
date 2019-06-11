package com.song.songup.shoppingapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.song.songup.shoppingapp.R;

import butterknife.ButterKnife;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/6/3 13:45
 * 修改备注：
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_beisai);
        ButterKnife.bind(this);
    }

//    @OnClick(R.id.iv1)
//    public void onViewClicked() {
//        ToastUtils.showShort("点击了第一张图片");
//    }
}
