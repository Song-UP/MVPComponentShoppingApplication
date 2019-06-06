package com.song.songup.shoppingapp.mvp.ui.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.songup.shoppingapp.R;

import butterknife.BindView;
import me.jessyan.armscomponent.commonres.weight.BaseFramelayout;
import me.jessyan.armscomponent.commonres.weight.BaseLinearLayout;
import timber.log.Timber;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/4/29 19:17
 * 修改备注：
 */
public class MainTabView extends BaseTabView {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_name)
    TextView tvName;

    private String selText;//选中时文字
//    private String unSelText;//非选中文字

    private int selTextColor = -1;//选中时文字
    private int unSelTextColor = -1;//非选中文字

    private int selIcon;//选中时图标
    private int unSelIcon;//非选中时图标

    private boolean isSel = false;// 是否选中
    private boolean isFirst=true;//是否时第一次设置

    TypeArrayTemp typeArrayTemp;


    public MainTabView(Context context) {
        super(context);
    }

    public MainTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setContentView(R.layout.tab_main_fragment);

        //获取自定义的属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MainTabView, defStyleAttr, 0);
        this.selText = typedArray.getString(R.styleable.MainTabView_selText);

        this.unSelTextColor = typedArray.getColor(R.styleable.MainTabView_unselTextColor,-1);
        this.selTextColor = typedArray.getColor(R.styleable.MainTabView_selTextColor, -1);

        this.unSelIcon = typedArray.getResourceId(R.styleable.MainTabView_unselSrc,-1);
        this.selIcon = typedArray.getResourceId(R.styleable.MainTabView_selSrc,-1);

        this.isSel = typedArray.getBoolean(R.styleable.MainTabView_isSel,false);

        this.typeArrayTemp = new TypeArrayTemp();
        this.typeArrayTemp.selTextColor = selTextColor;
        this.typeArrayTemp.unSelTextColor = unSelTextColor;
        this.typeArrayTemp.isSel = isSel;

        typedArray.recycle();   //注意typeArray的回收

        tvName.setText(selText);

        change(isSel);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               Timber.d(""+selTextColor);
            }
        });


    }

    @Override
    protected void initView(Context context) {

    }

    @Override
    protected void load(Context context) {

    }

    public void change(boolean toIsSel){
        reset();
        if (isFirst && (toIsSel == isSel))
            return;
        if (toIsSel){
            tvName.setTextColor(selTextColor);
            ivTop.setImageResource(selIcon);
        }else {
            tvName.setTextColor(unSelTextColor);
            ivTop.setImageResource(unSelIcon);
        }
        isSel = toIsSel;
        isFirst=false;
    }

    public void reset(){
        if (unSelTextColor == -1  && this.typeArrayTemp != null){
            unSelTextColor = this.typeArrayTemp.unSelTextColor;
            selTextColor = this.typeArrayTemp.selTextColor;
            isSel = this.typeArrayTemp.isSel;
            this.typeArrayTemp = null;

        }
    }

    public class TypeArrayTemp{
        int selTextColor;
        int unSelTextColor;
        boolean isSel;

    }

}
