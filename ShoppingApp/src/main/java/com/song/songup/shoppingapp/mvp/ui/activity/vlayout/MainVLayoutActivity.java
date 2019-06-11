package com.song.songup.shoppingapp.mvp.ui.activity.vlayout;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.extend.LayoutManagerCanScrollListener;
import com.alibaba.android.vlayout.extend.PerformanceMonitor;
import com.alibaba.android.vlayout.extend.ViewLifeCycleListener;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.jess.arms.di.component.AppComponent;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.ui.fragment.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.BaseRCViewHold;
import me.jessyan.armscomponent.commonsdk.common.adapter.BasePagerAdapter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/07/2019 14:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainVLayoutActivity extends MyBaseActivity {
    private static final boolean BANNER_LAYOUT = true;
    private static final boolean FIX_LAYOUT = true;
    private static final boolean LINEAR_LAYOUT = true;
    private static final boolean SINGLE_LAYOUT = true;
    private static final boolean FLOAT_LAYOUT = true;
    private static final boolean ONEN_LAYOUT = true;
    private static final boolean COLUMN_LAYOUT = true;
    private static final boolean GRID_LAYOUT = true;
    private static final boolean STICKY_LAYOUT = true;
    private static final boolean STAGGER_LAYOUT = true;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    VirtualLayoutManager layoutManager = null;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerMainVLayoutComponent //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
    }

    final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main_vlayout; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

//        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
//        recycleview.setAdapter(delegateAdapter);
//        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        initLayoutManager();
        // layoutManager.setReverseLayout(true);
        initRCScrollListener();
        initItemDecoration();

        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recycleview.setRecycledViewPool(viewPool);
        // recyclerView.addItemDecoration(itemDecoration);
        viewPool.setMaxRecycledViews(0, 20);
        layoutManager.setRecycleOffset(300);

        initViewLifeCycleListener();
        initCanScrollListener();


        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);

        //创建适配器
        createBannerAdapter(delegateAdapter);
        createFloatAdapter(delegateAdapter);
        createSubView(delegateAdapter);



        recycleview.setAdapter(delegateAdapter);
        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

    }
    public void initLayoutManager(){
        layoutManager = new VirtualLayoutManager(this);
        layoutManager.setPerformanceMonitor(new PerformanceMonitor() {
            long start;
            long end;
            @Override
            public void recordStart(String phase, View view) {
                start = System.currentTimeMillis();
            }

            @Override
            public void recordEnd(String phase, View view) {
                end = System.currentTimeMillis();
                Log.d("VLayoutActivity", view.getClass().getName() + " " + (end - start));
            }
        });

        recycleview.setLayoutManager(layoutManager);

    }
    public void initRCScrollListener(){
        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mFirstText.setText("First: " + layoutManager.findFirstVisibleItemPosition());
//                mLastText.setText("Existing: " + MainViewHolder.existing + " Created: " + MainViewHolder.createdTimes);
//                mCountText.setText("Count: " + recyclerView.getChildCount());
//                mTotalOffsetText.setText("Total Offset: " + layoutManager.getOffsetToStart());
            }
        });
    }
    public void  initItemDecoration(){
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = ((VirtualLayoutManager.LayoutParams) view.getLayoutParams()).getViewPosition();
                outRect.set(4, 4, 4, 4);
            }
        };


    }
    public void initViewLifeCycleListener(){
        // viewLifeCycleListener should be used with setRecycleOffset()
        layoutManager.setViewLifeCycleListener(new ViewLifeCycleListener() {
            @Override
            public void onAppearing(View view) {
//                Log.e("ViewLifeCycleTest", "onAppearing: " + view);
            }

            @Override
            public void onDisappearing(View view) {
//                Log.e("ViewLifeCycleTest", "onDisappearing: " + view);
            }

            @Override
            public void onAppeared(View view) {
//                Log.e("ViewLifeCycleTest", "onAppeared: " + view);
            }

            @Override
            public void onDisappeared(View view) {
//                Log.e("ViewLifeCycleTest", "onDisappeared: " + view);
            }
        });
    }
    public void initCanScrollListener(){
        layoutManager.setLayoutManagerCanScrollListener(new LayoutManagerCanScrollListener() {
            @Override
            public boolean canScrollVertically() {
                Log.i("vlayout", "canScrollVertically: ");
                return true;
            }

            @Override
            public boolean canScrollHorizontally() {
                Log.i("vlayout", "canScrollHorizontally: ");
                return true;
            }
        });

    }


    private void createBannerAdapter(DelegateAdapter delegateAdapter){
        if (!BANNER_LAYOUT)
            return;

        BaseSubAdapter baseSubAdapter = new BaseSubAdapter(this,new LinearLayoutHelper(),1,
                R.layout.view_pager){
            @Override
            protected void onBindViewHolderWithOffset(BaseRCViewHold holder, int position, int offsetTotal) {
                super.onBindViewHolderWithOffset(holder, position, offsetTotal);
                ViewPager viewPager = (ViewPager) holder.getContentView();
                viewPager.setLayoutParams(new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                // from position to get adapter


                ArrayList<View> viewList = new ArrayList<>();
                for (int i =0; i<3; i++) {
                    View view1 = new View(MainVLayoutActivity.this);
                    view1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    if (i ==0)
                        view1.setBackgroundColor(Color.RED);
                    else if(i == 1)
                        view1.setBackgroundColor(Color.BLUE);
                    else if (i == 2)
                        view1.setBackgroundColor(Color.YELLOW);
                    viewList.add(view1);
                }
                BasePagerAdapter basePagerAdapter = new BasePagerAdapter(viewList);
                viewPager.setAdapter(basePagerAdapter);

            }
        };
        delegateAdapter.addAdapter(baseSubAdapter);

    }

    private void createFloatAdapter(DelegateAdapter delegateAdapter){
        if (!FLOAT_LAYOUT)
            return;
        FloatLayoutHelper floatLayoutHelper = new FloatLayoutHelper();
        floatLayoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);//初始的位置
        floatLayoutHelper.setDefaultLocation(50,200);//初始距离周围的距离
        floatLayoutHelper.setAspectRatio(2);//设置宽高比
        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(100,100);
        List<String> list = new ArrayList<>();
        BaseSubAdapter baseSubAdapter = new BaseSubAdapter(this,floatLayoutHelper,
                1,layoutParams,R.layout.listview_vlayout_float);

        delegateAdapter.addAdapter(baseSubAdapter);

    }

    //添加子布局
    public void createSubView(DelegateAdapter adapter){
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
        layoutHelper.setDividerHeight(3);//设置分割线宽度
//        layoutHelper.setAspectRatio(4);//设置宽高比
        layoutHelper.setMargin(10,30,10,10);//item外边距
        layoutHelper.setPadding(10,30,10,10);//item内边距
//        layoutHelper.setBgColor(Color.BLUE);//整块区域的颜色


        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(VirtualLayoutManager.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        List<String> dataList = new ArrayList<>();
        for (int i =0; i<25;i++){
            dataList.add(String.valueOf(i));
        }
        BaseSubAdapter baseSubAdapter = new BaseSubAdapter(this,layoutHelper,
                layoutParams,dataList,R.layout.listview_vlayout_float){
            @Override
            protected void onBindViewHolderWithOffset(BaseRCViewHold holder, int position, int offsetTotal) {
                super.onBindViewHolderWithOffset(holder, position, offsetTotal);
                holder.setText(R.id.tv_name,this.dataList.get(position).toString());

            }
        };

        adapter.addAdapter(baseSubAdapter);
    }




}
