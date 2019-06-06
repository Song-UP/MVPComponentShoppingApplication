package utils;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipan on 2014/6/8.
 */
public class StringUtils {
    public final static String UTF_8 = "utf-8";

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        return value == null || "".equalsIgnoreCase(value.trim()) || "null".equalsIgnoreCase(value.trim());
    }

    /**
     * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equalsIgnoreCase(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    /**
     * 返回一个高亮spannable
     *
     * @param content 文本内容
     * @param color   高亮颜色
     * @param start   起始位置
     * @param end     结束位置
     * @return 高亮spannable
     */
    public static CharSequence getHighLightText(String content, int color, int start, int end) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        start = start >= 0 ? start : 0;
        end = end <= content.length() ? end : content.length();
        SpannableString spannable = new SpannableString(content);
        CharacterStyle span = new ForegroundColorSpan(color);
        spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 格式化文件大小，不保留末尾的0
     */
    public static String formatFileSize(long len) {
        return formatFileSize(len, false);
    }

    /**
     * 格式化文件大小，保留末尾的0，达到长度一致
     */
    public static String formatFileSize(long len, boolean keepZero) {
        String size;
        DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
        DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
        if (len < 1024) {
            size = String.valueOf(len + "B");
        } else if (len < 10 * 1024) {
            // [0, 10KB)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / (float) 100) + "KB";
        } else if (len < 100 * 1024) {
            // [10KB, 100KB)，保留一位小数
            size = String.valueOf(len * 10 / 1024 / (float) 10) + "KB";
        } else if (len < 1024 * 1024) {
            // [100KB, 1MB)，个位四舍五入
            size = String.valueOf(len / 1024) + "KB";
        } else if (len < 10 * 1024 * 1024) {
            // [1MB, 10MB)，保留两位小数
            if (keepZero) {
                size = String.valueOf(formatKeepTwoZero.format(len * 100 / 1024 / 1024 / (float) 100)) + "MB";
            } else {
                size = String.valueOf(len * 100 / 1024 / 1024 / (float) 100) + "MB";
            }
        } else if (len < 100 * 1024 * 1024) {
            // [10MB, 100MB)，保留一位小数
            if (keepZero) {
                size = String.valueOf(formatKeepOneZero.format(len * 10 / 1024 / 1024 / (float) 10)) + "MB";
            } else {
                size = String.valueOf(len * 10 / 1024 / 1024 / (float) 10) + "MB";
            }
        } else if (len < 1024 * 1024 * 1024) {
            // [100MB, 1GB)，个位四舍五入
            size = String.valueOf(len / 1024 / 1024) + "MB";
        } else {
            // [1GB, ...)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float) 100) + "GB";
        }
        return size;
    }


    /**
     * 以‘,’的形式分割id列表
     *
     * @param list
     * @return
     */
    public static String getIdScopeString(long[] list) {
        if (list == null || list.length == 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (long id : list) {
            sb.append("'").append(id).append("',");
        }

        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 过滤NULL字符串
     *
     * @param s
     * @return
     */
    public static String getStringWithoutNull(String s) {
        if (isEmpty(s)) {
            return "";
        }
        return s;
    }

    public static Date formatData(String date, String formatStr) {
        if (isEmpty(date) || isEmpty(formatStr)) {
            return null;
        }
        Date result = null;
        try {
            Date utilDate = new SimpleDateFormat(formatStr).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String formatTime(Date date, String formatStr) {
        if (date == null || isEmpty(formatStr)) {
            return "";
        }
        String utilDate = new SimpleDateFormat(formatStr).format(date);
        return utilDate;
    }

    /**
     * 将时间转化为“2017-06-28 周三 17：00”
     *
     * @param dateStr   待转换日期字符串 2017-06-28 16:14:51
     * @param formatStr 待转换日期字符串格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTime(String dateStr, String formatStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        try {
            Date date = formatter.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String weekS = getWeekOfDate(date);
            String dateS = formatTime(date, "yyyy-MM-dd");
            String timeS = formatTime(date, "HH:mm");
            StringBuffer buffer = new StringBuffer();
            buffer.append(dateS).append(" ").append(weekS).append(" ").append(timeS);
            return buffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将时间2017-06-28 16:14:51转化为“2017-06-28 周三 16:14”
     *
     * @param dateStr
     * @return
     */
    public static String formatTime(String dateStr) {
        return formatTime(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatTimeStr(long time, String formPattern) {
        SimpleDateFormat format = new SimpleDateFormat(formPattern);
        return format.format(time);
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 比较两个时间yyyy-MM-dd HH:mm:ss
     *
     * @param date1 yyyy-MM-dd HH:mm:ss
     * @param date2 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int compareDate(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 与当前时间想比较
     *
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int compareDateWithNow(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(new Date());
        return compareDate(date, now);
    }


    /**
     * 格式化日期
     *
     * @param time
     * @param formPattern
     * @return
     */
    public static String formatTime(long time, String formPattern) {
        String t = time + "";
        if (t.length() <= 10) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(formPattern);
        return format.format(date);
    }

    /**
     * 格式化日期，临近时间自动优化可读性
     *
     * @param time        待格式化时间
     * @param timePattern 待格式化时间格式
     * @return
     */
    public static String formatTimeWithNearBy(String time, String timePattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(timePattern);
        try {
            Date date = formatter.parse(time);
            return formatTimeWithNearBy(date.getTime(), "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化日期，临近时间自动优化可读性
     *
     * @param time
     * @param formPattern
     * @return
     */
    public static String formatTimeWithNearBy(long time, String formPattern) {
        String t = time + "";
        if (t.length() == 10) {
            time = time * 1000;
        }
        long minus = System.currentTimeMillis() - time;
        //当差值小于1分钟
        if (minus < 60 * 1000) {
            return "刚刚";
        }
        //当差值小于1个小时
        if (minus < 1 * 60 * 60 * 1000) {
            return minus / (60 * 1000) + "分钟前";
        }
        //当差值小于5个小时
        if (minus < 5 * 60 * 60 * 1000) {
            return minus / (60 * 60 * 1000) + "小时前";
        }
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(formPattern);
        return format.format(date);
    }

    /**
     * 格式化日期，临近时间自动优化可读性
     *
     * @param time
     * @param formPattern
     * @return
     */
    public static String formatTimeWithCounDown(long targetTime) {
        int second = 1000;
        int minite = 60 * second;
        int hour = 60 * minite;
        int day = 24 * hour;
        long now = System.currentTimeMillis();
        long offerTime = targetTime - now;
        if (offerTime <= 0) {
            return "00:00:00";
        }
        long tempDay = offerTime / day;
        long tempHour = offerTime % day / hour;
        long tempMinite = offerTime % hour / minite;
        long tempSecond = offerTime % minite / second;
        StringBuffer stringBuffer = new StringBuffer();
        String result;
        if (tempDay > 0) {
            result = String.format("%s天%s:%s:%s", getTimeFormatStr(tempDay), getTimeFormatStr(tempHour), getTimeFormatStr(tempMinite), getTimeFormatStr(tempSecond));
        } else {
            result = String.format("%s:%s:%s", getTimeFormatStr(tempHour), getTimeFormatStr(tempMinite), getTimeFormatStr(tempSecond));

        }
        return result;
    }

    private static String getTimeFormatStr(long i) {
        String result;
        if (i > 9) {
            result = i + "";
        } else {
            result = "0" + i;
        }
        return result;
    }

    /**
     * 获得字符串中匹配以某一个字符开头，一些字符结尾的字串位置
     *
     * @param text
     * @param start 起始符号
     * @param end   多个结束符号
     * @return 符合匹配的字符串的位置区域
     */
    public static Map<Integer, Integer> getCharPairMap(String text, char start, char... end) {
        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        boolean doFinding = false;
        int findingStartIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == start && !doFinding) {
                doFinding = true;
                findingStartIndex = i;
                continue;
            } else if ((contain(end, c)/* || i == text.length() - 1*/) && doFinding) {
                doFinding = false;
                indexMap.put(findingStartIndex, i + 1);
            }
        }
        return indexMap;
    }

    /**
     * 字符数组中是否包含字符
     *
     * @param chars
     * @param c
     * @return
     */
    private static boolean contain(char[] chars, char c) {
        if (chars == null) {
            return false;
        }

        for (char a : chars) {
            if (a == c) {
                return true;
            }
        }

        return false;
    }

    /**
     * [A-z0-9]{2,} 任意字母和数字组合，并长度大于等于2，必选
     * <p/>
     * [@] 必选
     * <p/>
     * [a-z0-9]{2,} 任意小写字母和数字组合，并长度大于等于2，必选
     * <p/>
     * [.] 必选
     * <p/>
     * \p{Lower}{2,} 任意小写字母，并长度大于等于2，必选
     * <p/>
     *
     * @param emailString
     * @return
     */
    public static boolean validateEmailAddress(String emailString) {
        String format = "[A-z0-9._]{2,}[@][a-z0-9]{2,}[.]\\p{Lower}{2,}";
        return emailString.matches(format);
    }

    /**
     * 匹配格式： 11位手机号码 3-4位区号，7-8位直播号码，1－4位分机号 如：12345678901、1234-12345678-1234
     *
     * @param phoneString
     * @return
     */
    public static boolean validatePhoneNumber(String phoneString) {
        String format = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
        return phoneString.matches(format);
    }

    /**
     * 5-12位的数字
     *
     * @param qqString
     * @return
     */
    public static boolean validateQQNumber(String qqString) {
        String format = "^\\d{5,12}$";
        return qqString.matches(format);
    }

    /**
     * 密码验证
     *
     * @param pwd 密码
     * @return
     */
    public static boolean validatePassword(String pwd) {
        if (!StringUtils.isEmpty(pwd)) {
            String format = "^[A-Za-z0-9\\@\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,22}$";
            return pwd.matches(format);
        }
        return false;
    }

    public static Spannable getSpanClickable(Context context, int text, ClickableSpan clickableSpan,
                                             int fontColor) {
        if (context == null) {
            return null;
        }
        String textString = context.getString(text);
        SpannableString spannableString = new SpannableString(textString);
        spannableString.setSpan(clickableSpan, 0, textString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(fontColor)),
                0, textString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static int length(String commentContent) {
        if (StringUtils.isEmpty(commentContent)) {
            return 0;
        }

        return commentContent.length();
    }

    /**
     * 获取字符串的长度，中文占一个字符,英文数字占半个字符
     *
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static double chineseLength(String value) {
        if (StringUtils.isEmpty(value))
            return 0;

        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < value.length(); i++) {
            // 获取一个字符
            String temp = value.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
        }
        //进位取整
        return Math.ceil(valueLength);
    }

    /**
     * 获得最大长度的汉字字串
     *
     * @param source
     * @param maxLength
     * @return
     */
    public static String getChineseStringByMaxLength(String source, int maxLength) {
        String cutString = "";
        double tempLength = 0;
        for (int i = 0; i < source.length(); i++) {
            String temp = source.substring(i, i + 1);
            String chinese = "[\u4e00-\u9fa5]";
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                tempLength += 1;
            } else {
                // 其他字符长度为0.5
                tempLength += 0.5;
            }
            cutString += temp;
            if (Math.ceil(tempLength) == maxLength) {
                break;
            }
        }
        return cutString;
    }

    /**
     * 字符串是否包含制定的特殊字符
     *
     * @param keyname 字符串
     * @param s       制定字符
     * @return
     */
    public static boolean containWith(String keyname, String s) {
        if (isEmpty(keyname)) {
            return false;
        }
        return keyname.contains(s);
    }


    /**
     * 是否是字母字符
     *
     * @param c
     * @return
     */
    public static boolean isChar(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');

    }

    public static boolean isFloatNumber(String text) {
        try {
            Float.parseFloat(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDoubleNumber(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isLongNumber(String text) {
        try {
            Long.parseLong(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isIntegerNumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String newStringFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            if (is != null && is.available() > 0) {
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, len);
                }
                byte[] content = outputStream.toByteArray();
                return new String(content);
            }
        } finally {
            outputStream.close();
        }

        return null;
    }

    public static ForegroundColorSpan addForegroundColor(Context context, Spannable spannable, int color, char start, char... end) {
        String text = spannable.toString();
        Map<Integer, Integer> atIndexMap = StringUtils.getCharPairMap(text, start, end);
        //clear
        ForegroundColorSpan[] spans = spannable.getSpans(0, spannable.length(), ForegroundColorSpan.class);
        for (ForegroundColorSpan span : spans) {
            spannable.removeSpan(span);
        }

        //add
        ForegroundColorSpan span = null;
        for (Map.Entry<Integer, Integer> entry : atIndexMap.entrySet()) {
            span = new ForegroundColorSpan(context.getResources().getColor(color));
            spannable.setSpan(span, entry.getKey(), entry.getValue(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return span;
    }

    public static boolean equals(String src, String target) {
        if (StringUtils.isEmpty(src) && StringUtils.isEmpty(target)) {
            return true;
        }

        return !StringUtils.isEmpty(src) && src.equals(target);
    }

    public static String subChineseString(String value, int start, int end) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";

        int startPos = start;
        if (start > 0) {
            for (int i = 0; i < value.length(); i++) {
                // 获取一个字符
                String temp = value.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                    // 中文字符长度为1
                    valueLength += 1;
                } else {
                    // 其他字符长度为0.5
                    valueLength += 0.5;
                }
                int startLength = (int) Math.ceil(valueLength);
                if (startLength == start) {
                    startPos = i;
                    break;
                }
            }
        }
        for (int i = startPos; i < value.length(); i++) {
            // 获取一个字符
            String temp = value.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }

            int endLength = (int) Math.ceil(valueLength);
            if (endLength == end) {
                return temp;
            }
        }

        return "";
    }

    public static int translateToInt(String string) {
        char[] chars = string.toCharArray();
        int result = 0;
        for (char c : chars) {
            result += c;
        }
        return result;
    }

    public static int parseInt(String number) {
        if (isEmpty(number) || !isIntegerNumber(number)) {
            return 0;
        }
        return Integer.parseInt(number);
    }

    public static long parseLong(String number) {
        if (isEmpty(number) || !isLongNumber(number)) {
            return 0;
        }
        return Long.parseLong(number);
    }

    public static float parseFloat(String number) {
        if (isEmpty(number) || !isFloatNumber(number)) {
            return 0;
        }
        return Float.parseFloat(number);
    }

    public static double parseDouble(String number) {
        if (isEmpty(number) || !isDoubleNumber(number)) {
            return 0;
        }
        return Double.parseDouble(number);
    }

    /**
     * 获得格式化的钱
     *
     * @param deliveryPrice
     * @return
     */
    public static String getPriceText(Object deliveryPrice) {
        try {
            return "¥" + new DecimalFormat("##0.00").format(deliveryPrice);
        } catch (Exception e) {
            e.printStackTrace();
            return "¥--";
        }
    }

    public static String getPhoneName(String phone) {
        if (phone.isEmpty()) {
            return "匿名手机";
        }
        try {
            String prefix = phone.substring(0, 3);
            String last = phone.substring(phone.length() - 4, phone.length());
            return prefix + "****" + last;
        } catch (Exception e) {
            return "匿名手机";
        }
    }
}
