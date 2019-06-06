package com.song.songup.shoppingapp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.di.component.DaggerMainAcComponent;
import com.song.songup.shoppingapp.mvp.contract.MainAcContract;
import com.song.songup.shoppingapp.mvp.presenter.MainAcPresenter;
import com.song.songup.shoppingapp.mvp.ui.fragment.MainShoppingCarFragment;
import com.song.songup.shoppingapp.mvp.ui.fragment.MainSuperPanFragment;
import com.song.songup.shoppingapp.mvp.ui.fragment.base.MyBaseActivity;
import com.song.songup.shoppingapp.mvp.ui.fragment.main.MainClassFragment;
import com.song.songup.shoppingapp.mvp.ui.fragment.main.MainHomeAFragment;
import com.song.songup.shoppingapp.mvp.ui.fragment.main.MainMeActivityFragment;
import com.song.songup.shoppingapp.mvp.ui.weight.MainTabLayout;
import com.song.songup.shoppingapp.mvp.ui.weight.NavigateBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.armscomponent.commonsdk.common.adapter.BaseFragmentPagerAdapter;
import me.jessyan.armscomponent.commonsdk.utils.ToastUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/01/2019 14:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainAcActivity extends MyBaseActivity<MainAcPresenter> implements MainAcContract.View {
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    @BindView(R.id.maintablelayout)
    MainTabLayout maintablelayout;
    List<BaseFragment> baseFragment;
    @BindView(R.id.navigation_header_container)
    NavigateBarView navigationHeaderContainer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainAcComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        baseFragment = new ArrayList<>();
        baseFragment.add(MainHomeAFragment.newInstance());
        baseFragment.add(MainClassFragment.newInstance());
        baseFragment.add(MainSuperPanFragment.newInstance());
        baseFragment.add(MainShoppingCarFragment.newInstance(0));
        baseFragment.add(MainMeActivityFragment.newInstance());

        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), baseFragment);
        viewpage.setAdapter(baseFragmentPagerAdapter);
        viewpage.setOffscreenPageLimit(5);
        maintablelayout.setViewPager(viewpage, new MainTabLayout.ItemClickListener() {
            @Override
            public boolean click(int position) {
                if (position == 4) {
                    if (navigationHeaderContainer.getVisibility() != View.GONE)
                        navigationHeaderContainer.setVisibility(View.GONE);
                } else {
                    if (navigationHeaderContainer.getVisibility() != View.VISIBLE)
                        navigationHeaderContainer.setVisibility(View.VISIBLE);
                }
                if (position == 1) {
                    baseFragment.get(position).setData(new Object());
                }

                return true;
            }
        });

        requestPermission();

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
        finish();
    }


    private static Boolean isExit = false;

    private void exitBy2Click() {

        if (!isExit) {
            isExit = true;
            ToastUtils.showShort("再按一次退出程序");
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            mPresenter.exit();
        }

    }


    public void requestPermission() {
//        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
//            @Override
//            public void onRequestPermissionSuccess() {
//
//            }
//            @Override
//            public void onRequestPermissionFailure(List<String> permissions) {
//
//            }
//            @Override
//            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
//
//            }
//        }, new RxPermissions(this),  RxErrorHandler.builder().with(this).responseErrorListener(new ResponseErrorListener() {
//            @Override
//            public void handleResponseError(Context context, Throwable t) {
//
//            }
//        }).build());
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mPresenter.requestDailyList();
    }
}
