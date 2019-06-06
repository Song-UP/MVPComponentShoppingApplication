package com.android.lib.dialog;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/11/3.
 */

//这个类保存了dialog的众多参数
class Paraments {
    public String title;
    public int titleColor;
    public boolean cancelable;
    public List<BottomDialog.Option> options;

    public Paraments() {
        title = "";
        titleColor = Color.BLACK;
        cancelable = true;
        options = new ArrayList();
    }
}
