package com.android.lib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.lib.R;

/**
 * Created by Administrator on 2015/7/21.
 */
public class NetWaitDialog extends BaseDialog {
    int size = 70;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_net_wait, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setShowPosition(WindowManager.LayoutParams lp) {
        super.setShowPosition(lp);

        DisplayMetrics metric = new DisplayMetrics();
        WindowManager vm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        vm.getDefaultDisplay().getMetrics(metric);
        lp.width = (int) metric.density * size;
        lp.height = (int) metric.density * size;
    }

    public void show(FragmentActivity activity) {
        setOnOutAndBackCancle(false, false);

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(this, null);
        transaction.commitAllowingStateLoss();
    }

    public void show(Fragment fragment) {
        setOnOutAndBackCancle(false, false);

        FragmentTransaction transaction = fragment.getChildFragmentManager().beginTransaction();
        transaction.add(this, null);
        transaction.commitAllowingStateLoss();
    }
}
