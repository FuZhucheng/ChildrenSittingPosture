package com.android.administrator.childrensittingposture.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.view.PickerView;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    PickerView minute_pv;
    PickerView second_pv;
    PickerView hour_pv;
    PickerView minute_pv2;
    PickerView second_pv2;
    PickerView hour_pv2;

    PickerView pv_learned;
    PickerView pv_rest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        List<String> hours=new ArrayList<String>();
        List<String> data = new ArrayList<String>();
        List<String> seconds = new ArrayList<String>();
        List<String> hours2=new ArrayList<String>();
        List<String> data2 = new ArrayList<String>();
        List<String> seconds2 = new ArrayList<String>();
        List<String> pv_learned_time = new ArrayList<String>();
        List<String> pv_rest_time = new ArrayList<String>();

        for (int i=0; i<60;i++){
            pv_learned_time.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i=0; i<60;i++){
            pv_rest_time.add(i < 10 ? "0" + i : "" + i);
        }
        pv_learned.setData(pv_learned_time);
        pv_learned.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Toast.makeText(SettingActivity.this, "选择了" + text + "时", Toast.LENGTH_SHORT).show();
            }
        });
        pv_rest.setData(pv_learned_time);
        pv_rest.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Toast.makeText(SettingActivity.this, "选择了" + text + "时", Toast.LENGTH_SHORT).show();
            }
        });



        for (int i = 0;i < 24; i++){
            hours.add(""+ i );
        }
        for (int i = 0; i < 60; i++)
        {
            data.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i = 0; i < 60; i++)
        {
            seconds.add(i < 10 ? "0" + i : "" + i);
        }
        hour_pv.setData(hours);
        hour_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Toast.makeText(SettingActivity.this, "选择了" + text + "时", Toast.LENGTH_SHORT).show();
            }
        });
        minute_pv.setData(data);
        minute_pv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                Toast.makeText(SettingActivity.this, "选择了 " + text + " 分",
                        Toast.LENGTH_SHORT).show();
            }
        });
        second_pv.setData(seconds);
        second_pv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                Toast.makeText(SettingActivity.this, "选择了 " + text + " 秒",
                        Toast.LENGTH_SHORT).show();
            }
        });



        for (int i = 0;i < 24; i++){
            hours2.add(""+ i );
        }
        for (int i = 0; i < 60; i++)
        {
            data2.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i = 0; i < 60; i++)
        {
            seconds2.add(i < 10 ? "0" + i : "" + i);
        }
        hour_pv2.setData(hours);
        hour_pv2.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Toast.makeText(SettingActivity.this, "选择了" + text + "时", Toast.LENGTH_SHORT).show();
            }
        });
        minute_pv2.setData(data);
        minute_pv2.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                Toast.makeText(SettingActivity.this, "选择了 " + text + " 分",
                        Toast.LENGTH_SHORT).show();
            }
        });
        second_pv2.setData(seconds);
        second_pv2.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                Toast.makeText(SettingActivity.this, "选择了 " + text + " 秒",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initView(){
        hour_pv=(PickerView)findViewById(R.id.pv_hour);
        minute_pv = (PickerView) findViewById(R.id.minute_pv);
        second_pv = (PickerView) findViewById(R.id.second_pv);
        hour_pv2=(PickerView)findViewById(R.id.pv_hour2);
        minute_pv2 = (PickerView) findViewById(R.id.minute_pv2);
        second_pv2 = (PickerView) findViewById(R.id.second_pv2);
        pv_learned = (PickerView) findViewById(R.id.pv_learned);
        pv_rest = (PickerView) findViewById(R.id.pv_rest);
    }
}
