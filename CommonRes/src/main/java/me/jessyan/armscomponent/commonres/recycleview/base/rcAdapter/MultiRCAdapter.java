package me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wusong on 2018/1/16.
 *
 * 多种布局的RecycleView
 */

public abstract class MultiRCAdapter<T> extends BaseRCAdapter<T> {
    protected MultiItemTypeSupport multiItemTypeSupport;

    public MultiRCAdapter(Context context, List<T> dataList, MultiItemTypeSupport multiItemTypeSupport) {
        super(context, 0, dataList);
        this.multiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public BaseRCViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemId = multiItemTypeSupport.getItemView(viewType);
        return BaseRCViewHold.get(context, itemId, parent);
    }

    @Override
    public int getItemViewType(int position) {
        return multiItemTypeSupport.getItemType(position, dataList.get(position));
    }

    /**
     * 接口类，用于加载不同的布局
     */
    public interface MultiItemTypeSupport<T>{
        int getItemType(int position, T data);
        int getItemView(int type);

    }



}
