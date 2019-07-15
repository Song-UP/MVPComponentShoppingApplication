package com.song.songup.shoppingapp.mvp.ui.weight.calendars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.RangeMonthView;

/**
 * 范围选择月视图
 * Created by huanghaibin on 2018/9/13.
 */

public class CustomRangeMonthView extends RangeMonthView {

    private int mRadius;

    public CustomRangeMonthView(Context context) {
        super(context);
    }


    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 绘制选中时的样式(完全靠自定义)
     */
    int fistEndColor = Color.parseColor("#f4493f");
    int centerColor = Color.parseColor("#ffe5e6");
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme,
                                     boolean isSelectedPre, boolean isSelectedNext) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (isSelectedPre) {
            if (isSelectedNext) {
                mSelectedPaint.setColor(centerColor);
                canvas.drawRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            } else {//最后一个，the last
                mSelectedPaint.setColor(centerColor);
                canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSelectedPaint);//最后一个的左内容
                mSelectedPaint.setColor(fistEndColor);
                canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
                calendar.setScheme("first");
            }
        } else {
            mSelectedPaint.setColor(centerColor);
            if(isSelectedNext){//第一个(右内容)
                canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            }
            mSelectedPaint.setColor(fistEndColor);
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            calendar.setScheme("last");
        }

        return false;
    }

    /**
     * 这里绘制标记的日期样式，想怎么操作就怎么操作
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

//    int year = mCalendarView.getCurYear();
//    int month = mCalendarView.getCurMonth();
//    Map<String, Calendar> map = new HashMap<>();
//        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
//    getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
    //此方法在巨大的数据量上不影响遍历性能，推荐使用
//        mCalendarView.setSchemeDate(map);



//这里绘制文本，不要再问我怎么隐藏农历了，不要再问我怎么把某个日期换成特殊字符串了，
// 要怎么显示你就在这里怎么画，你不画就不显示，是看你想怎么显示日历的，而不是看框架
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        boolean isInRange = isInRange(calendar);
        boolean isEnable = !onCalendarIntercept(calendar);

        if (isSelected) {
            if ("first".equals(calendar.getScheme()) || "last".equals(calendar.getScheme())) {
                mSelectTextPaint.setColor(Color.parseColor("#ffffff"));
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx,
                        baselineY,
                        mSelectTextPaint);
                calendar.setScheme("other");
            }else {
                mSelectTextPaint.setColor(Color.parseColor("#383838"));
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx,
                        baselineY,
                        mSelectTextPaint);
            }
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }


}
