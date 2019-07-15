package com.song.songup.shoppingapp.mvp.ui.weight.calendars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.song.songup.shoppingapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/7/7 15:46
 * 修改备注：
 */
public class CalenterManger {

    private CalendarView mCalendarView;
    private Context context;
    private OnSelectListener onSelectListener;
    private Calendar maxCalendar = new Calendar();
    private Calendar minCalendar;
    private TextView tvYearMonth;

    public CalenterManger(Context context, CalendarView mCalendarView, OnSelectListener onSelectListener) {
        this.mCalendarView = mCalendarView;
        this.context = context;
        this.onSelectListener = onSelectListener;

        maxCalendar.setYear(mCalendarView.getCurYear());
        maxCalendar.setMonth(mCalendarView.getCurMonth());
        maxCalendar.setDay(mCalendarView.getCurDay());

        minCalendar = getPreNumCalendar(maxCalendar,ONE_92_DAY);
        mCalendarView.setRange(minCalendar.getYear(),minCalendar.getMonth(),minCalendar.getDay(),maxCalendar.getYear(),maxCalendar.getMonth(),maxCalendar.getDay());

        tvYearMonth = mCalendarView.findViewById(R.id.app_tv_year_month);
        tvYearMonth.setText(mCalendarView.getCurYear()+"年"+mCalendarView.getCurMonth()+"月");
        setScheme();
        initCalenter();
    }

    public void initCalenter(){
        mCalendarView.scrollToCurrent();
        mCalendarView.setOnCalendarRangeSelectListener(new CalendarView.OnCalendarRangeSelectListener() {
            @Override
            public void onCalendarSelectOutOfRange(Calendar calendar) {
                // TODO: 2018/9/13 超出范围提示
            }

            @Override
            public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {
//                Toast.makeText(context,
//                        calendar.toString() + (isOutOfMinRange ? "小于最小选择范围" : "超过最大选择范围"),
//                        Toast.LENGTH_SHORT).show();
            }

            //选中之后调用
            @SuppressLint("SetTextI18n")
            @Override
            public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {
                onSelectListener.onCalendarRangeSelect(calendar,getWeekString(calendar.getWeek()),isEnd);
            }
        });

        mCalendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
//                Log.e("onMonthChange", "  -- " + year + "  --  " + month);
                tvYearMonth.setText(year+"年"+month+"月");

            }
        });

        //设置日期拦截事件，当前有效（点击的拦截）
        mCalendarView.setOnCalendarInterceptListener(new CalendarView.OnCalendarInterceptListener() {
            /**
             * 屏蔽某些不可点击的日期，可根据自己的业务自行修改
             */
            @Override
            public boolean onCalendarIntercept(Calendar calendar) {
                return isInRand(calendar);

            }
            @Override
            public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
            }
        });
    }


    public boolean isInRand(Calendar calendar){
        if (0<=calendar.compareTo(minCalendar) && calendar.compareTo(maxCalendar) <=0){
            return false;
        }
        return true;
    }


    public interface OnSelectListener{
        void onCalendarRangeSelect(Calendar calendar, String week, boolean isEnd);
    }

//TODO  (92天，对应的就是91，同理5对应4)
    int ONE_92 = 91;
    long ONE_DAY = 1000 * 3600 * 24;
    long ONE_92_DAY = ONE_92*ONE_DAY;
    static Calendar getPreNumCalendar(Calendar calendar, long preNumDayTime) {
        java.util.Calendar date = java.util.Calendar.getInstance();
        date.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());//
        long timeMills = date.getTimeInMillis();//获得起始时间戳
        date.setTimeInMillis(timeMills - preNumDayTime);
        Calendar preCalendar = new Calendar();
        preCalendar.setYear(date.get(java.util.Calendar.YEAR));
        preCalendar.setMonth(date.get(java.util.Calendar.MONTH) + 1);
        preCalendar.setDay(date.get(java.util.Calendar.DAY_OF_MONTH));
        return preCalendar;
    }


    public void setScheme(){
        Map<String, Calendar> map = new HashMap<>();
        Calendar temCalent = maxCalendar;
        for (int i =0;i<ONE_92;i++){
            Calendar calendar = getPreNumCalendar(temCalent,ONE_DAY);
            map.put(calendar.toString(),calendar);
            temCalent = calendar;
        }
//        此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);

    }

    public String getWeekString(int i){
        String week = "日";
        switch (i){
            case 1:
                week="一";
                break;
            case 2:
                week="二";
                break;
            case 3:
                week="三";
                break;
            case 4:
                week="四";
                break;
            case 5:
                week="五";
                break;
            case 6:
                week="六";
                break;

        }
        return week;
    }

}
