package com.song.songup.shoppingapp.mvp.model.been;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/21 14:00
 * 修改备注：
 */
public class UserInfo {
    private static String RongCloudToken;
    private static String token;
    private static String MyUserId;
    private static String Nickname;
    private static String Avatar;
    public static String getRongCloudToken() {
        return RongCloudToken;
    }

    public static void setRongCloudToken(String rongCloudToken) {
        RongCloudToken = rongCloudToken;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserInfo.token = token;
    }

    public static String getMyUserId() {
        return MyUserId;
    }

    public static void setMyUserId(String myUserId) {
        MyUserId = myUserId;
    }

    public static String getNickname() {
        return Nickname;
    }

    public static void setNickname(String nickname) {
        Nickname = nickname;
    }

    public static String getAvatar() {
        return Avatar;
    }

    public static void setAvatar(String avatar) {
        Avatar = avatar;
    }
}
