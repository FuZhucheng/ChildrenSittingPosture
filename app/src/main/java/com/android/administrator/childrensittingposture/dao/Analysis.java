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

    public void analysis(Context context, String jsonData,Handler handler){
        try {
                CultivateDb cultivateDb = new CultivateDb();

            List<CultivateDb>postureBeanList=new ArrayList<>();
            JSONArray jsonArray=new JSONArray(jsonData);
            int index=0;
            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                postureBean.setId(jsonObject.getString("id"));
                cultivateDb.setCultivateTime(jsonObject.getInt("cultivateTime"));
                cultivateDb.setRemindFrequency(jsonObject.getInt("remindFrequency"));
                cultivateDb.setSumRemindFrequency(jsonObject.getInt("sumRemindFrequency"));
                cultivateDb.setThatDayTime(jsonObject.getString("date"));

                postureBeanList.add(cultivateDb);

                entriesBar.add(new BarEntry((float) cultivateDb.getRemindFrequency(),index));
                index++;
                XlableBar.add(cultivateDb.getThatDayTime());
                Log.e("TAG", String.valueOf(cultivateDb.getId()));
                cultivateDb.save();
                Log.e("TAG", String.valueOf(cultivateDb.getId()));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

//        dataset = new BarDataSet(entriesBar, "");
//        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        BarData data = new BarData(XlableBar, dataset);

        Message messag=new Message();
        messag.what= MainActivity.BAR_DATA;
//        messag.obj=data;
        handler.handleMessage(messag);


        Message message=new Message();
        message.what=MainActivity.REMIND_NUMBER;
        handler.handleMessage(message);


        message.what=MainActivity.TODAY_SCORE;
        handler.handleMessage(message);
    }

}
