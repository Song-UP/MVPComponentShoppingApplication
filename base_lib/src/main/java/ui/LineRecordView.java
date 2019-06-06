package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.lib.R;

/**
 * Created by Administrator on 2015/6/15.
 */
public class LineRecordView extends LinearLayout {
    ImageView ivLeftIcon;//左边的Icon
    TextView tvLeftTitle;//左标题
    TextView tvCenterTitle;//中间标题
    TextView tvRightTitle;//右标题
    ImageView ivRightIcon;//右边的Icon

    public LineRecordView(Context context) {
        super(context);
    }

    public LineRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addView(LayoutInflater.from(context).inflate(R.layout.view_line_record, null), params);

        ivLeftIcon = findViewById(R.id.ivLeftIcon);
        tvLeftTitle = findViewById(R.id.tvLeftTitle);
        tvCenterTitle = findViewById(R.id.tvCenterTitle);
        tvRightTitle = findViewById(R.id.tvRightTitle);
        ivRightIcon = findViewById(R.id.ivRightIcon);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineRecordView);
        setLeftIcon(a.getResourceId(R.styleable.LineRecordView_line_record_left_icon, 0));
        setLeftTitle(a.getString(R.styleable.LineRecordView_line_record_left_title));
        setCenterTitle(a.getString(R.styleable.LineRecordView_line_record_center_title));
        boolean isLeftLong = a.getBoolean(R.styleable.LineRecordView_is_left_long , false);
        boolean isShort = a.getBoolean(R.styleable.LineRecordView_is_line_record_short, false);
        setRightTitle(a.getString(R.styleable.LineRecordView_line_record_right_title));
        setRightIcon(a.getResourceId(R.styleable.LineRecordView_line_record_right_icon, 0));
        if (a.getBoolean(R.styleable.LineRecordView_line_record_default_padding, true)) {
            float dentiy = getContext().getResources().getDisplayMetrics().density;
            int paddingLeft = getPaddingLeft() == 0 ? (int) (16 * dentiy) : getPaddingLeft();
            int paddingTop = getPaddingTop() == 0 ? (int) (20 * dentiy) : getPaddingTop();
            int paddingRight = getPaddingRight() == 0 ? (int) (16 * dentiy) : getPaddingRight();
            int paddingBottom = getPaddingBottom() == 0 ? (int) (20 * dentiy) : getPaddingBottom();
            if(isShort){
                paddingTop = getPaddingTop() == 0 ? (int) (12 * dentiy) : getPaddingTop();
                paddingBottom = getPaddingBottom() == 0 ? (int) (12 * dentiy) : getPaddingBottom();
            }
            setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
        int leftsize = a.getInt(R.styleable.LineRecordView_line_left_title_size, 0);
        if (leftsize != 0) {
            tvLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, leftsize);
        }
        if(isLeftLong){
            LayoutParams lp = (LinearLayout.LayoutParams)tvLeftTitle.getLayoutParams();
            lp.width = LayoutParams.WRAP_CONTENT;
            tvLeftTitle.setLayoutParams(lp);
        }

        a.recycle();
    }

    public void setLeftIcon(int resId) {
        if (resId == 0) {
            ivLeftIcon.setVisibility(View.GONE);
        } else {
            ivLeftIcon.setVisibility(View.VISIBLE);
            ivLeftIcon.setImageResource(resId);
        }
    }

    public void setLeftTitle(String title) {
        tvLeftTitle.setText(title == null ? "" : title);
    }

    public void setCenterTitle(String title) {
        tvCenterTitle.setText(title == null ? "" : title);
    }

    public void setRightTitle(String operateTitle) {
        tvRightTitle.setText(operateTitle == null ? "" : operateTitle);
    }

    public void setRightTitleColor(String color) {
        tvRightTitle.setTextColor(Color.parseColor(color));
    }

    public void setLeftTitleColor(String color) {
        tvLeftTitle.setTextColor(Color.parseColor(color));
    }

    public String getRightTitle(){
        return tvRightTitle.getText().toString();
    }

    public void setRightTitleSize(float f){
        tvRightTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, f);
    }

    public void setRightTitleBg(int bg) {
        tvRightTitle.setBackgroundResource(bg);
    }

    public void setRightIcon(int resId) {
        if (resId == 0) {
            ivRightIcon.setVisibility(View.GONE);
        } else {
            ivRightIcon.setVisibility(View.VISIBLE);
            ivRightIcon.setImageResource(resId);
        }
    }

    public void setLeftTitleSize(float f){
        tvLeftTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, f);
    }
}
