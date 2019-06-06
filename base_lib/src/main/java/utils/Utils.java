package utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.drakeet.support.toast.ToastCompat;

import com.android.lib.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ASUS on 2017/7/26.
 */

public class Utils {
    private static Application appContext;

    public static void init(Application context) {
        appContext = context;
    }


    // Parser Json 裡的密文
    public static ArrayList<String> getRsaUrlfromJson(String jsonStr){
        ArrayList<String> arrStr = new ArrayList<>();
        String str = jsonStr.replace("name", "");
        StringTokenizer st = new StringTokenizer(str, "[{\":]},");
        while(st.hasMoreTokens()) {
            arrStr.add(st.nextToken());
        }
        return arrStr;
    }
    // 判断一个字符串是否含有数字
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches())
            flag = true;
        return flag;
    }

    //判断当前线程到底是主线程, 还是子线程
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 该方法主要使用正则表达式来判断字符串中是否包含字母
     */
    public static boolean judgeContainsStr(String cardNum) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m=Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    /**@param bMute 值为true时为关闭背景音乐。*/
    @TargetApi(Build.VERSION_CODES.FROYO)
    public static boolean muteAudioFocus(Context context, boolean bMute) {
        if(context == null){
            Log.d("ANDROID_LAB", "context is null.");
            return false;
        }
        boolean bool;
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if(bMute){
            int result = am.requestAudioFocus(null,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        }else{
            int result = am.abandonAudioFocus(null);
            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        }
        Log.d("ANDROID_LAB", "pauseMusic bMute="+bMute +" result="+bool);
        return bool;
    }

    public static String GetCountryZipCode(Context context){
        String CountryID;
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getNetworkCountryIso().toUpperCase();
        String[] rl=context.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 禁止EditText输入空格
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText){
        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(" "))return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

     /*---------------------------------
     * 绘制图片
     * @param       x屏幕上的x坐标
     * @param       y屏幕上的y坐标
     * @param       w要绘制的图片的宽度
     * @param       h要绘制的图片的高度
     * @param       bx图片上的x坐标
     * @param       by图片上的y坐标
     *
     * @return      null
     ------------------------------------*/

    public static void drawImage(Canvas canvas, Bitmap blt, int x, int y,
                                 int w, int h, int bx, int by) {
        Rect src = new Rect();// 图片 >>原矩形
        Rect dst = new Rect();// 屏幕 >>目标矩形

        src.left = bx;
        src.top = by;
        src.right = bx + w;
        src.bottom = by + h;

        dst.left = x;
        dst.top = y;
        dst.right = x + w;
        dst.bottom = y + h;
        // 画出指定的位图，位图将自动--》缩放/自动转换，以填补目标矩形
        // 这个方法的意思就像 将一个位图按照需求重画一遍，画后的位图就是我们需要的了
        canvas.drawBitmap(blt, null, dst, null);
    }

    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };
    private static String toHexString(byte[] b) {
        //String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 获取md5
     * @param s
     * @return
     */
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * md5 32 小写
     *
     * @param text
     * @return
     */
    public static String encode(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }
    /**
     * 获取网络状态
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        String networkType = "wifi";

        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                int subType = networkInfo.getSubtype();
                switch (subType) {
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        networkType = "2g";  // ~ 50-100 kbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        networkType = "2g"; // ~ 14-64 kbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        networkType = "2g"; // ~ 50-100 kbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        networkType = "3g"; // ~ 400-1000 kbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        networkType = "3g"; // ~ 600-1400 kbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        networkType = "2g"; // ~ 100 kbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        networkType = "3g"; // ~ 2-14 Mbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        networkType = "3g"; // ~ 700-1700 kbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        networkType = "3g"; // ~ 1-23 Mbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        networkType = "3g"; // ~ 400-7000 kbps
                        break;
                    /*
                      * Above API level 7, make sure to set android:targetSdkVersion to appropriate level to use these
                    */
                    case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                        networkType = "3g"; // ~ 1-2 Mbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                        networkType = "3g"; // ~ 5 Mbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                        networkType = "4g"; // ~ 10-20 Mbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                        networkType = "2g"; // ~25 kbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                        networkType = "4g"; // ~ 10+ Mbps
                        break;
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        break;
                    default:
                        break;
                }
            }
        } else {
            networkType = null;
        }

        return networkType;
    }

    /**
     * 获取versioncode
     * @return
     */
    public static int getVersionCode() {
        int version = 0;
        try {
            PackageInfo pInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
            version = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            return version;
        }
    }

    public static String getVersionName() {
        String version = null;
        try {
            PackageInfo pInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            return version;
        }
    }

    /**
     * 获取随机数
     * @param start
     * @param end
     * @return
     */
    public static int getRandom(int start, int end){//[start, end)
        long l = System.currentTimeMillis();
        return start + (int)(l % end);
    }

    public static ArrayList<int[]> results = new ArrayList<>();

    public static void combine(int[] a, int n) {
        results.clear();
        if(null == a || a.length == 0 || n <= 0 || n > a.length)
            return;

        int[] b = new int[n];//辅助空间，保存待输出组合数
        getCombination(a, n , 0, b, 0);
    }

    private static void getCombination(int[] a, int n, int begin, int[] b, int index) {
        if(n == 0){//如果够n个数了，输出b数组
            int[] result = b.clone();
            results.add(result);
            return;
        }

        for(int i = begin; i < a.length; i++){
            b[index] = a[i];
            getCombination(a, n-1, i+1, b, index+1);
        }
    }
    /**
     * 把网络图片保存到本地相册
     * @param context
     * @param url
     */
    public static void saveImageLocal(final Context context, String url){
        new AsyncTask<FutureTarget<Bitmap>, Void, Bitmap>() {
            @Override protected Bitmap doInBackground(FutureTarget<Bitmap>... params) {
                try {
                    return params[0].get(); // get needs to be called on background thread
                } catch (Exception ex) {
                    return null;
                }
            }
            @Override protected void onPostExecute(Bitmap bitmap) {
                if(bitmap == null) return;
                MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, System.currentTimeMillis() + "", System.currentTimeMillis() + "");
            }
        }.execute(Glide.with(context).load(url).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)); // into needs to be called on main thread
    }
    /**
     * 实现文本复制功能
     * add by wangqianzhou
     * @param content
     */
    public static void copy(String content, Context context)
    {
    // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 打开系统浏览器
     * @param url
     * @param context
     */
    public static void openBrowser(String url, Context context){
        if(url != null){
            try {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建基础Dialog
     */
    public static void createDialog(Context context, String message, String positive, String negative, DialogInterface.OnClickListener positives, DialogInterface.OnClickListener negatives){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(message)//设置提示内容
                //确定按钮
                .setPositiveButton(positive, positives)
                //取消按钮
                .setNegativeButton(negative, negatives)
                .create();//创建对话框
        dialog.show();//显示对话框
        dialog.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.purples));
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.purples));
    }

    /**
     * 判断一个字符串是不是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if(str == null || str.equals("")){
            return false;
        }
        return str.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * 保留两位有效数字，如果是小数点后全是0则不显示
     *
     * @param num 需要格式化的数字
     * @return 格式化结果
     */
    public static String saveTwoFormat(double num) {
        String temp = String.format("%.2f", num);
        //如果最后是以.00结尾的则把.00去掉
        if (temp.endsWith(".00")) {
            temp = temp.substring(0, temp.length() - 3);
        }
        return temp;
    }

    /**
     * 获取屏幕尺寸
     * @param activity
     * @return
     */
    public static DisplayMetrics getWindowDisplay(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    private static ToastCompat toast;

    public static void showToast(String s) {
        try {
            if(toast != null){
                toast.cancel();
                toast = null;
            }
            toast = ToastCompat.makeText(appContext, s, Toast.LENGTH_LONG);
            ViewGroup group = (ViewGroup) toast.getView();
            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setText(s);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int textHeight2sp(Context context, float height){
        return (int)((height / context.getResources().getDisplayMetrics().density)*0.7);
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 当前时间年月日
     * @param curr
     * @return
     */
    public static String toYMD(long curr){
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd" );
        return format.format(curr);
    }

    /**
     * 当前时间年月日 时分秒
     * @param curr
     * @return
     */
    public static String toYMD_HMS(long curr){
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        return format.format(curr);
    }

    /**
     * 当前时间年
     * @param curr
     * @return
     */
    public static String toYear(long curr){
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy" );
        return format.format(curr);
    }

    /**
     * 当前月的第一天
     * @return
     */
    public static String toYMD1stDay(){
        Calendar cal_1= Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, 0);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd" );
        return format.format(cal_1.getTime());
    }

    //毫秒换成00:00:00
    public static String getCountTimeByLong(long finishTime) {
        if(finishTime < 0){
            return "00:00:00";
        }

        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(minute).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();

    }


    public static int parseInt(String str) {
        int result = 0;
        try {
            result = Integer.parseInt(str);
        } catch (Exception e) {
            System.out.print(e);
        }
        return result;
    }


    public static float parseFloat(String str) {
        float result = 0;
        try {
            result = Float.parseFloat(str);
        } catch (Exception e) {
            System.out.print(e);
        }
        return result;
    }

    public static double parseDouble(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        double result = 0;
        try {
            result = Double.parseDouble(str);
        } catch (Exception e) {
            System.out.print(e);
        }
        return result;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }


    private static CharSequence setForegroundColorSpan(String text, int color) {
        int start = 0;
        int end = text.length();
        return setForegroundColorSpan(text, color, start, end);
    }

    private static CharSequence setForegroundColorSpan(String text, int color, int start, int end) {
        SpannableString spanString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spanString.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spanString;
    }

    public static boolean isActivityRunning(Activity activity) {
        if (activity == null || activity.isDestroyed() || activity.isFinishing()) {
            return false;
        }
        return true;
    }
    public static boolean isFengg(String  time) {
        if (time.equals("00:00:10")||time.equals("00:00:09")||time.equals("00:00:08")
                ||time.equals("00:00:07")||time.equals("00:00:06")||time.equals("00:00:05")
                ||time.equals("00:00:04")||time.equals("00:00:03")||time.equals("00:00:02")
                ||time.equals("00:00:01")) {
           return  true;
        }else {
            return false;
        }

    }

}
