package com.android.lib.dialog;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import utils.Utils;

/**
 * Created by ASUS on 2017/11/3.
 */

public class Builder {
    private Paraments p;
    private Context context;

    public Builder(Context context) {
        p = new Paraments();
        this.context = context;
    }

    public Builder setTitle(String title, int color) {
        p.title = title;
        p.titleColor = color;
        return this;
    }

    public Builder addOption(String option, int color, BottomDialog.OnOptionClickListener listener) {
        p.options.add(new BottomDialog.Option(option, color, listener));
        return this;
    }

    public BottomDialog create() {
        final BottomDialog dialog = new BottomDialog(context);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        if (p.title.isEmpty()) {
            //设置标题栏不可见
            dialog.title.setVisibility(View.GONE);
            dialog.title_line.setVisibility(View.GONE);
        } else {
            dialog.title.setText(p.title);
            dialog.title.setTextColor(p.titleColor);
            dialog.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            //设置标题栏可见
            dialog.title.setVisibility(View.VISIBLE);
            dialog.title_line.setVisibility(View.VISIBLE);
        }
        if (p.options.size() == 0) {
            dialog.options_ll.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < p.options.size(); i++) {
                final BottomDialog.Option option = p.options.get(i);
                final TextView optionText = new TextView(context);
                optionText.setPadding(Utils.dip2px(context, 13), Utils.dip2px(context, 13), Utils.dip2px(context, 13), Utils.dip2px(context, 13));
                optionText.setText(option.getName());
                optionText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                optionText.setGravity(Gravity.CENTER);
                optionText.setTextColor(option.getColor());
                optionText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (option.getListener() != null) {
                            option.getListener().onOptionClick();
                        }
                    }
                });
                dialog.options_ll.addView(optionText);
                //添加条目之间的分割线
                if (i != p.options.size() - 1) {
                    View divider = new View(context);
                    divider.setBackgroundColor(Color.BLACK);
                    dialog.options_ll.addView(divider, params);
                }
            }
        }
        return dialog;
    }
}
