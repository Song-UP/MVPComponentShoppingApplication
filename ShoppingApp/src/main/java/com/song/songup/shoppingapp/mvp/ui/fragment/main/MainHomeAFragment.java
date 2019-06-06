package com.song.songup.shoppingapp.mvp.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.di.component.main.DaggerMainHomeAComponent;
import com.song.songup.shoppingapp.interfaces.BaseAnimationListener;
import com.song.songup.shoppingapp.mvp.contract.main.MainHomeAContract;
import com.song.songup.shoppingapp.mvp.model.been.main.Winger;
import com.song.songup.shoppingapp.mvp.presenter.main.MainHomeAPresenter;
import com.song.songup.shoppingapp.mvp.ui.adapter.VerticalSwitchView;
import com.song.songup.shoppingapp.mvp.ui.adapter.VerticalSwitchViewAdapter;
import com.song.songup.shoppingapp.mvp.ui.fragment.base.LazyFragment;
import com.song.songup.shoppingapp.util.GlideImageLoader;
import com.song.songup.shoppingapp.util.RandomWinnerUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.BaseRCAdapter;
import me.jessyan.armscomponent.commonsdk.utils.count_down.CountDownManager;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/02/2019 19:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainHomeAFragment extends LazyFragment<MainHomeAPresenter> implements MainHomeAContract.View {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ry_activity)
    RecyclerView ryActivity;
    Unbinder unbinder1;
    @BindView(R.id.switchView_winger)
    VerticalSwitchView switchViewWinger;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private long autoScrollTime = 0;
    private boolean isRyWingerTouch = false;
    LinearLayoutManager layoutManager;
    VerticalSwitchViewAdapter verticalSwitchViewAdapter;
    private boolean mHasLoadedOnce;  //用来判断已经加载过的不在加载

    private int mIndex = 0;

    List<Winger> wingerList;

    @Inject
    BaseRCAdapter<Winger> wingerAdapter;
    @Inject
    CountDownManager.CountDownModel countDownModel;

    public static MainHomeAFragment newInstance() {
        MainHomeAFragment fragment = new MainHomeAFragment();
        return fragment;
    }

    int mCurrentPosition = 0;

    @Override
    public void changeSwitchviewWinggerByTime() {
        mIndex++;
        if (mIndex % 3 == 0) {
            switchViewWinger.showNext();
            switchViewWinger.recodeSwtichIndex();
        }
    }

    @Override
    public void changeRyActivityByTime() {
        //recycleview每3个每隔6000轮播
        if (System.currentTimeMillis() - autoScrollTime > 6000) {
            autoScrollTime = System.currentTimeMillis();
            if (!isRyWingerTouch) {
                if (mCurrentPosition + 1 < wingerAdapter.getData().size()) {
                    ryActivity.smoothScrollToPosition(++mCurrentPosition);
                } else {
                    mCurrentPosition = 0;
                    ryActivity.scrollToPosition(mCurrentPosition);
                }
            }
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainHomeAComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        onCreateActivity()之后执行
        isPrepare = true;
        initSwipeRefresh();
        initRcycleview();
        initSwitchData();
        initBanner();

        countDown();
    }
    public void initSwipeRefresh(){
        swipeRefresh.setOnRefreshListener(()->{
            mPresenter.refresh();
        });

    }

    public void initBanner() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(R.drawable.banner1);
        }
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
    }

    public void initRcycleview() {
        ryActivity.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    int position = layoutManager.findFirstCompletelyVisibleItemPosition();
                    if (position != -1) {
                        mCurrentPosition = position;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        ryActivity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                autoScrollTime = System.currentTimeMillis();
                if (ev.getAction() == MotionEvent.ACTION_DOWN || ev.getAction() == MotionEvent.ACTION_MOVE) {
                    isRyWingerTouch = true;
                } else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
                    isRyWingerTouch = false;
                }
                return false;
            }
        });

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        //        layoutManager.setAutoMeasureEnabled(true);
        ryActivity.setLayoutManager(layoutManager);
        ryActivity.setHasFixedSize(true);//如果可以确定每个item的高度是固定的(当recycleview高度固定，不在计算item的总高度)，设置这个选项可以提高性能
        ryActivity.setNestedScrollingEnabled(false);
        ryActivity.setAdapter(wingerAdapter);
        wingerAdapter.setDataList(Winger.getList());

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(ryActivity);

    }

    private void initSwitchData() {
        wingerList = Winger.getList();
        verticalSwitchViewAdapter = new VerticalSwitchViewAdapter(wingerList);
        switchViewWinger.setAdapter(verticalSwitchViewAdapter);
        switchViewWinger.getInAnimation().setAnimationListener(new BaseAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                switchViewWinger.refreshData(RandomWinnerUtils.getWinners(3));
            }
        });
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
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
        mHasLoadedOnce= true;




    }

    private void countDown() {
        CountDownManager.asInstance().register(countDownModel).startCountDown();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CountDownManager.asInstance().unRegister(countDownModel);
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
}
