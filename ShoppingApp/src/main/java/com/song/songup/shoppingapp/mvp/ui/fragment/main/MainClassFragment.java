package com.song.songup.shoppingapp.mvp.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.di.component.main.DaggerMainClassComponent;
import com.song.songup.shoppingapp.mvp.contract.main.MainClassContract;
import com.song.songup.shoppingapp.mvp.presenter.main.MainClassPresenter;
import com.song.songup.shoppingapp.mvp.ui.adapter.CommonFragmentStatePagerAdapter;
import com.song.songup.shoppingapp.mvp.ui.fragment.MainShoppingCarFragment;
import com.song.songup.shoppingapp.mvp.ui.fragment.base.LazyFragment;
import com.song.songup.shoppingapp.mvp.ui.fragment.classes.ClassFirstFragment;
import com.song.songup.shoppingapp.mvp.ui.weight.ShapeIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/16/2019 19:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainClassFragment extends LazyFragment<MainClassPresenter> implements MainClassContract.View {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp_view)
    ViewPager vpView;
    @BindView(R.id.custom_indicator)
    ShapeIndicatorView customIndicator;
    Unbinder unbinder;
    private boolean mHasLoadedOnce;  //用来判断已经加载过的不在加载
    private FragmentManager childFragmentManager;
    CommonFragmentStatePagerAdapter adapter;
    List<BaseFragment> baseFragmentList = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    public static MainClassFragment newInstance() {
        MainClassFragment fragment = new MainClassFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainClassComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_classes, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isPrepare = true;

        childFragmentManager = getChildFragmentManager();
        baseFragmentList.add(ClassFirstFragment.newInstance());
//        baseFragmentList.add(MainShoppingCarFragment.newInstance(1));
//        baseFragmentList.add(MainShoppingCarFragment.newInstance(2));
//        baseFragmentList.add(MainShoppingCarFragment.newInstance(3));

        titles.add("标题");
//        titles.add("标题二");
//        titles.add("标题三");
//        titles.add("标题四");

        initViewPager();
        initTablayout();

    }

    public void initViewPager() {
        adapter = new CommonFragmentStatePagerAdapter(childFragmentManager, baseFragmentList, titles);
        vpView.setAdapter(adapter);
    }

    public void initTablayout() {
        tablayout.setupWithViewPager(vpView);
//        setTabClickListener();

    }

    int index = 1;

    public void changeViewPager(int position) {
        boolean isRemoved = false;
        for (int i = titles.size() - 1; i > position; i--) {
            titles.remove(i);
            baseFragmentList.remove(i);
            isRemoved = true;
        }
//        if (titles.size() >= (position + 1) && isRemoved) {
//            adapter.notifyDataSetChanged();
//            vpView.setCurrentItem(titles.size() - 1);
//            return;
//        } else {
            position++;
            titles.add("标题" + position);
            baseFragmentList.add(MainShoppingCarFragment.newInstance(index * 10 + position));
            index++;
            adapter.notifyDataSetChanged();
            vpView.setCurrentItem(titles.size());
            setTabClickListener();

//        }


    }

    int i = 0;
    @OnClick(R.id.bt_add_tab)
    public void onViewClicked() {
        changeViewPager((i++)%3);


    }

    public void setTabClickListener() {
        LinearLayout tabStrip = (LinearLayout) tablayout.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View tabView = tabStrip.getChildAt(i);
            if (tabView != null) {
                int finalI = i;
                tabView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (finalI < (titles.size()-1))
//                            changeViewPager(finalI);
                    }
                });
            }
        }
    }


//        for (int i = 0; i < tablayout.getTabCount(); i++) {
//            TabLayout.Tab tab =tablayout.getTabAt(i);
//            if (tab!=null){
//                if (tab.getCustomView()!=null){
//                    View tabView=  (View)tab.getCustomView().getParent();
//                    int finalI = i;
//                    tabView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            changeViewPager(finalI);
//                        }
//                    });
//
//                }
//            }
//        }
//    }


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
    protected void lazyLoad() {
        if (!isPrepare || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;


    }


}
