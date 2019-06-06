package umeng;

import android.os.Parcel;
import android.os.Parcelable;

public class Version implements Parcelable {
    /**
     * 版本更新说明
     */
    public final String note;

    /**
     * 版本 APK 的下载地址
     */
    public final String URL;

    /**
     * 版本代码
     */
    public final int code;

    public int isForce;

    public Version(String note, String url, int code, int isForce) {
        this.note = note;
        this.URL = url;
        this.code = code;
        this.isForce = isForce;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.note);
        dest.writeString(this.URL);
        dest.writeInt(this.code);
        dest.writeInt(isForce);
    }

    private Version(Parcel in) {
        this.note = in.readString();
        this.URL = in.readString();
        this.code = in.readInt();
        this.isForce = in.readInt();
    }

    public static final Parcelable.Creator<Version> CREATOR = new Parcelable.Creator<Version>() {
        public Version createFromParcel(Parcel source) {
            return new Version(source);
        }

        public Version[] newArray(int size) {
            return new Version[size];
        }
    };
}