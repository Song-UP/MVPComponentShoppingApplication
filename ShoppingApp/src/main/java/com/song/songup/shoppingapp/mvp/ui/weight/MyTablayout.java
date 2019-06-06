package com.song.songup.shoppingapp.mvp.ui.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.song.songup.shoppingapp.R;

import butterknife.BindView;
import timber.log.Timber;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/15 15:56
 * 修改备注：
 */
public class MyTablayout extends BaseTabView {
    @BindView(R.id.textview)
    TextView textview;
    @BindView(R.id.view_line)
    View viewLine;

    private String selText;//选中时文字

    private int selTextColor = -1;//选中时文字
    private int unSelTextColor = -1;//非选中文字

    private boolean isSel = false;// 是否选中
    private boolean isFirst = true;//是否时第一次设置
    private int tabIndicatorColor;//是否时第一次设置

    TypeArrayTemp typeArrayTemp;


    public MyTablayout(Context context) {
        super(context);
    }

    public MyTablayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTablayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setContentView(R.layout.layout_item_tablayout);

        //获取自定义的属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTablayout, defStyleAttr, 0);
        this.selText = typedArray.getString(R.styleable.MyTablayout_selText);

        this.unSelTextColor = typedArray.getColor(R.styleable.MyTablayout_unselTextColor, -1);
        this.selTextColor = typedArray.getColor(R.styleable.MyTablayout_selTextColor, -1);

        this.tabIndicatorColor = typedArray.getColor(R.styleable.MyTablayout_selTextColor, -1);

        this.typeArrayTemp = new TypeArrayTemp();
        this.typeArrayTemp.selTextColor = selTextColor;
        this.typeArrayTemp.unSelTextColor = unSelTextColor;
        this.typeArrayTemp.isSel = isSel;
        this.tabIndicatorColor = tabIndicatorColor;

        typedArray.recycle();   //注意typeArray的回收

        textview.setText(selText);
        viewLine.setBackgroundColor(tabIndicatorColor);

        change(isSel);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("" + selTextColor);
            }
        });


    }

    @Override
    protected void initView(Context context) {

    }

    @Override
    protected void load(Context context) {

    }

    public void change(boolean toIsSel) {
        reset();
        if (isFirst && (toIsSel == isSel))
            return;
        if (toIsSel) {
            textview.setTextColor(selTextColor);
            viewLine.setAlpha(1);
        } else {
            textview.setTextColor(unSelTextColor);
            viewLine.setAlpha(0);
        }
        isSel = toIsSel;
        isFirst = false;
    }

    public void reset() {
        if (unSelTextColor == -1 && this.typeArrayTemp != null) {
            unSelTextColor = this.typeArrayTemp.unSelTextColor;
            selTextColor = this.typeArrayTemp.selTextColor;
            isSel = this.typeArrayTemp.isSel;
            this.tabIndicatorColor = typeArrayTemp.tabIndicatorColor;
            this.typeArrayTemp = null;

        }
    }

    public class TypeArrayTemp {
        int selTextColor;
        int unSelTextColor;
        boolean isSel;
        int tabIndicatorColor;

    }

}
