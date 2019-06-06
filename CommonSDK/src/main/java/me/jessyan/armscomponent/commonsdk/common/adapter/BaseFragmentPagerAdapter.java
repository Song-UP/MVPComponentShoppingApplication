package me.jessyan.armscomponent.commonsdk.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jess.arms.base.BaseFragment;

import java.util.List;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/4/30 15:40
 * 修改备注：
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> baseFragments;
    private boolean isPrepared;// 标志位，标志已经初始化完成。

    public BaseFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> baseFragments) {
        super(fm);
        this.baseFragments = baseFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return baseFragments.get(i);
    }

    @Override
    public int getCount() {
        return baseFragments==null?0:baseFragments.size();
    }
}
