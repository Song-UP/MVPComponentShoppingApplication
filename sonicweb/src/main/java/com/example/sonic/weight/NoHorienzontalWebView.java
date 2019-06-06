package com.example.sonic.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/28 12:55
 * 修改备注：
 */
public class NoHorienzontalWebView extends WebView {
    public NoHorienzontalWebView(Context context) {
        super(context);
    }

    public NoHorienzontalWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoHorienzontalWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //重写onScrollChanged 方法
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollTo(0,t);
    }
}
