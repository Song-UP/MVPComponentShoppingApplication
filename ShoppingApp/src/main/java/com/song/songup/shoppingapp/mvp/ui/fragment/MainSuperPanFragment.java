package com.song.songup.shoppingapp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.ui.fragment.base.LazyFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/29/2019 15:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainSuperPanFragment extends LazyFragment {

    public static MainSuperPanFragment newInstance() {
        MainSuperPanFragment fragment = new MainSuperPanFragment();
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_superspan, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //onCreateActivity()之后执行
        isPrepare = true;

    }

    @Override
    protected void lazyLoad() {
        if(!isPrepare || !isVisible) {
            return;
        }
        //填充各控件的数据

    }
}
