package com.song.songup.shoppingapp.mvp.ui.weight.calendars;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.WeekBar;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.util.ConvertUtils;

/**
 * 自定义英文栏
 * Created by huanghaibin on 2017/11/30.
 */

public class CustomRangeWeekBar extends WeekBar {

    private int mPreSelectedIndex;

    public CustomRangeWeekBar(Context context) {
        super(context);

        this.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.custom_range_week_bar, this, true);
        setBackgroundColor(Color.WHITE);
        int padding = ConvertUtils.dp2px(10);
        setPadding(padding, 0, padding, 0);
    }

    @Override
    protected void onDateSelected(Calendar calendar, int weekStart, boolean isClick) {
//        getChildAt(mPreSelectedIndex).setSelected(false);
//        int viewIndex = getViewIndexByCalendar(calendar, weekStart);
//        getChildAt(viewIndex).setSelected(true);
//        mPreSelectedIndex = viewIndex;
    }

    ViewGroup chileWeekGropView;

    public ViewGroup getChildWeekView(){
        if (chileWeekGropView == null){
            chileWeekGropView = findViewById(R.id.ll_week);
        }
        return chileWeekGropView;

    }



    /**
     * 当周起始发生变化，使用自定义布局需要重写这个方法，避免出问题
     *
     * @param weekStart 周起始
     */
    @Override
    protected void onWeekStartChange(int weekStart) {

//        ViewGroup view = findViewById(R.id.ll_week);
//
//        for (int i = 0; i < view.getChildCount(); i++) {
//            ((TextView) view.getChildAt(i)).setText(getWeekString(i, weekStart));
//        }
    }

}
