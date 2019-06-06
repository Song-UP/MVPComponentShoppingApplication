package me.jessyan.armscomponent.commonres.weight;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseLinearLayout extends LinearLayout {
    public Context mContext;
    Unbinder unbinder;



    public BaseLinearLayout(Context context) {
        this(context, null, 0);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(mContext, attrs, defStyleAttr);
        initView(mContext);
        load(mContext);
    }

    public void setContentView(@LayoutRes int resId) {
        View view = LayoutInflater.from(mContext).inflate(resId, this,true);
        initButterKnife();
    }

    @Override
    protected void detachViewFromParent(View child) {
        super.detachViewFromParent(child);
        if (unbinder != null)
            unbinder.unbind();
    }

    public synchronized void initButterKnife() {
        unbinder=ButterKnife.bind(this, this);
    }

    protected abstract void init(Context context, AttributeSet attrs, int defStyleAttr);

    protected abstract void initView(Context context);

    protected abstract void load(Context context);

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
}
