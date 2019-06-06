package umeng;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import java.util.Date;

public class DownloadDialog extends ProgressDialog {
    private Version version;
    private Handler handler;
    private Downloads downloads;

    public DownloadDialog(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setTitle("正在下载升级包");
        setMax(100);
        setCancelable(false);
        setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    public void showDownload(Context context, final Version version, String app_id) {
        this.version = version;
        if (downloads != null) {
            downloads.destroy();
        }

        if (this.version.isForce == 0) {
            setButton(BUTTON_NEGATIVE, "取消下载", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UpdateUtils.addIgnoreVersion(getContext(), new Date().getTime(), version.code);
                    dismiss();
                }
            });
        }

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == Downloads.PROCESS) {
                    setProgress((Integer) msg.obj);
                }
                return true;
            }
        });
        downloads = new Downloads(context, handler, app_id);

        downloads.submit("下载", version.URL);
        show();
    }

    @Override
    public void dismiss() {
        if (downloads != null) {
            downloads.destroy();
            downloads = null;
        }
        super.dismiss();
    }
}