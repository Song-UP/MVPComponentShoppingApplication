package com.song.songup.shoppingapp.mvp.ui.activity.vlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.List;

import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.BaseRCViewHold;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/6/7 15:35
 * 修改备注：
 */
public class BaseSubAdapter<T extends Object> extends DelegateAdapter.Adapter<BaseRCViewHold> {

    private Context mContext;
    private int layoutId; //对应的布局
    private LayoutHelper mLayoutHelper;
    private VirtualLayoutManager.LayoutParams mLayoutParams;
    private int mCount = 0;
    protected List<T> dataList;

    /**
     *
     * @param context
     * @param layoutHelper
     * @param dataList
     * @param layoutId
     */
    public BaseSubAdapter(Context context, LayoutHelper layoutHelper,VirtualLayoutManager.LayoutParams layoutParams,List<T> dataList,int layoutId) {
        this(context, layoutHelper,
                0, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                layoutId);
        this.dataList = dataList;
    }

    /**
     *
     * @param context
     * @param layoutHelper
     * @param count
     * @param layoutId
     */
    public BaseSubAdapter(Context context, LayoutHelper layoutHelper, int count,int layoutId) {
        this(context, layoutHelper,
                count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),
                layoutId);
    }

    public BaseSubAdapter(Context context, LayoutHelper layoutHelper,
                          int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams,
                          int layoutId) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mLayoutParams = layoutParams;
        this.layoutId = layoutId;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public BaseRCViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new BaseRCViewHold(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
        return BaseRCViewHold.get(mContext,layoutId,parent);
    }

    @Override
    public void onBindViewHolder(BaseRCViewHold holder, int position) {
        // only vertical
        holder.getContentView().setLayoutParams(
                new VirtualLayoutManager.LayoutParams(mLayoutParams));
    }


    @Override
    protected void onBindViewHolderWithOffset(BaseRCViewHold holder, int position, int offsetTotal) {
//        ((TextView) holder.itemView.findViewById(R.id.title)).setText(Integer.toString(offsetTotal));
    }

    @Override
    public int getItemCount() {
        return mCount != 0 ? mCount:(dataList == null?0:dataList.size());
    }
}