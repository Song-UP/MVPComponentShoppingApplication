package com.android.lib.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.android.lib.R;

/**
 * Created by Administrator on 2015/3/30.
 */
public abstract class BaseDialog extends DialogFragment implements DialogInterface.OnKeyListener, View.OnClickListener {
    //对话框是否允许取消标志位
    boolean outCancleFlag = true;
    boolean backCancleFlag = true;

    public BaseDialog() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化额外参数
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //自定义对话框样式
        return inflater.inflate(R.layout.dialog_fragment_main, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setOnKeyListener(this);
        setOnOutAndBackCancle(outCancleFlag, backCancleFlag);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//No Title style
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        //调整对话框的弹出位置和大小，设置弹出动画
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        LayoutParams lp = window.getAttributes();
        setShowPosition(lp);
        window.setAttributes(lp);
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && backCancleFlag) {
            onBack();
            dismiss();
        }
        return true;
    }

    /**
     * @param outCancleFlag  true:能取消
     * @param backCancleFlag true:能取消
     */
    public void setOnOutAndBackCancle(Boolean outCancleFlag, Boolean backCancleFlag) {
        if (outCancleFlag != null) {
            this.outCancleFlag = outCancleFlag;
            if (getDialog() != null) {
                getDialog().setCanceledOnTouchOutside(outCancleFlag);
            }
        }
        if (backCancleFlag != null) {
            this.backCancleFlag = backCancleFlag;
        }
    }

    /**
     * 默认从中间位置弹出，不带动画效果
     */
    public void setShowPosition(LayoutParams lp) {
        lp.gravity = Gravity.CENTER;// 对话框基准位置
        lp.x = 0; // 相对基准的x偏移
        lp.y = 0; // y偏移
        lp.dimAmount = 0.5f;// 对话框的遮蔽层透明度(0:完全透明)
        //lp.windowAnimations = R.style.dialog_center;
    }

    /**
     * 返回键对话框消失，接口回调
     */
    public void onBack() {

    }

    /**
     * 点击外部区域对话框消失，接口回调
     *
     * @param dialog
     */
    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onClick(View v) {

    }
}