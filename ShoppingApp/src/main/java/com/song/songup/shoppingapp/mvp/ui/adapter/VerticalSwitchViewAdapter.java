package com.song.songup.shoppingapp.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.model.been.main.Winger;

import java.util.List;

public class VerticalSwitchViewAdapter {

    protected List<Winger> mDatas;

    public VerticalSwitchViewAdapter(List<Winger> datas) {
        this.mDatas = datas;
    }

    public void setData(List<Winger> datas) {
        this.mDatas = datas;
    }

    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public View onCreateView(VerticalSwitchView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.switchview_winger, null);
    }

    public void onBindView(View view, final int position) {
        Winger response = mDatas.get(position);

        TextView name = view.findViewById(R.id.tv_name);
        TextView tvMoney = view.findViewById(R.id.tv_money);
        TextView tvActivity = view.findViewById(R.id.tv_activity);
        name.setText(response.getName());
        tvMoney.setText(response.getMoney());
        tvActivity.setText(response.getName());
    }

    public void refreshData(LinearLayout parent, List<Winger> winners){
        for(int i=0; i<parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            onBindView(child,i);

//            TextView name = child.findViewById(R.id.name);
//            TextView result = child.findViewById(R.id.result);
//            TextView type = child.findViewById(R.id.type);
//            name.setText(winners.get(i).getUsername());
//            result.setText(winners.get(i).getProfit());
//            type.setText(winners.get(i).getName());
        }
    }

}
