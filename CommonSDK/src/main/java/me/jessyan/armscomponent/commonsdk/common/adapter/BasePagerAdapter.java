package me.jessyan.armscomponent.commonsdk.common.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/8/19.
 */

public class BasePagerAdapter extends PagerAdapter {
    private List<View> mViewList;

    public BasePagerAdapter(ArrayList<View> mViewList) {
        super();
        this.mViewList = mViewList;
    }


    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));//添加页卡
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);//删除页卡
    }
}
