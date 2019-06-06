//package com.song.songup.shoppingapp.mvp.ui.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.view.ViewPager;
//
//import com.jess.arms.base.BaseActivity;
//import com.jess.arms.base.BaseFragment;
//import com.jess.arms.di.component.AppComponent;
//import com.jess.arms.utils.ArmsUtils;
//import com.song.songup.shoppingapp.R;
//import com.song.songup.shoppingapp.mvp.contract.MainContract;
//import com.song.songup.shoppingapp.mvp.presenter.MainPresenter;
//import com.song.songup.shoppingapp.mvp.ui.fragment.MainClassesFragment;
//import com.song.songup.shoppingapp.mvp.ui.fragment.MainHomeFragment;
//import com.song.songup.shoppingapp.mvp.ui.fragment.MainMeFragment;
//import com.song.songup.shoppingapp.mvp.ui.fragment.MainSuperPanFragment;
//import com.song.songup.shoppingapp.mvp.ui.fragment.base.MyBaseActivity;
//import com.song.songup.shoppingapp.mvp.ui.weight.MainTabLayout;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import me.jessyan.armscomponent.commonsdk.common.adapter.BaseFragmentPagerAdapter;
//
//import static com.jess.arms.utils.Preconditions.checkNotNull;
//
//
///**
// * ================================================
// * Description:
// * <p>
// * Created by MVPArmsTemplate on 04/29/2019 15:03
// * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
// * <a href="https://github.com/JessYanCoding">Follow me</a>
// * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
// * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
// * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
// * ================================================
// */
//public class MainActivity extends MyBaseActivity<MainPresenter> implements MainContract.View {
//
//    @BindView(R.id.viewpage)
//    ViewPager viewpage;
//    @BindView(R.id.maintablelayout)
//    MainTabLayout maintablelayout;
//    List<BaseFragment> baseFragment;
//
//    @Override
//    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerMainComponent //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
//    }
//
//    @Override
//    public int initView(@Nullable Bundle savedInstanceState) {
//        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
//    }
//
//    @Override
//    public void initData(@Nullable Bundle savedInstanceState) {
//        baseFragment.add(MainHomeFragment.newInstance());
//        baseFragment.add(MainClassesFragment.newInstance());
//        baseFragment.add(MainSuperPanFragment.newInstance());
//        baseFragment.add(MainSuperPanFragment.newInstance());
//        baseFragment.add(MainMeFragment.newInstance());
//
//        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(),baseFragment);
//        viewpage.setAdapter(baseFragmentPagerAdapter);
//        maintablelayout.setViewPager(viewpage);
//
//
//
//    }
//
//    @Override
//    public void showLoading() {
//
//    }
//
//    @Override
//    public void hideLoading() {
//
//    }
//
//    @Override
//    public void showMessage(@NonNull String message) {
//        checkNotNull(message);
//        ArmsUtils.snackbarText(message);
//    }
//
//    @Override
//    public void launchActivity(@NonNull Intent intent) {
//        checkNotNull(intent);
//        ArmsUtils.startActivity(intent);
//    }
//
//    @Override
//    public void killMyself() {
//        finish();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }
//}
