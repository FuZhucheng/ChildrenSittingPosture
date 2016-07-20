package com.android.administrator.childrensittingposture.dao;

import com.android.administrator.childrensittingposture.bean.CultivateDb;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;
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
    private ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
    private BarDataSet dataset;
    private ArrayList<String> labels = new ArrayList<String>();

    public void analysis(String jsonData){
        try {
            CultivateDb db=new CultivateDb();
            List<CultivateDb>postureBeanList=new ArrayList<>();
            JSONArray jsonArray=new JSONArray(jsonData);
            CultivateDb postureBean=new CultivateDb();
            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                postureBean.setId(jsonObject.getString("id"));
                postureBean.setRemindFrequency(jsonObject.getString("remindNumber"));
                postureBean.setSumRemindFrequency(jsonObject.getString("sumSumRemindNumber"));
                postureBeanList.add(postureBean);
                db.save();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
