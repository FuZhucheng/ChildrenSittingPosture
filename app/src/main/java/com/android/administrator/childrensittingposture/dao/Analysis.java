package com.android.administrator.childrensittingposture.dao;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.administrator.childrensittingposture.activity.MainActivity;
import com.android.administrator.childrensittingposture.bean.CultivateDb;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/7/20.
 */
public class Analysis {
    private BarChart barChart;
    private LineChart lineChart;

    //BarChart的数据
    private ArrayList<BarEntry> entriesBar = new ArrayList<BarEntry>();
    private ArrayList<String> XlableBar = new ArrayList<String>();//    private BarDataSet dataset;

    //LineChart的数据、

    public void analysis(Context context, String jsonData, Handler handler) {
        try {

//            if (cultivateDb.getThatDayTime() > DataSupport.findLast(CultivateDb.class).getThatDayTime()&cultivateDb==null) {
            List<CultivateDb> postureBeanList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonData);

            CultivateDb firstDb = DataSupport.findLast(CultivateDb.class);
            if (firstDb == null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    CultivateDb cultivateDb = new CultivateDb();
                    cultivateDb.setCultivateTime(jsonObject.getInt("cultivateTime"));
                    cultivateDb.setRemindFrequency(jsonObject.getInt("remindFrequency"));
                    cultivateDb.setSumRemindFrequency(jsonObject.getInt("sumRemindFrequency"));
                    cultivateDb.setThatDayTime(jsonObject.getInt("data"));

                    Log.e("today", String.valueOf(cultivateDb.getThatDayTime()));

                    postureBeanList.add(cultivateDb);

//                        entriesBar.add(new BarEntry((float) cultivateDb.getRemindFrequency(), index));
//                        index++;
//                        XlableBar.add(String.valueOf(cultivateDb.getThatDayTime()));
                    Log.e("TAG_create", String.valueOf(cultivateDb.getId()));
                    cultivateDb.save();
                    Log.e("TAG_create", String.valueOf(cultivateDb.getId()));
                }
            } else {
                JSONObject jsonObject_seven = jsonArray.getJSONObject(6);
                int nowDay = jsonObject_seven.getInt("data");
                if (firstDb.getThatDayTime() < nowDay) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        CultivateDb cultivateDb = new CultivateDb();
                        cultivateDb.setCultivateTime(jsonObject.getInt("cultivateTime"));
                        cultivateDb.setRemindFrequency(jsonObject.getInt("remindFrequency"));
                        cultivateDb.setSumRemindFrequency(jsonObject.getInt("sumRemindFrequency"));
                        cultivateDb.setThatDayTime(jsonObject.getInt("data"));

                        postureBeanList.add(cultivateDb);
//                        Log.e("Data", String.valueOf(postureBeanList.get(2).getThatDayTime()));
                        Log.e("Data", String.valueOf(postureBeanList.get(0).getThatDayTime()));

//                            entriesBar.add(new BarEntry((float) cultivateDb.getRemindFrequency(), index));
//                            index++;
//                            XlableBar.add(String.valueOf(cultivateDb.getThatDayTime()));
                        Log.e("TAG_refresh", String.valueOf(cultivateDb.getId()));
                        if (jsonObject.getInt("data")>firstDb.getThatDayTime()){
                            cultivateDb.save();
                        }
                        Log.e("TAG_refresh", String.valueOf(cultivateDb.getId()));
                    }
                } else {
                    Log.e("not show", "false");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

//        dataset = new BarDataSet(entriesBar, "");
//        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        BarData data = new BarData(XlableBar, dataset);
        CultivateDb todayDb = DataSupport.findLast(CultivateDb.class);
//
//        Log.e("today", String.valueOf(todayDb.getThatDayTime()));
//        Log.e("today", String.valueOf(todayDb.getCultivateTime()));
//        Log.e("today", String.valueOf(todayDb.getRemindFrequency()));
//        Message messag = new Message();
//        messag.what = MainActivity.TODAY_REST_CUMULATION_TIME;
////        messag.obj=data;
//        handler.handleMessage(messag);


        Message messageRemind = new Message();
        messageRemind.what = MainActivity.REMIND_NUMBER;
        messageRemind.obj = todayDb.getRemindFrequency();
        handler.handleMessage(messageRemind);

        Message messageStudyTime = new Message();
        messageStudyTime.what = MainActivity.TODAY_STUDY_CUMULATION_TIME;
        messageStudyTime.obj = todayDb.getCultivateTime();
        handler.handleMessage(messageStudyTime);

        Message messageScore=new Message();
        messageScore.what = MainActivity.TODAY_SCORE;
        handler.handleMessage(messageScore);


        List<CultivateDb>sevenData= DataSupport.findAll(CultivateDb.class);
        Log.e("sevenData", String.valueOf(sevenData.get(0).getThatDayTime()));
        Log.e("sevenData", String.valueOf(sevenData.get(1).getThatDayTime()));
        Log.e("sevenData", String.valueOf(sevenData.get(2).getThatDayTime()));

    }

}
