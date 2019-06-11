package com.song.songup.shoppingapp.mvp.ui.activity;

/**
 * Created by ASUS on 2017/8/14.
 */

public class BaseModel<T> {
    private int status;

    public Integer getSrv_time() {
        return srv_time;
    }

    public void setSrv_time(Integer srv_time) {
        this.srv_time = srv_time;
    }

    private Integer srv_time;
    private String msg;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
