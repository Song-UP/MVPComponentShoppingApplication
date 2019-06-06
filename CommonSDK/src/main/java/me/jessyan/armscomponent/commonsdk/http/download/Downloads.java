package me.jessyan.armscomponent.commonsdk.http.download;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

/**
 * 注意下载安装的时候，需要
 * 1.权限  REQUEST_INSTALL_PACKAGES    WRITE_EXTERNAL_STORAGE，READ_EXTERNAL_STORAGE(普通权限)
 * 2.清单文件 provider (此时app_id已经没用用了，需要的时候再开启吧，它是包名  包名+.fileprovider)
 * 3.保证 清单文件的 authority 与 FileProvider.getUriForFile(context,"com.demo.fileprovider", file);保持一致
 *
 * 使用
 *  downloads = new Downloads(ZhihuHomeActivityActivity.this, handler, "");
 *   downloads.submit("下载", "http://bxvip.oss-cn-zhangjiakou.aliyuncs.com/bxvip/androidapk/985zy.apk");
 *
 *销毁
 * downloads.distroy()
 *
 *
 */

public class Downloads {
    public final static int PROCESS = 0x1122;
    public final static int FINISHED = 0x1123;
    final Context context;
    final Handler handler;
    long downloadId = -1;
    private String app_id;
    private final int PERMISSION_CODE = 150;
    public Downloads(Context context, Handler handler, String app_id) {
        this.context = context;
        this.handler = handler;
        this.app_id = app_id;
        this.context.registerReceiver(downloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        this.context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, contentObserver);
    }

    public void destroy() {
        try {
            if (downloadId != -1) {
                DownloadManager download = getDownLoadManager();
                download.remove(downloadId);
            }
            this.context.getContentResolver().unregisterContentObserver(contentObserver);
            this.context.unregisterReceiver(downloadReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submit(String name, String url) {
        try {
            DownloadManager download = getDownLoadManager();
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(String.valueOf(name));
            //设置下载存放的文件夹和文件名字
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "lottery.apk");

            downloadId = download.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DownloadManager getDownLoadManager() {
        return (DownloadManager) this.context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    private final BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (reference == -1 || reference != downloadId) return;
            // 下载完成，自动安装
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(reference);
            DownloadManager download = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor cursor = download.query(query);
            try {
                if(cursor != null) {
                    if (cursor.moveToFirst()) {
//                        int fileNameIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
//                        String fileName = cursor.getString(fileNameIdx);
                        int fileUriIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                        String fileUri = cursor.getString(fileUriIdx);
                        String fileName = null;
                        if (fileUri != null) {
                            File mFile = new File(Uri.parse(fileUri).getPath());
                            fileName = mFile.getAbsolutePath();
                        }
                        if (fileName != null) {
                            if (fileName.endsWith(".apk")) {
                               /* if(Build.VERSION.SDK_INT >= 26  &&  ContextCompat.checkSelfPermission(context, Manifest.permission.REQUEST_INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, PERMISSION_CODE);
                                } else {*/
                                if (Build.VERSION.SDK_INT >= 26){
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, PERMISSION_CODE);
                                }
                                    if(Build.VERSION.SDK_INT>=24) {
                                        //判读版本是否在7.0以上
                                        File file= new File(fileName);
                                        //TODO 注意这里需要和清单文件保持一致 authority
                                        Uri apkUri = FileProvider.getUriForFile(context,"com.demo.fileprovider", file);
                                        Intent install = new Intent(Intent.ACTION_VIEW);
                                        // 由于没有在Activity环境下启动Activity,设置下面的标签
                                        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                                        context.startActivity(install);
                                    } else{
                                        Intent install = new Intent(Intent.ACTION_VIEW);
                                        install.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                                        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(install);
                                    }
                                }


                        }
                    }
                }
            } finally {
                if(cursor != null) {
                    cursor.close();
                }
            }
        }
    };

    private final ContentObserver contentObserver = new ContentObserver(null) {

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            if (downloadId == -1) return;
            DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor cursor = downloadManager.query(query);
            while (cursor.moveToNext()) {
                int mDownload_so_far = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int mDownload_all = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                int mProgress = (mDownload_so_far * 99) / mDownload_all;
                Log.d("TAG_PROCESS", String.valueOf(mProgress));
                if (handler != null) {
                    Message msg = Message.obtain();
                    msg.what = PROCESS;
                    msg.obj = new Integer(mProgress);
                    handler.sendMessage(msg);
                }
            }
        }
    };
}