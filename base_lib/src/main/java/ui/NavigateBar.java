package ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.lib.R;

public class NavigateBar extends LinearLayout implements View.OnClickListener {
    Context context;

    ImageView ivIcon;//左边的Icon
    TextView tvLeftTitle;//左标题
    TextView tvCenterTitle;//标题

    TextView tvOperateTitle;//右边的title
    ImageView ivOperateIcon;//右边Icon

    //click区域
    FrameLayout vIcon;
    FrameLayout vOperate;

    View lines;

    boolean retEnable;
    OnOperateClickListener opeListener;
    OnIconClickListener iconListener;
//    private OnTvOperateTitleClickListener tvOperateTitleListener;

    public NavigateBar(Context context) {
        super(context);
        this.context = context;
    }

    public NavigateBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        addView(LayoutInflater.from(context).inflate(R.layout.view_navigate_bar, null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ivIcon = findViewById(R.id.ivIcon);
        tvLeftTitle = findViewById(R.id.tvLeftTitle);
        tvCenterTitle = findViewById(R.id.tvCenterTitle);
        tvOperateTitle = findViewById(R.id.tvOperate);
        ivOperateIcon = findViewById(R.id.ivOperate);
//        tvOperateTitle.setOnClickListener(this);
        vIcon = findViewById(R.id.vIcon);
        vOperate = findViewById(R.id.vOperate);
        lines = findViewById(R.id.lines);
        vIcon.setOnClickListener(this);
        vOperate.setOnClickListener(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigateBar);
        retEnable = a.getBoolean(R.styleable.NavigateBar_navigate_return_enable, true);
        setIcon(a.getResourceId(R.styleable.NavigateBar_navigate_bar_icon, 0));
        setLeftTitle(a.getString(R.styleable.NavigateBar_navigate_bar_left_title));
        setCenterTitle(a.getString(R.styleable.NavigateBar_navigate_bar_center_title));
        setOperateTitle(a.getString(R.styleable.NavigateBar_navigate_bar_operate_title));
        setOperateIcon(a.getResourceId(R.styleable.NavigateBar_navigate_bar_operate_icon, 0));
        if(a.getBoolean(R.styleable.NavigateBar_has_line, true)){
            lines.setVisibility(VISIBLE);
        } else {
            lines.setVisibility(GONE);
        }
        a.recycle();
    }


    public void setIcon(int resId) {
        ivIcon.setImageResource(resId);
    }

    public void setLeftTitle(String title) {
        tvLeftTitle.setText(title == null ? "" : title);
        if(title != null){
            ivIcon.setVisibility(GONE);
        }
    }

    public void setCenterTitle(String title) {
        tvCenterTitle.setText(title == null ? "" : title);
    }

    public void setOperateTitle(String operateTitle) {
        tvOperateTitle.setText(operateTitle == null ? "" : operateTitle);
    }
    public void setOperateTitleGone(boolean operateTitle) {
        if (operateTitle){
            tvOperateTitle.setVisibility(GONE);
        }else {
            tvOperateTitle.setVisibility(VISIBLE);
        }
    }
    public String getOperateTitle(){
        return tvOperateTitle.getText().toString();
    }

    public void setOperateIcon(int resId) {
        ivOperateIcon.setImageResource(resId);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.vIcon) {
            if (iconListener != null) {
                iconListener.OnIconClick(v);
            } else {
                if (retEnable && getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        } else if (v.getId() == R.id.vOperate) {
            if (opeListener != null) {
                opeListener.onOperateClick(v);
            }
        }
//        else if (v.getId() == R.id.tvOperate) {
//            if (tvOperateTitleListener != null) {
//                tvOperateTitleListener.OnIconClick(v);
//            }
//        }
    }

    public void setOperateIconVisible(boolean v){
        if(v){
            vOperate.setVisibility(VISIBLE);
        } else {
            vOperate.setVisibility(GONE);
        }
    }

    public void setOnOperateClickListener(OnOperateClickListener listener) {
        this.opeListener = listener;
    }

    public void setOnIconClickListener(OnIconClickListener listener) {
        this.iconListener = listener;
    }

//    public void setTvOperateTitleClickListener(OnTvOperateTitleClickListener tvOperateTitleListener ){
//        this.tvOperateTitleListener = tvOperateTitleListener;
//    }

    public interface OnOperateClickListener {
        void onOperateClick(View view);
    }

    public interface OnIconClickListener {
        void OnIconClick(View view);
    }
//    public interface OnTvOperateTitleClickListener {
//        void OnIconClick(View view);
//    }
}