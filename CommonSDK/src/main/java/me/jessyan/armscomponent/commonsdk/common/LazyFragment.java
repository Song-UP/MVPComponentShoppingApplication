package me.jessyan.armscomponent.commonsdk.common;

import android.support.v4.app.Fragment;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/4/29 13:55
 * 修改备注：
 */
public abstract class LazyFragment extends Fragment {
    protected boolean isVisible;

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

//使用例子
//public class OpenResultFragment extends LazyFragment{
//    // 标志位，标志已经初始化完成。
//    private boolean isPrepared;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(LOG_TAG, "onCreateView");
//        View view = inflater.inflate(R.layout.fragment_open_result, container, false);
//        //XXX初始化view的各控件
//        isPrepared = true;
//        lazyLoad();
//        return view;
//    }
//    @Override
//    protected void lazyLoad() {
//        if(!isPrepared || !isVisible) {
//            return;
//        }
//        //填充各控件的数据
//    }
//}
