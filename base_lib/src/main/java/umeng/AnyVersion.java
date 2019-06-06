package umeng;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AnyVersion {

    private static final String TAG = "AnyVersion";

    private static final Lock LOCK = new ReentrantLock();

    private static AnyVersion ANY_VERSION = null;

    Context context;

    final VersionParser parser;

    private Future<?> workingTask;
    private String url;
    private RemoteHandler remoteHandler;
    private final Handler mainHandler;
    private final ExecutorService threads;

    private FragmentManager manager;

    public static AnyVersion getInstance() {
        try {
            LOCK.lock();
            if (ANY_VERSION == null) {
                throw new IllegalStateException("AnyVersion NOT init !");
            }
            return ANY_VERSION;
        } finally {
            LOCK.unlock();
        }
    }

    private AnyVersion(final Context context, String url, VersionParser parser) {
        Log.d(TAG, "AnyVersion init...");
        this.context = context;
        this.url = url;
        this.parser = parser;
        this.threads = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Version version = (Version) msg.obj;
                if (msg.what == 1) {
                    UpdateDialog dialog = new UpdateDialog();
                    Bundle args = new Bundle();
                    args.putParcelable(UpdateDialog.VERSION, version);
                    dialog.setArguments(args);

                    FragmentTransaction transaction = manager.beginTransaction();
                    if(dialog != null) {
                        transaction.add(dialog, "update");
                    }
                    transaction.commitAllowingStateLoss();
                }
            }
        };
        String versionName = null;
        int versionCode = 0;
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
//        currentVersion = new Version(versionName, null, null, versionCode, versionCode, 0);
    }

    /**
     * 初始化 AnyVersion。
     *
     * @param context 必须是 Application
     * @param parser  服务端响应数据解析接口
     */
    public static void init(Context context, String url, VersionParser parser) {
        Preconditions.requiredMainUIThread();
        try {
            LOCK.lock();
            if (ANY_VERSION != null) {
                Log.e(TAG, "Duplicate init AnyVersion ! This VersionParser  will be discard !");
                Log.e(TAG, "AnyVersion recommend init on YOUR-Application.onCreate(...) .");
                return;
            }
        } finally {
            LOCK.unlock();
        }
        if (context == null) {
            throw new NullPointerException("Application Context CANNOT be null !");
        }
        if (parser == null) {
            throw new NullPointerException("Parser CANNOT be null !");
        }
        ANY_VERSION = new AnyVersion(context, url, parser);
    }

    /**
     * 设置自定义检测远程版本数据的接口
     */
    public void setCustomRemote(RemoteHandler remoteHandler) {
        Preconditions.requireInited();
        if (remoteHandler == null) {
            throw new NullPointerException("RemoteHandler CANNOT be null !");
        }
        this.remoteHandler = remoteHandler;
    }

    /**
     * 检测新版本，并指定发现新版本的处理方式
     * isIgnore:是否跳过本地忽略版本
     */
    public void check(FragmentManager manager, boolean isSkipIgnore) {
        this.manager = manager;
        createRemoteRequestIfNeed();
        check(this.url, isSkipIgnore, this.remoteHandler);
    }

    public void check(FragmentManager manager, String url, boolean isSkipIgnore) {
        this.url = url;
        this.manager = manager;
        createRemoteRequestIfNeed();
        check(this.url, isSkipIgnore, this.remoteHandler);
    }

    public void check(String url, Callback callback) {
        this.url = url;
        createRemoteRequestIfNeed();
        check(url, callback, this.remoteHandler);
    }

    private void check(final String url, final boolean isSkipIgnore, final RemoteHandler remote) {
//        Preconditions.requireInited();
//        final Callback core = new Callback() {
//            @Override
//            public void onVersion(Version remoteVersion) {
//                if (remoteVersion == null) return;
//                // 检查是否为新版本
//                boolean isUpdate = UpdateUtils.isRequireUpdate(currentVersion.code, remoteVersion.code);
//                if (isUpdate && isSkipIgnore) {
//                    boolean isIgnore = UpdateUtils.isIgnoreVersion(context, remoteVersion.code);
//                    isUpdate = isIgnore ? false : isUpdate;
//                }
//                remoteVersion.isForce = currentVersion.code < remoteVersion.minCode ? 1 : 0;
//                final Message msg = Message.obtain(ANY_VERSION.mainHandler, isUpdate ? 1 : 0, remoteVersion);
//                msg.sendToTarget();
//            }
//        };
//        remote.setOptions(url, parser, core);
//        cancelCheck();
//        workingTask = threads.submit(remote);
    }

    private void check(final String url, final Callback callback, final RemoteHandler remote) {
        Preconditions.requireInited();
        remote.setOptions(url, parser, callback);
        cancelCheck();
        workingTask = threads.submit(remote);
    }

    /**
     * 取消当前正在检测的工作线程
     */
    public void cancelCheck() {
        Preconditions.requireInited();
        if (workingTask != null && !workingTask.isDone()) {
            workingTask.cancel(true); // force interrupt
        }
    }

    private void createRemoteRequestIfNeed() {
        if (remoteHandler == null) {
            // 使用内置请求时，URL 地址是必须的。
            checkRequiredURL(this.url);
            remoteHandler = new SimpleRemoteHandler();
        }
    }

    private void checkRequiredURL(String url) {
        if (TextUtils.isEmpty(url)) {
//            throw new NullPointerException("URL CANNOT be null or empty !");
        }
    }
}
