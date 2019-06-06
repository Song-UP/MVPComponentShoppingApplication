package com.song.songup.shoppingapp.mvp.ui.adapter;

import android.content.Context;
import android.util.SparseArray;

import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.model.been.classes.ClassesBeen;
import com.song.songup.shoppingapp.util.TimeUtils;

import java.util.List;

import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.BaseRCAdapter;
import me.jessyan.armscomponent.commonres.recycleview.base.rcAdapter.BaseRCViewHold;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/17 19:33
 * 修改备注：
 */
public class ClassesFirstAdapter extends BaseRCAdapter<ClassesBeen> {
    //存储带有倒计时的BaseRcviewHold
    SparseArray<BaseRCViewHold> baseRCViewHoldSparseArray = new SparseArray<>();
    /**
     * @param context
     * @param dataList  {@hide}
     */
    public ClassesFirstAdapter(Context context, List<ClassesBeen> dataList) {
        super(context,R.layout.listview_class_first, dataList);
    }
    public ClassesFirstAdapter(Context context, int convertId, List<ClassesBeen> dataList) {
        super(context, convertId, dataList);
    }

    @Override
    public void onBindView(BaseRCViewHold holder, ClassesBeen data) {
//        holder.setText(R.id.tv_remainTime, data.getTime());
    }

    @Override
    public void onBindView(BaseRCViewHold holder, ClassesBeen data, int postion) {
        holder.setText(R.id.tv_title,data.getTitle());
        if ("1".equals(data.getType())){
            baseRCViewHoldSparseArray.put(postion, holder);
        }
    }

    public void refreshTime(){
        for (int i = 0; i < baseRCViewHoldSparseArray.size(); i++) {
            BaseRCViewHold baseRCViewHold = baseRCViewHoldSparseArray.get(i);
            if (baseRCViewHold == null)
                return;
            dataList.get(i).setPastTime(dataList.get(i).getPastTime()+1);
            if (dataList.get(i).getPastTime() == dataList.get(i).getTime())
                dataList.get(i).setPastTime(0);
            baseRCViewHold.setText(R.id.tv_remainTime,TimeUtils.getCountTimeByLong((dataList.get(i).getTime() - dataList.get(i).getPastTime()) * 1000));
        }
    }
}
