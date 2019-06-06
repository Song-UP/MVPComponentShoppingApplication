package property;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 共享索引属性保存
 *
 * @author kevin
 * @version v1.0
 * @since 2014-10/3/14
 */
public class SharePreferenceProperties {

    private static final String FILE_SHARE = "wecook_shared_file";

    private static SharedPreferences mSharePreferences;

    public static void setup(Context context) {
        mSharePreferences = context.getSharedPreferences(FILE_SHARE, Context.MODE_PRIVATE);
    }


    public static void setObj(String key, Object entity) {
        if (entity != null) {
            set(key, JSON.toJSONString(entity));
        } else {
            set(key, "");
        }
    }

    public static <T extends Object> T getObj(String key, Class clazz) {
        String entityStr = get(key, "").toString();
        if (!TextUtils.isEmpty(entityStr)) {
            return (T) JSON.parseObject(entityStr, clazz);
        }
        return null;
    }

    public static <T extends Object> List<T> getList(String key, Class clazz) {
        String entityStr = get(key, "").toString();
        if (!TextUtils.isEmpty(entityStr)) {
            return (List<T>) JSON.parseArray(entityStr, clazz);
        }
        return null;
    }

    public static void set(String key, Object value) {
        SharedPreferences.Editor editor = mSharePreferences.edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        }
        editor.apply();
    }

    public static Object get(String key, Object def) {
        if (def instanceof Integer) {
            return mSharePreferences.getInt(key, (Integer) def);
        } else if (def instanceof String) {
            return mSharePreferences.getString(key, (String) def);
        } else if (def instanceof Long) {
            return mSharePreferences.getLong(key, (Long) def);
        } else if (def instanceof Boolean) {
            return mSharePreferences.getBoolean(key, (Boolean) def);
        } else if (def instanceof Float) {
            return mSharePreferences.getFloat(key, (Float) def);
        } else if (def instanceof Set) {
            return mSharePreferences.getStringSet(key, new HashSet<String>());
        }
        return "";
    }

}
