package com.song.songup.shoppingapp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.ui.fragment.base.LazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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
public class MainShoppingCarFragment extends LazyFragment {

    @BindView(R.id.text)
    TextView text;
    Unbinder unbinder;

    public static MainShoppingCarFragment newInstance(int args) {
        MainShoppingCarFragment fragment = new MainShoppingCarFragment();
        Bundle build = new Bundle();
        build.putInt("1", args);
        fragment.setArguments(build);
        return fragment;
    }

    int args = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = getArguments().getInt("1", 0);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_shoppingcar, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //onCreateActivity()之后执行
        isPrepare = true;
        text.setText(""+args);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepare || !isVisible) {
            return;
        }
        //填充各控件的数据

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
