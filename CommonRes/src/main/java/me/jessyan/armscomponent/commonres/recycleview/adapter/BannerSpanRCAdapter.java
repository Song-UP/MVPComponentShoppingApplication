package me.jessyan.armscomponent.commonres.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.jessyan.armscomponent.commonres.R;
import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.BaseRCViewHold;
import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.MultiRCAdapter;

/**
 * Created by wusong on 2018/1/18.
 *
 *不同列  headMap 是 第一个是 <K,topCount>
 */

public abstract class BannerSpanRCAdapter<T,K> extends MultiRCAdapter<T> {
    private final int BANNER_TYPE = 0; //banner
    private final int TITLE_TYPE = 1;  //标题
    private final int DETAIL_TYPE = 2;  //detail
    private int topCount = 1; //最上方不规则的个数

    protected BannerSupport headSupport;
    protected LinkedHashMap<K, Integer> headMap;  //存放head的字符串和存放的位置

    public BannerSpanRCAdapter(Context context, List dataList, BannerSupport headSupport) {
        super(context, dataList, null);
        this.headSupport = headSupport;
        this.multiItemTypeSupport = new HeadMultiItemTypeSupport();
        headMap = new LinkedHashMap<>();
        setDataHeadMap();
        //用于监听数据的改变，当数据改变时，同时刷新头部的数据 （不要忘了取消注册）
        registerAdapterDataObserver(observer);
    }


//设置item所占的区域大小（过大换行） 注意 spanCount必须大于等于4
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (! (layoutManager instanceof GridLayoutManager) ){
            return;
        }

        final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int span = gridLayoutManager.getSpanCount();
                switch (multiItemTypeSupport.getItemType(position,null)){
                    case BANNER_TYPE:
                        break;
                    case TITLE_TYPE:
                        break;
                    case DETAIL_TYPE:
                        span /=2;
                        break;
                }
                return span;
            }
        });
    }

    //Head

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unregisterAdapterDataObserver(observer);
    }


    @Override
    public int getItemViewType(int position) {
        return multiItemTypeSupport.getItemType(position, null);
    }


    @Override
    public void onBindViewHolder(BaseRCViewHold holder, int position) {
        //除头部之外，其他的实际位置

        int type = multiItemTypeSupport.getItemType(position, null);
        switch (type){
            case BANNER_TYPE:
                onBindBanner(holder);
                break;
            case TITLE_TYPE:
                onBindTitle(holder, getKey(position));
                break;
            case DETAIL_TYPE:
                int index = getOtherPostion(position);
                onBindDetail(holder,dataList.get(index));
                break;
        }
    }

    @Override
    public void onBindView(BaseRCViewHold holder, T data) {

    }

    //banner需要实现的方法
    public abstract void onBindBanner(BaseRCViewHold holder);
    //头部需要实现
    public abstract void onBindTitle(BaseRCViewHold holder,K tileData);
    //banner需要实现的方法
    public abstract void onBindDetail(BaseRCViewHold holder, T data);

    @Override
    public int getItemCount() {
        return dataList.size() + headMap.size() +topCount;
    }

    /**
     * 用来创建headMap
     */

    int isLeft =1;//等于1代表在左边 -1在右边
    public void setDataHeadMap() {
        int headIndex = topCount;
        headMap.clear();
        for (int i = 0; i<dataList.size(); i++){
            K headStr = (K) headSupport.getTitle(dataList.get(i));
            if (!headMap.containsKey(headStr)){
                headMap.put(headStr, i+ headIndex);
                headIndex++;
            }
        }
    }

    /**
     * 从hashMap中通过value获取key
     */
    private K getKey(int postion){
        Set<Map.Entry<K,Integer>> set = headMap.entrySet();
        for (Map.Entry<K, Integer> entry : set){
            if(entry.getValue() == postion){
                return entry.getKey();

            }
        }
        return null;
    }


    /**
     * 因为 head 和其他的 是分开的， 所以要获取
     */
    public int getOtherPostion(int positon){
        //从第二个开始有title和 detail
        int headCournt = 0;
        Set<Map.Entry<K, Integer>> entrySet = headMap.entrySet();
        for (Map.Entry<K, Integer> entry : entrySet){
            if (entry.getValue() < positon ){
                headCournt++;
            }
        }
        positon -= headCournt;
        positon-=topCount;  //因为 data 的 position是从2开始，也就是index=1； head的map.index = 1,而data的index应为1
        return  positon;
    }


    /**
     * 设置头部的viewId
     */
    public class HeadMultiItemTypeSupport implements MultiItemTypeSupport<T> {
        private final int OTHER_TYPE = 1;

        @Override
        public int getItemType(int position, T data) {
            //return headMap.containsValue(position) ? HEAD_TYPE : OTHER_TYPE;
            if (position == 0){
                return BANNER_TYPE;
            }else {
                return headMap.containsValue(position) ? TITLE_TYPE : DETAIL_TYPE;
            }
        }
        @Override
        public int getItemView(int type) {
            int viewId = converId;
            switch (type){
//                case BANNER_TYPE:
//                    //viewId = headSupport.headLayoutId();
//                    viewId = R.layout.item_banner;
//                    break;
//                case TITLE_TYPE:
//                    viewId = R.layout.item_title;
//                    break;
//                case DETAIL_TYPE:
//                    viewId = R.layout.item_detail;
//                    break;
            }
            return viewId;
        }
    };

    /**
     * 回调， 用来设置标题的内容
     */
    public interface BannerSupport<T,K>{
        K getTitle(T t);  //获取头部的数据
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
