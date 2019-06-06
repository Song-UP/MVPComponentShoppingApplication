package umeng;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.drakeet.support.toast.ToastCompat;

import com.android.lib.R;

import java.util.Date;

import me.drakeet.support.toast.ToastCompat;
import utils.Utils;

public class UpdateDialog extends BaseDialog implements View.OnClickListener {
    public static final String VERSION = "VERSION";

    private Version version;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            version = bundle.getParcelable(VERSION);
        } else {
            throw new IllegalArgumentException("没有传递升级参数");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tvVersion = getView().findViewById(R.id.tvVersion);
        TextView tvLog = getView().findViewById(R.id.tvLog);
        Button btnUpdate = getView().findViewById(R.id.btnOK);
        Button btnIgnore = getView().findViewById(R.id.btnCancle);

        btnUpdate.setOnClickListener(this);
        btnIgnore.setOnClickListener(this);

        tvVersion.setText(String.format("最新版本：V%d", version.code));
        tvLog.setText(version.note == null ? "暂无更新日志" : version.note);

        if (version.isForce == 1) {
            btnIgnore.setVisibility(View.GONE);
        } else {
            btnIgnore.setVisibility(View.VISIBLE);
        }
        //设置是否允点击返回键和out区域返回
        setOnOutAndBackCancle(false, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_dialog_custom_define, null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnOK) {
            if (isWifiStatus(getActivity())) {
                startDownload();
            } else {
                AlertDialog infoDialog = new AlertDialog.Builder(getActivity()).setTitle("网络提示")
                        .setMessage("正在使用移动网络，是否继续下载？")
                        .setPositiveButton("继续下载", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startDownload();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                infoDialog.show();
            }
        } else if (id == R.id.btnCancle) {
            UpdateUtils.addIgnoreVersion(getActivity(), new Date().getTime(), version.code);
            ToastCompat.makeText(getActivity().getApplicationContext(), "更新已取消", Toast.LENGTH_LONG).show();
            dismissAllowingStateLoss();
        }
    }

    private final int PERMISSION_CODE = 15;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_CODE && grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            download();
        } else {
            Utils.showToast("因为要下载最新安装包，请授权APP的应用存储权限");
        }
    }

    private void startDownload() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
        } else {
            download();
        }
    }

    private void download(){
        DownloadDialog dialog = new DownloadDialog(getActivity());
        dialog.showDownload(getActivity(), version, getArguments().getString("app_id"));
        dismissAllowingStateLoss();
    }

    private boolean isWifiStatus(Context context) {
        String net = Utils.getNetworkType(context);
        return net != null && net.equals("wifi");
    }
}