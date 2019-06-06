package umeng;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

public class UMengUtil {

    /**
     * 初始化
     *
     * @param ctx
     */
    public static void init(Context ctx) {
//        AnyVersion.init(ctx, null, new VersionParser() {
//            @Override
//            public Version onParse(String response) {
//                final JSONTokener tokener = new JSONTokener(response);
//                try {
//                    JSONObject json = (JSONObject) tokener.nextValue();
//                    return new Version(
//                            "大房鸭",
//                            json.getString("context"),
//                            json.getString("url"),
//                            Integer.valueOf(json.getString("curVer")),
//                            Integer.valueOf(json.getString("compatibleVer"),)
//                    );
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
    }

    /**
     * 检查更新
     *
     * @param activity
     */
    public static void checkUpdate(FragmentActivity activity, String url, final boolean isSkipIgnore) {
        AnyVersion.getInstance().check(activity.getSupportFragmentManager(), url, isSkipIgnore);
    }

    /**
     * 检查更新
     */
    public static void checkUpdate(String url, Callback callback) {
        AnyVersion.getInstance().check(url, callback);
    }
}