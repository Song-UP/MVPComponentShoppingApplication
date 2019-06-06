package com.song.songup.shoppingapp.mvp.ui.weight;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.util.ConvertUtils;


public class ShapeIndicatorView extends View implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    int left;
    int top;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private Bitmap mBgBitmap;
    private Context mContext;

    public ShapeIndicatorView(Context context) {
        this(context, null);
    }

    public ShapeIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr, 0);
    }



    private void initViews(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tab_active);//设置背景图

        mBgBitmap = Bitmap.createScaledBitmap(mBgBitmap, ConvertUtils.dp2px( 48), ConvertUtils.dp2px( 3), false);

        top = ConvertUtils.dp2px( 47);

        mContext = context;
    }


    public void setupWithTabLayout(final TabLayout tableLayout) {
        mTabLayout = tableLayout;

        tableLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        tableLayout.setOnTabSelectedListener(this);


        ViewCompat.setElevation(this, ViewCompat.getElevation(mTabLayout));
        tableLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mTabLayout.getTabCount() > 0)
                    onTabSelected(mTabLayout.getTabAt(0));

            }
        });

        //清除Tab background
        for (int tab = 0; tab < tableLayout.getTabCount(); tab++) {
            View tabView = getTabViewByPosition(tab);
            if(tabView != null) {
                tabView.setBackgroundResource(0);
            }
        }


//        generatePath(0, 0);//初始化画第一个
//        invalidate();
    }

    public void setupWithViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);

        post(new Runnable() {
            @Override
            public void run() {
                generatePath(0);//初始化画第一个
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {
        if(mTabLayout != null) {
            canvas.drawBitmap(mBgBitmap, left, top, null);
        }
    }

    private void generatePath(int position) {
        View tabView = getTabViewByPosition(position);
        if(tabView == null){
            return;
        }

        left = tabView.getLeft() + (tabView.getWidth() - ConvertUtils.dp2px(48)) / 2;

    }

    private Rect getTabArea() {
        Rect rect = null;

        if (mTabLayout != null) {
            View view = mTabLayout.getChildAt(0);
            rect = new Rect();
            view.getHitRect(rect);
        }

        return rect;
    }

    private View getTabViewByPosition(int position) {
        if (mTabLayout != null && mTabLayout.getTabCount() > 0) {
            ViewGroup tabStrip = (ViewGroup) mTabLayout.getChildAt(0);
            return tabStrip != null ? tabStrip.getChildAt(position) : null;
        }

        return null;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        generatePath(position, positionOffset);
//        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (mTabLayout.getSelectedTabPosition() != position)
            mTabLayout.getTabAt(position).select();

        generatePath(position);
        invalidate();
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 当已经有一个ViewPager后，当TabLayout的tab改变的时候在onTabSelected方法直接调用ViewPager的
     * setCurrentItem方法调用这个方法后会触发ViewPager的scroll事件也就是在onPageScrolled方法中调用
     * generatePath方法来更新Path，如果没有ViewPager的话直接在onTabSelected的方法中调用generatePath
     * 方法。
     **/
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (mViewPager != null) {
            if (tab.getPosition() != mViewPager.getCurrentItem())
                mViewPager.setCurrentItem(tab.getPosition());
        } else {
            generatePath(tab.getPosition());
//            invalidate();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}