package com.song.songup.shoppingapp.mvp.ui.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.songup.shoppingapp.R;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.weight.BaseLinearLayout;
import timber.log.Timber;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/4/29 19:17
 * 修改备注：
 */
public class ActivityItemView extends BaseLinearLayout {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_name)
    TextView tvName;

    public ActivityItemView(Context context) {
        super(context);
    }

    public ActivityItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActivityItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setContentView(R.layout.view_item_activity);

        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.CENTER);

        //获取自定义的属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ActivityItemView, defStyleAttr, 0);
        String bottomText = typedArray.getString(R.styleable.ActivityItemView_bottomText);
        int topSrc = typedArray.getResourceId(R.styleable.ActivityItemView_topSrc,0);
        typedArray.recycle();   //注意typeArray的回收

        setText(tvName, bottomText);
        setSrc(ivTop, topSrc);
    }

    @Override
    protected void initView(Context context) {

    }

    @Override
    protected void load(Context context) {

    }


    public class TypeArrayTemp {
        int selTextColor;
        int unSelTextColor;
        boolean isSel;

    }

}
