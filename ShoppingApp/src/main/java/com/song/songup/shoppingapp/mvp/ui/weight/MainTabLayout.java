package com.song.songup.shoppingapp.mvp.ui.weight;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonres.weight.BaseFramelayout;
import me.jessyan.armscomponent.commonres.weight.BaseLinearLayout;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/4/29 20:09
 * 修改备注：
 */
public class MainTabLayout extends BaseLinearLayout implements ViewPager.OnPageChangeListener{
    ViewPager viewPager;
    private int childCount;
    private List<BaseTabView> mainTabViews;
    private int currentItem;
    public MainTabLayout(Context context) {
        super(context);
    }
    private ItemClickListener itemClickListener;

    public MainTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mainTabViews = new ArrayList<BaseTabView>();

    }

    @Override
    protected void initView(Context context) {

    }

    @Override
    protected void load(Context context) {

    }

    public void setViewPager(ViewPager viewPager, ItemClickListener itemClickListener) {
        this.viewPager = viewPager;
        this.itemClickListener = itemClickListener;
        this.init();
    }

    private void init() {
        if (this.viewPager == null) {
            throw new IllegalArgumentException("参数不能为空");
        } else {
            this.childCount = this.getChildCount();
            if (this.viewPager.getAdapter().getCount() != this.childCount) {
                throw new IllegalArgumentException("LinearLayout的子View数量必须和ViewPager条目数量一致");
            } else {
                for(int i = 0; i < this.childCount; ++i) {
                    if (!(this.getChildAt(i) instanceof MainTabView)) {
                        throw new IllegalArgumentException("MainTabLayout的子View必须是BaseTabLayout的子类");
                    }

                    BaseTabView mainTabView = (BaseTabView) this.getChildAt(i);
                    this.mainTabViews.add(mainTabView);
                    final int clickItem = i;
                    this.mainTabViews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (itemClickListener != null){
                                if (!itemClickListener.click(clickItem)){
                                    return;
                                }
                            }
                            resetState(clickItem);
                            viewPager.setCurrentItem(clickItem);
                        }
                    });
//                    mainTabView.setOnClickListener(new AlphaIndicator.MyOnClickListener(i));
                }

                this.viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
//                this.mainTabViews.get(this.currentItem).setIconAlpha(1.0F);
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public void resetState(int currentItem){
        this.currentItem = currentItem;
        for (int i =0; i<mainTabViews.size(); i++){
            if (currentItem == i)
                mainTabViews.get(i).change(true);
            else
                mainTabViews.get(i).change(false);
        }
    }


    private class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        private MyOnPageChangeListener() {
        }

        public void onPageSelected(int position) {
            MainTabLayout.this.resetState(position);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Context context = getContext();
                Activity activity = null;
                if (!(context instanceof Activity))
                    return;
                activity = (Activity)context;
//                if (position == 0) { //设置1
//                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                } else {
//                    activity.getWindow().getDecorView().setSystemUiVisibility(
//                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//                }
            }
//            ((AlphaView)AlphaIndicator.this.alphaViews.get(position)).setIconAlpha(1.0F);
//            AlphaIndicator.this.currentItem = position;
        }
    }

    /**
     * 点击时添加自己的逻辑，同时可以切换viewpager
     */
    public interface ItemClickListener {
        boolean click(int position); //返回false,则不在执行viewPager的切换；返回true则viewPager切换正常执行
    }

}


