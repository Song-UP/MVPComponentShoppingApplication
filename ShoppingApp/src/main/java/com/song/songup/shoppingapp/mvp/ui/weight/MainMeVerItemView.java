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
public class MainMeVerItemView extends BaseFramelayout {

    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;

    public MainMeVerItemView(Context context) {
        super(context);
    }

    public MainMeVerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainMeVerItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setContentView(R.layout.view_item_main_me_verti);
        //获取自定义的属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainMeVerItemView, defStyleAttr, 0);

        int topSrc = typedArray.getResourceId(R.styleable.MainMeVerItemView_topSrc,-1);
        if (topSrc != -1)
            ivTop.setImageResource(topSrc);

        String bottomText = typedArray.getString(R.styleable.MainMeVerItemView_bottomText);
        if (!TextUtils.isEmpty(bottomText))
            tvBottom.setText(bottomText);
        typedArray.recycle();
    }

    @Override
    protected void initView(Context context) {

    }

    @Override
    protected void load(Context context) {

    }
}
