package me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseHolder;

import java.util.List;

/**
 * Created by wusong on 2018/1/16.
 * recycleview Adapter 基础类
 */

public abstract class BaseRCAdapter<T> extends RecyclerView.Adapter<BaseRCViewHold> {
    protected List<T> dataList;
    protected int converId;
    protected Context context;

    /**
     *
     * @param context
     * @param convertId
     * @param dataList
     * {@hide}
     */
    public BaseRCAdapter(Context context, int convertId, List<T> dataList) {
        this.converId = convertId;
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public BaseRCViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseRCViewHold baseRCViewHold = BaseRCViewHold.get(context, converId, parent);
        return baseRCViewHold;
    }

    @Override
    public void onBindViewHolder(BaseRCViewHold holder, int position) {
        onBindView(holder, dataList.get(position));
        onBindView(holder,dataList.get(position), position);

    }
    //接口用于外部调用，设置界面
    public abstract void onBindView(BaseRCViewHold holder, T data);
    //接口用于外部调用，设置界面
    public void onBindView(BaseRCViewHold holder, T data,int postion){};

    @Override
    public int getItemCount() {
        return dataList == null ? 0:dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 遍历所有{@link BaseHolder},释放他们需要释放的资源
     *
     * @param recyclerView
     */
    public static void releaseAllHolder(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        for (int i = recyclerView.getChildCount() - 1; i >= 0; i--) {
            final View view = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder != null && viewHolder instanceof BaseRCViewHold) {
                ((BaseRCViewHold) viewHolder).onRelease();
            }
        }
    }

    public void setDataList(List<T> dataList){
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    public List<T> getData(){
        return dataList;
    }

}
