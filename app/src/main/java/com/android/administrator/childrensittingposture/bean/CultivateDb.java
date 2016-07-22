package com.android.administrator.childrensittingposture.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/7/13.
 */
public class CultivateDb extends DataSupport {

    private int id;
    private int cultivateTime;                  //学习累计时间
    private int remindFrequency;     //当天提醒次数
    private int sumRemindFrequency;  //总提醒次数
    private String thatDayTime;                 //当天日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCultivateTime() {
        return cultivateTime;
    }

    public void setCultivateTime(int cultivateTime) {
        this.cultivateTime = cultivateTime;
    }

    public int getRemindFrequency() {
        return remindFrequency;
    }

    public void setRemindFrequency(int remindFrequency) {
        this.remindFrequency = remindFrequency;
    }

    public int getSumRemindFrequency() {
        return sumRemindFrequency;
    }

    public void setSumRemindFrequency(int sumRemindFrequency) {
        this.sumRemindFrequency = sumRemindFrequency;
    }

    public String getThatDayTime() {
        return thatDayTime;
    }

    public void setThatDayTime(String thatDayTime) {
        this.thatDayTime = thatDayTime;
    }
}
