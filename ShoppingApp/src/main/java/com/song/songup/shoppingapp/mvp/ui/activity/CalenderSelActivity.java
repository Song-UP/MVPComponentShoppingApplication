package com.song.songup.shoppingapp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.mvp.ui.weight.calendars.CalenterManger;
import com.song.songup.shoppingapp.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CalenderSelActivity extends AppCompatActivity {
    CalenterManger calenterManger;
    @BindView(R.id.tv_start_day)
    TextView tvStartDay;
    @BindView(R.id.tv_start_week)
    TextView tvStartWeek;
    @BindView(R.id.tv_count_day)
    TextView tvCountDay;
    @BindView(R.id.tv_end_day)
    TextView tvEndDay;
    @BindView(R.id.tv_end_week)
    TextView tvEndWeek;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    Calendar startCalendar;
    Unbinder unbinder;

    public static void startForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, CalenderSelActivity.class);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.anim_switch_in, R.anim.activity_stay);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_sel);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    protected void initView() {

        calenterManger = new CalenterManger(this, calendarView, new CalenterManger.OnSelectListener() {
            @Override
            public void onCalendarRangeSelect(Calendar calendar, String week, boolean isEnd) {
                if (!isEnd) {
                    startCalendar = calendar;
                    tvStartDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
                    tvStartWeek.setText("星期" + week);

                    tvEndDay.setText("请选择");
                    tvEndWeek.setText("");
                    tvCountDay.setText("");
                } else {
                    tvEndDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
                    tvEndWeek.setText("星期" + week);

                    tvCountDay.setText("共" + calendarView.getSelectCalendarRange().size() + "天");
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                List<Calendar> calendarList = calendarView.getSelectCalendarRange();
                if (calendarList.size() == 0) {
                    if (startCalendar == null) {
                        ToastUtils.showShort("请先选择日期");
                        return;
                    }
                    else {
                        calendarList.add(startCalendar);
                    }
                }
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.anim_exit_from_bottom);
    }
}
