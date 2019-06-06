package ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.lib.R;

/**
 * Created by ASUS on 2017/10/6.
 */

public class IToast {
    private static final int DEFAULT_DURATION = Toast.LENGTH_LONG;
    private static Toast toast;
    /**
     * 展示toast==LENGTH_SHORT
     *
     * @param msg
     */
    public static void show(String msg, Context context) {
        show(msg, Toast.LENGTH_SHORT, context);
    }

    /**
     * 展示toast==LENGTH_LONG
     *
     * @param msg
     */
    public static void showLong(String msg, Context context) {
        show(msg, Toast.LENGTH_LONG, context);
    }
    public static void showDialog(Context context, String msg, @DrawableRes int icon){
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView title = view.findViewById(R.id.title);
        ImageView iv = view.findViewById(R.id.dialog_icon);
//        //设置显示的内容
        iv.setImageResource(icon);
        title.setText(msg);
        if(toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = new Toast(context);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER, 0, 0);
        //设置显示时间
        toast.setDuration(DEFAULT_DURATION);
        toast.setView(view);
        toast.show();
    }
    private static void show(String massage, int show_length, Context context) {
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView title = view.findViewById(R.id.title);
//        //设置显示的内容
        title.setText(massage);
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
    public static void show(Context context) {
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout1, null);
     /*   TextView title = view.findViewById(R.id.title);
//        //设置显示的内容
        title.setText(massage);
        if(toast != null) {
            toast.cancel();
            toast = null;
        }*/
        toast = new Toast(context);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER, 0, 0);
        //设置显示时间
        toast.setView(view);
        toast.show();
    }

    public static void showAlert(Context context) {
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout_get_success, null);
     /*   TextView title = view.findViewById(R.id.title);
//        //设置显示的内容
        title.setText(massage);
        if(toast != null) {
            toast.cancel();
            toast = null;
        }*/
        toast = new Toast(context);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER, 0, 0);
        //设置显示时间
        toast.setView(view);
        toast.show();
    }
    public static void showCopySuccess(Context context) {
        //使用布局加载器，将编写的toast_layout布局加载进来
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout_copy_success, null);
     /*   TextView title = view.findViewById(R.id.title);
//        //设置显示的内容
        title.setText(massage);
        if(toast != null) {
            toast.cancel();
            toast = null;
        }*/
        toast = new Toast(context);
        //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移70个单位，
        toast.setGravity(Gravity.CENTER, 0, 0);
        //设置显示时间
        toast.setView(view);
        toast.show();
    }

}