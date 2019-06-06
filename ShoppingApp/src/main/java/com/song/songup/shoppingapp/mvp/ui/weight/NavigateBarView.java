package com.song.songup.shoppingapp.mvp.ui.weight;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.songup.shoppingapp.R;
import com.song.songup.shoppingapp.util.BarUtils;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.weight.BaseFramelayout;

/**
 * Created by ASUS on 2017/7/25.
 * 不能滑动的viewpager
 */

public class NavigateBarView extends BaseFramelayout {

    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.tvLeftTitle)
    TextView tvLeftTitle;
    @BindView(R.id.tvCenterTitle)
    TextView tvCenterTitle;
    @BindView(R.id.ivOperate)
    ImageView ivOperate;
    @BindView(R.id.tvOperate)
    TextView tvOperate;
    @BindView(R.id.lines)
    View lines;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;

    public NavigateBarView(Context context) {
        super(context);
    }

    public NavigateBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigateBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setContentView(R.layout.view_navigate_bar);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigateBar);
        boolean retEnable = a.getBoolean(R.styleable.NavigateBar_navigate_return_enable, true);
        int leftIconSrcId = a.getResourceId(R.styleable.NavigateBar_navigate_bar_icon, 0);
        String leftText = a.getString(R.styleable.NavigateBar_navigate_bar_left_title);
        String centerText = a.getString(R.styleable.NavigateBar_navigate_bar_center_title);
        String rightText = a.getString(R.styleable.NavigateBar_navigate_bar_right_title);
        int rightIconSrcId = a.getResourceId(R.styleable.NavigateBar_navigate_bar_right_src, 0);

        a.recycle();

        if (retEnable) {
            ivIcon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            });
        }

        setSrc(ivIcon, leftIconSrcId);
        setText(tvLeftTitle, leftText);

        setText(tvCenterTitle, centerText);

        setSrc(ivOperate, rightIconSrcId);
        setText(tvOperate, rightText);

        BarUtils.addMarginTopEqualStatusBarHeight(framelayout);

    }

    public void setText(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            if (textView.getVisibility() != View.VISIBLE)
                textView.setVisibility(VISIBLE);
            textView.setText(text);
        }
    }

    public void setSrc(ImageView imageView, int srcId) {
        if (srcId != 0) {
            if (imageView.getVisibility() != View.VISIBLE)
                imageView.setVisibility(VISIBLE);
            imageView.setImageResource(srcId);
        }

    }

    @Override
    protected void initView(Context context) {


    }

    @Override
    protected void load(Context context) {

    }
}
