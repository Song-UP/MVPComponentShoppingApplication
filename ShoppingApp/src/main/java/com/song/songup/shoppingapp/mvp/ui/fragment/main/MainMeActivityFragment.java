package com.song.songup.shoppingapp.mvp.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.di.component.main.DaggerMainMeActivityComponent;
import com.song.songup.shoppingapp.mvp.contract.main.MainMeActivityContract;
import com.song.songup.shoppingapp.mvp.presenter.main.MainMeActivityPresenter;
import com.song.songup.shoppingapp.mvp.ui.activity.TestActivity;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonsdk.utils.AppUtils;
import me.jessyan.armscomponent.commonsdk.utils.ToastUtils;

import static com.example.sonic.BrowserActivity.start;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/08/2019 20:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainMeActivityFragment extends BaseFragment<MainMeActivityPresenter> implements MainMeActivityContract.View {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.icon_head)
    ImageView iconHead;
    @BindView(R.id.bt_version)
    Button btVersion;
    Unbinder unbinder;
    Unbinder unbinder1;

    public static MainMeActivityFragment newInstance() {
        MainMeActivityFragment fragment = new MainMeActivityFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainMeActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_me, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        btVersion.setText(AppUtils.getAppVersionName());

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    @OnClick({R.id.bt_version, R.id.icon_head,R.id.mainmeitemview_check,R.id.mainmeitemview_myview,
                R.id.webiew})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_version:
                toast();
                break;
            case R.id.icon_head:
                break;
            case R.id.webiew:
                start(getActivity(),"https://www.bilibili.com/");
                break;
            case R.id.mainmeitemview_myview:
                startActivity(new Intent(getActivity(),TestActivity.class));
                break;
            case R.id.mainmeitemview_check:
                Beta.checkUpgrade(true, false);
                break;
        }
    }

    public void toast(){
        ToastUtils.showLong("热修改之前");
    }
}
