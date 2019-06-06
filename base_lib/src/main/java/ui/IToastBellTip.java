package ui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.lib.R;

/**
 * Created by ASUS on 2017/10/6.
 */

public class IToastBellTip {
    private static Toast toast;

    public static void show(Context context) {
        show(Toast.LENGTH_SHORT, context);
    }

    public static void showLong(Context context) {
        show(Toast.LENGTH_LONG, context);
    }

    private static void show(int show_length, Context context) {
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout_tip, null);
        if(toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = new Toast(context);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER, 0, 0);
        //设置显示时间
        toast.setDuration(show_length);
        toast.setView(view);
        toast.show();
    }
}