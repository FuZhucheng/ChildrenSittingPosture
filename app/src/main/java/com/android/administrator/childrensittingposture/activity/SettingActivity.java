package com.android.administrator.childrensittingposture.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.view.PickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingActivity extends Activity {

    PickerView pv_hour;
    PickerView pv_minute;
    private TextView tv__setting_remind;

    OkHttpClient okHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    List<String> hours;
    List<String> minute;
    List<String> secondsStart;


    JSONObject person;
    JSONArray phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_setting);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlt_setting);


        try {
            // 首先最外层是{}，是创建一个对象
            person = new JSONObject();
            // 第一个键phone的值是数组，所以需要创建数组对象
//            phone = new JSONArray();
            person.put("startTime","20");
            person.put("endTime", "8");

            person.put("continuityTime", "20");
            person.put("restTime", "20");
//            // 键address的值是对象，所以又要创建一个对象
//            address = new JSONObject();
//            address.put("startTime", "20");
//            address.put("endTime", "8");
//            person.put("address", address);

        } catch (JSONException ex) {
            // 键为null或使用json不支持的数字格式(NaN, infinities)
            throw new RuntimeException(ex);
        }

        initView(person);
        initList();


        setHoursStart();
        setMinuteStart();





    }


    private void initView(final JSONObject person) {
        tv__setting_remind=(TextView)findViewById(R.id.tv__setting_remind);
        pv_hour=(PickerView)findViewById(R.id.pv_hour);
        pv_minute=(PickerView)findViewById(R.id.pv_minute);

        tv__setting_remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                //开启一个线程，做联网操作
                new Thread() {
                    @Override
                    public void run() {

//                        postJson(person);
                    }
                }.start();
            }
        });
//        tv_sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //开启一个线程，做联网操作
////                new Thread() {
////                    @Override
////                    public void run() {
////
////                        postJson(person);
////                    }
////                }.start();
//            }
//        });
    }

            private void initList() {
                hours = new ArrayList<String>();
                minute = new ArrayList<String>();
                secondsStart = new ArrayList<String>();



            }



            private void setHoursStart() {
                for (int i = 0; i < 24; i++) {
                    hours.add("" + i);
                }
                pv_hour.setData(hours);
                pv_hour.setOnSelectListener(new PickerView.onSelectListener() {
                    @Override
                    public void onSelect(String text) {
                        Toast.makeText(SettingActivity.this, "选择了" + text + "时", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            private void setMinuteStart() {
                for (int i = 0; i < 60; i++) {
                    minute.add(i < 10 ? "0" + i : "" + i);
                }
                pv_minute.setData(minute);
                pv_minute.setOnSelectListener(new PickerView.onSelectListener() {

                    @Override
                    public void onSelect(String text) {
                        Toast.makeText(SettingActivity.this, "选择了 " + text + " 分",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

    private void postJson(JSONObject persona) {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(persona));
        //创建一个请求对象
        Request request = new Request.Builder()
                .url("http://119.29.176.80:8916")
                .post(requestBody)
                .build();
        //发送请求获取响应
        try {
            Response response=okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if(response.isSuccessful()){
                //打印服务端返回结果
                Log.e("success",response.body().string());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
