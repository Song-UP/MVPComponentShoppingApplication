package com.song.songup.shoppingapp.mvp.ui.fragment.base;

import com.jess.arms.mvp.IPresenter;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/4/30 17:51
 * 修改备注：
 */
public abstract class LazyFragment<P extends IPresenter> extends MyBaseFragment<P> {
    protected boolean isVisible;
    protected boolean isPrepare;

    /**
     * viewpager 配合lazyFragment 使用，当Fragment可见性改变时，执行这个方法（此时onResume已经不准了）
     * 因为setUserVisibleHint实在onCreateView之前调用的，所以要比要避免出现view=null的情况(第一次打开的时候会出现这种情况)
     * @param isVisibleToUser
     * getUserVisibleHint ：判断是否是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){ //可见的是时候执行操作
            isVisible = true;
            onVisible();
        }else {//不可见的时候执行
            isVisible=false;
            onInvisible();

        }
    }
    protected void onVisible(){
        lazyLoad();
    }
    protected void onInvisible(){

    };

    protected abstract void lazyLoad();

}