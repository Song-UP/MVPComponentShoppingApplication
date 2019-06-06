package com.song.songup.shoppingapp.mvp.ui.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.model.been.main.Winger;

import java.util.ArrayList;
import java.util.List;

public class VerticalSwitchView extends ViewFlipper {

    private Context context;
    /**动画时间*/
    private int animDuration = 400;
    /**一次性显示多少个*/
    private int itemCount = 3;
    /**轮播间隔*/
    private int interval = 2000;
    /**
    * 更新移出屏幕的View的索引
    */
    private int index = 0;
    private List<LinearLayout> parentViews = new ArrayList<>();

    private VerticalSwitchViewAdapter adapter;

    public VerticalSwitchView(Context context) {
        this(context, null);
    }

    public VerticalSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void setAdapter(VerticalSwitchViewAdapter adapter) {
        this.adapter = adapter;
        setData();
    }

    private void init() {
        Animation animIn = AnimationUtils.loadAnimation(context, R.anim.anim_switch_in);
        Animation animOut = AnimationUtils.loadAnimation(context, R.anim.anim_switch_out);
        animIn.setDuration(animDuration);
        animOut.setDuration(animDuration);
        setInAnimation(animIn);
        setOutAnimation(animOut);
        setFlipInterval(interval);
        setMeasureAllChildren(false);
    }

    private void setData() {
        removeAllViews();
        parentViews.clear();
        int currentIndex = 0;
        int loopCount = adapter.getItemCount() % itemCount == 0 ? adapter.getItemCount() / itemCount : adapter.getItemCount() / itemCount + 1;
        for(int i=0; i<loopCount; i++){
            LinearLayout parentView = new LinearLayout(getContext());
            parentView.setOrientation(LinearLayout.VERTICAL);
            parentView.setGravity(Gravity.CENTER);
            parentView.removeAllViews();
            for (int j = 0; j < itemCount; j++) {
                View view = adapter.onCreateView(this);
                parentView.addView(view);
                adapter.onBindView(view, getRealPosition(j, currentIndex));
                currentIndex = getRealPosition(j, currentIndex);
                if (currentIndex+1>=adapter.mDatas.size())
                    break;
            }
            parentViews.add(parentView);
            addView(parentView);
        }
//        startFlipping();
    }

    public void refreshData(List<Winger> winners){
        LinearLayout parentView = parentViews.get((index+1) % 2);
        adapter.refreshData(parentView, winners);
    }

    public void recodeSwtichIndex(){
        index++;
    }

    private int getRealPosition(int index, int currentIndex) {
        if ((index == 0 && currentIndex == 0) ||
                (currentIndex == adapter.getItemCount() - 1
                        && currentIndex % itemCount == 0)) {
            return 0;
        } else {
            return currentIndex + 1;
        }
    }
}
