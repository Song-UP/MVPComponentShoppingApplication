package com.song.songup.shoppingapp.mvp.model.been.main;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/2 17:11
 * 修改备注：
 */
public class Winger {
    private String name;
    private String money;
    private String activityName;


    public Winger() {
    }

    public Winger(String name, String money, String activityName) {
        this.name = name;
        this.money = money;
        this.activityName = activityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public static List<Winger> getList(){
        List<Winger> wingerList = new ArrayList<>();
        for (int i=0; i<17;i++){
            wingerList.add(new Winger("我的名字"+i,i+"00000","活动"+i));
        }
        return wingerList;
    };
}
