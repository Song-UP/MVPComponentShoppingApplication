package com.song.songup.shoppingapp.mvp.ui.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.songup.shoppingapp.R;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.weight.BaseFramelayout;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/13 20:31
 * 修改备注：
 */
public class MainMeItemView extends BaseFramelayout {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;

    public MainMeItemView(Context context) {
        super(context);
    }

    public MainMeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainMeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setContentView(R.layout.view_item_main_me_hor);
        //获取自定义的属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainMeItemView, defStyleAttr, 0);
        int leftSrc = typedArray.getResourceId(R.styleable.MainMeItemView_leftSrc, -1);
        if (leftSrc == -1) {
            ivLeft.setVisibility(GONE);
        } else {
            ivLeft.setImageResource(leftSrc);
        }
        String titleText = typedArray.getString(R.styleable.MainMeItemView_title);
        if (!TextUtils.isEmpty(titleText)) {
            tv_title.setText(titleText);
        }

        String rightText = typedArray.getString(R.styleable.MainMeItemView_rightText);
        if (TextUtils.isEmpty(rightText)) {
            tvRight.setVisibility(GONE);
        } else {
            tvRight.setText(rightText);
        }

        boolean isShowRightIv = typedArray.getBoolean(R.styleable.MainMeItemView_isShowRightIv,true);
        if (!isShowRightIv) {
            ivRight.setVisibility(GONE);
        }
        typedArray.recycle();
    }

    @Override
    protected void initView(Context context) {

    }

    @Override
    protected void load(Context context) {

    }
}
