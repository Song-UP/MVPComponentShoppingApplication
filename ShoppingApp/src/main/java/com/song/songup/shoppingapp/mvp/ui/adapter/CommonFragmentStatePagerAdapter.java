package com.song.songup.shoppingapp.mvp.ui.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jess.arms.base.BaseFragment;

import java.util.List;

/**
 * Created by weixiaopan on 2018/1/5.
 */

public class CommonFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragments;
    private List<String> titles;

    public CommonFragmentStatePagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles==null?null:titles.get(position);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
