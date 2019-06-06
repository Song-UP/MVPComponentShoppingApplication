package com.android.lib.dialog;

import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.lib.R;

/**
 * Created by Administrator on 2015/3/30.
 */
public class AlertDialog extends BaseDialog {
    TextView tvInfo;
    Button btnOK;
    Button btnCancle;
    TextView titleView;

    String title = "";
    Listener listener;
    ListenerParameter listenerParameter;

    String ok;
    String cancel;
    String titleString = null;
    String parameter1;

    String parameter2;

    boolean isRightBold = false;//右侧选项是否加粗

    public AlertDialog() {
        super();
        setOnOutAndBackCancle(true, true);
    }

    public static AlertDialog getInstance(Listener listener, String title) {
        AlertDialog dialog = new AlertDialog();
        dialog.setListener(listener);
        dialog.setTitle(title);
        return dialog;
    }

    public static AlertDialog getInstance(ListenerParameter listener, String title, String s, String t) {
        AlertDialog dialog = new AlertDialog();
        dialog.setParameter(listener, s, t);
        dialog.setTitle(title);
        return dialog;
    }

    public static AlertDialog getInstance(Listener listener, String title, String ok, String cancel) {
        AlertDialog dialog = getInstance(listener, title);
        if (ok != null) {
            dialog.setOk(ok);
        }
        if (cancel != null) {
            dialog.setCancel(cancel);
        }
        return dialog;
    }

    public void setRightBold(){
        isRightBold = true;
    }

    private void setOk(String a) {
        ok = a;
    }

    private void setCancel(String a) {
        cancel = a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_base_alert, null);
    }

    public void setTitleContent(String s){
        titleString = s;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvInfo = getView().findViewById(R.id.tvInfo);
        btnOK = getView().findViewById(R.id.btnOK);
        btnCancle = getView().findViewById(R.id.btnCancle);
        titleView = getView().findViewById(R.id.title);

        tvInfo.setText(title);
        if (ok != null) {
            btnOK.setText(ok);
        }
        if (cancel != null) {
            btnCancle.setText(cancel);
        }
        if(titleString != null){
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(titleString);
        }
        if(isRightBold){
            TextPaint tp = btnOK.getPaint();
            tp.setFakeBoldText(true);
        }

        btnOK.setOnClickListener(this);
        btnCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (listener != null) {
            if (v.getId() == R.id.btnCancle) {
                listener.onClickState(0);
            }
            if (v.getId() == R.id.btnOK) {
                listener.onClickState(1);
            }
        }
        if(listenerParameter != null){
            if (v.getId() == R.id.btnCancle) {
                listenerParameter.onClickState(0, parameter1, parameter2);
            }
            if (v.getId() == R.id.btnOK) {
                listenerParameter.onClickState(1, parameter1, parameter2);
            }
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setParameter(ListenerParameter listener, String s, String b) {
        this.listenerParameter = listener;
        parameter1 = s;
        parameter2 = b;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public interface Listener {
        void onClickState(int state);
    }

    public interface ListenerParameter {
        void onClickState(int state, String id, String type);
    }
}
