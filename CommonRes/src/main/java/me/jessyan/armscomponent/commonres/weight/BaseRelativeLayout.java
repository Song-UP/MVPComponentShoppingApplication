package me.jessyan.armscomponent.commonres.weight;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseRelativeLayout extends RelativeLayout {
    public Context mContext;
    Unbinder unbinder;

    public BaseRelativeLayout(Context context) {
        this(context, null, 0);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(mContext, attrs, defStyleAttr);
        initView(mContext);
        load(mContext);
    }

    public void setContentView(@LayoutRes int resId) {
        LayoutInflater.from(mContext).inflate(resId, this);
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
}
