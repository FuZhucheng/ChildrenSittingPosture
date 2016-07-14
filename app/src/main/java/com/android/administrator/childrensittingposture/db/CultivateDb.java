package com.android.administrator.childrensittingposture.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/7/13.
 */
public class CultivateDb extends DataSupport {
    private int id;
    private String thatDayTime;                 //当天日期
    private String cultivateTime;                  //学习累计时间
    private String remindFrequency;     //当天提醒次数

    public String getThatDayTime() {
        return thatDayTime;
    }

    public void setThatDayTime(String thatDayTime) {
        this.thatDayTime = thatDayTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCultivateTime() {
        return cultivateTime;
    }

    public void setCultivateTime(String cultivateTime) {
        this.cultivateTime = cultivateTime;
    }

    public String getRemindFrequency() {
        return remindFrequency;
    }

    public void setRemindFrequency(String remindFrequency) {
        this.remindFrequency = remindFrequency;
    }
}
