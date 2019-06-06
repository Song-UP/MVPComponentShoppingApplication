package com.song.songup.shoppingapp.mvp.model.been.classes;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/17 19:22
 * 修改备注：
 */
public class ClassesBeen {
    private String title;
    private String type;

    private int time;
    private int PastTime;

    public ClassesBeen(String title, String type, int time) {
        this.title = title;
        this.type = type;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPastTime() {
        return PastTime;
    }

    public void setPastTime(int pastTime) {
        PastTime = pastTime;
    }
}
