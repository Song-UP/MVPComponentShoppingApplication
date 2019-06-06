package me.jessyan.armscomponent.commonres.recycleview.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.MultiRCAdapter;


/**
 * Created by wusong on 2018/1/18.
 *
 *不同列
 */

public abstract class SpanRCAdapter<T> extends MultiRCAdapter<T> {

    private SpanSupport<T> spanSupport;

    public SpanRCAdapter(Context context, List dataList,
                         MultiItemTypeSupport multiItemTypeSupport, SpanSupport<T> spanSupport) {
        super(context, dataList, multiItemTypeSupport);
        this.spanSupport = spanSupport;
    }


//设置item所占的区域大小（过大换行）
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (! (layoutManager instanceof GridLayoutManager) ){
            return;
        }

        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return spanSupport.getSpan(position, (T) dataList.get(position));
            }
        });
    }

    /**
     * 设置一行中的不同列
     */
    public interface SpanSupport<T>{
        int getSpan(int position, T data);
    }


}
