package umeng;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UpdateUtils {
    public final static String fileName = "app_update_info";
    private static SharedPreferences sp;

    private static SharedPreferences getInstance(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, 0);
        }
        return sp;
    }

    public static void clearIgnoreVersion(Context context) {
        SharedPreferences.Editor edit = getInstance(context).edit();
        edit.putStringSet("ignore_versions", new HashSet<String>());
        edit.commit();
    }

    public static void addIgnoreVersion(Context context, long time, int version) {
        Set<String> ignores = getIgnoreVersion(context);
        ignores.add(String.valueOf(time + "-" + version));
        SharedPreferences.Editor edit = getInstance(context).edit();
        edit.putStringSet("ignore_versions", ignores);
        edit.commit();
    }

    public static boolean isRequireUpdate(int nativeCode, int updateCode) {
        return updateCode > nativeCode;
    }

    public static boolean isIgnoreVersion(Context context, int updateVersion) {
        Set<String> ignores = getIgnoreVersion(context);
        Iterator<String> iterators = ignores.iterator();
        while (iterators.hasNext()) {
            String store = iterators.next();
            String[] splits = store.split("-");
            if (splits != null && splits.length == 2) {
                long now = new Date().getTime();
                long time = Long.parseLong(splits[0]);
                int version = Integer.parseInt(splits[1]);
                if (version == updateVersion && now > time) {
                    //天数+1
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(time);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    //将忽略时间的时分秒清零
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);

                    if (now <= calendar.getTimeInMillis()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static Set<String> getIgnoreVersion(Context context) {
        return getInstance(context).getStringSet("ignore_versions", new HashSet<String>());
    }
}
