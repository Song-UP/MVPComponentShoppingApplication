package me.jessyan.armscomponent.commonres.recycleview.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by wusong on 2018/1/18.
 *
 *不同列
 */

public abstract class BannerDecoraRCAdapter<T,K> extends BannerSpanRCAdapter<T,K>{

    public BannerDecoraRCAdapter(Context context, List dataList, BannerSupport headSupport) {
        super(context, dataList, headSupport);
    }

    int right = -1;
    public void setDataHeadMap() {
        int headIndex = 1;
        headMap.clear();
        for (int i = 0; i<dataList.size(); i++){
            K headStr = (K) headSupport.getTitle(dataList.get(i));
            if (!headMap.containsKey(headStr)){
                headMap.put(headStr, i+ headIndex);
                headIndex++;
                right =-1;
                //right = 1;
            }else {
                right = -right;
            }

            setIsLeft(dataList.get(i),right);
        }

    }

    public void setIsLeft(T data, int right){}


}
