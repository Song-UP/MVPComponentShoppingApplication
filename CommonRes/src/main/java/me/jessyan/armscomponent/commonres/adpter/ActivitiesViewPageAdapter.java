package me.jessyan.armscomponent.commonres.adpter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ActivitiesViewPageAdapter extends PagerAdapter {
    private List<View> mListViews;
    public ActivitiesViewPageAdapter(List<View> mListViews) {
        this.mListViews = mListViews;
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    //直接继承PagerAdapter，至少必须重写下面的四个方法，否则会报错
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)  {
        container.removeView(mListViews.get(position));//删除页卡
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        //这个方法用来实例化页卡
        container.addView(mListViews.get(position), 0);//添加页卡
        return mListViews.get(position);
    }
}
