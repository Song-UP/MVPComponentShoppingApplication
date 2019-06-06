package com.song.songup.shoppingapp;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.jess.arms.base.BaseApplication;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import me.jessyan.armscomponent.commonsdk.utils.count_down.CountDownManager;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/18 17:48
 * 修改备注：
 */
public class MyApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        // 安装tinker
//        Beta.installTinker();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CountDownManager.asInstance().startCountDown();
//        RongCloudChatManager.init(this);
        initBuggly();
    }

    public void initBuggly(){
        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly

        Bugly.init(context, "c9e666c5fb", BuildConfig.DEBUG, strategy);

//        CrashReport.initCrashReport(context, "c9e666c5fb", BuildConfig.DEBUG, strategy);
// 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
// CrashReport.initCrashReport(context, strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
