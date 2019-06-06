package umeng;

import android.os.Looper;

class Preconditions {

    private Preconditions() {
    }

    public static void requiredMainUIThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("Should run on main UI thread !");
        }
    }

    public static void requireInited() {
        if (AnyVersion.getInstance().context == null) {
            throw new IllegalStateException("AnyVersion instance NOT init !");
        }
    }
}