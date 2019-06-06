package me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wusong on 2018/1/16.
 *
 *  通过data获得title， 然后把title显示在布局上
 */

public abstract class HeadMutiRCAdapter<T> extends MultiRCAdapter {
    protected HeadSupport headSupport;
    protected LinkedHashMap<String, Integer> headMap;  //存放head的字符串和存放的位置
    private final int HEAD_TYPE = 0;  //标记头部

    public HeadMutiRCAdapter(Context context, int layoutId , List dataList, HeadSupport headSupport) {
        super(context, dataList, null);
        this.headSupport = headSupport;
        this.converId = layoutId;
        this.multiItemTypeSupport = new HeadMultiItemTypeSupport();
        headMap = new LinkedHashMap<>();
        setDataHeadMap();
        //用于监听数据的改变，当数据改变时，同时刷新头部的数据 （不要忘了取消注册）
        registerAdapterDataObserver(observer);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unregisterAdapterDataObserver(observer);
    }


    @Override
    public void onBindViewHolder(BaseRCViewHold holder, int position) {
//        int viewType = multiItemTypeSupport.getItemType(position,dataList.get(position));
        //如果是头部
        boolean isHead = headMap.containsValue(position);
        if (isHead){
            String title = headSupport.getTitle(dataList.get(position));
            holder.setText(headSupport.headTextViewId(), title );
            return;
        }
        //除头部之外，其他的实际位置
        position = getOtherPostion(position);
        super.onBindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return dataList.size() + headMap.size();
    }

    /**
     * 用来创建headMap
     */
    public void setDataHeadMap(){
        int headIndex = 0;
        headMap.clear();
        for (int i = 0; i<dataList.size(); i++){
            String headStr = headSupport.getTitle(dataList.get(i));
            if (!headMap.containsKey(headStr)){
                headMap.put(headStr, i+ headIndex);
                headIndex++;
            }
        }
    }

    /**
     * 因为 head 和其他的 是分开的， 所以要获取
     */
    public int getOtherPostion(int positon){
        int headCournt = 0;
        Set<Map.Entry<String, Integer>> entrySet = headMap.entrySet();
       for (Map.Entry<String, Integer> entry : entrySet){
           if (entry.getValue() < positon ){
               headCournt++;
           }
       }
        positon -= headCournt;
        return  positon;
    }


    /**
     * 设置头部的viewId
     */
    public class HeadMultiItemTypeSupport implements MultiItemTypeSupport<T> {
        private final int OTHER_TYPE = 1;

        @Override
        public int getItemType(int position, T data) {
            return headMap.containsValue(position) ? HEAD_TYPE : OTHER_TYPE;
        }
        @Override
        public int getItemView(int type) {
            int viewId = converId;
            switch (type){
                case HEAD_TYPE:
                    viewId = headSupport.headLayoutId();
                    break;
                case OTHER_TYPE:

                    viewId = converId;
                    break;
            }
            return viewId;
        }
    };

    /**
     * 回调， 用来设置标题的内容
     */
    public interface HeadSupport<T>{
        int headLayoutId();
        int headTextViewId();
        String getTitle(T t);
    }

    /**
     * head 中的数据改变时，用于进行监听，进行改变
     */

    RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            setDataHeadMap();

        }
    };




}
