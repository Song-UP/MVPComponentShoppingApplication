package com.android.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.lib.R;

/**
 * Created by ASUS on 2017/11/3.
 */

public class BottomDialog extends Dialog {

    public LinearLayout options_ll;
    public TextView title;
    public View title_line;

    public BottomDialog(Context context) {
        //给dialog定制了一个主题（透明背景，无边框，无标题栏，浮在Activity上面，模糊）
        super(context, R.style.ios_bottom_dialog);
        setContentView(R.layout.ios_bottom_dialog);
        initView();
    }

    public interface OnOptionClickListener {
        void onOptionClick();
    }

    public static class Option {
        private String name;
        private int color;
        private BottomDialog.OnOptionClickListener listener;

        public Option() {
        }

        Option(String name, int color, BottomDialog.OnOptionClickListener listener) {
            this.name = name;
            this.color = color;
            this.listener = listener;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        BottomDialog.OnOptionClickListener getListener() {
            return listener;
        }

        public void setListener(BottomDialog.OnOptionClickListener listener) {
            this.listener = listener;
        }
    }

    private void initView() {
        title = findViewById(R.id.bottom_dialog_title_tv);
        title_line = findViewById(R.id.bottom_dialog_title_line);
        options_ll = findViewById(R.id.options_ll);

        findViewById(R.id.bottom_dialog_cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialog.this.dismiss();
            }
        });
        //点击空白区域可以取消dialog
        this.setCanceledOnTouchOutside(true);
        //点击back键可以取消dialog
        this.setCancelable(true);
        Window window = this.getWindow();
        //让Dialog显示在屏幕的底部
        window.setGravity(Gravity.BOTTOM);
        //设置窗口出现和窗口隐藏的动画
        window.setWindowAnimations(R.style.ios_bottom_dialog_anim);
        //设置BottomDialog的宽高属性
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }
}