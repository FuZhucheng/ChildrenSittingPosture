package com.android.administrator.childrensittingposture.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.dialog.SettingDialog;
import com.android.administrator.childrensittingposture.dialog.ToastCommom;
import com.android.administrator.childrensittingposture.view.PickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingActivity extends Activity implements View.OnClickListener {

    private Timer timer_study;        //学习定时器
    private Timer timer_rest;       //休息定时器
    private TimerTask taskStudy;        //学习的任务
    private TimerTask taskRest;         //休息的任务
    private Handler settingHandler;

    private String studySetting = new String();         //设定的学习与休息时间
    private String restSetting = new String();

    private ToastCommom toastCommom;        //自定义吐司

    PickerView pv_study;
    PickerView pv_rest;
    private TextView tv__setting_remind;
    private ImageView img_setting_back;

    OkHttpClient okHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    List<String> hours;
    List<String> minute;
    List<String> secondsStart;


    JSONObject jsonRemind;              //post

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_setting);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlt_setting);

        settingHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                // 要做的事情
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        try {
                            // 首先最外层是{}，是创建一个对象
                            jsonRemind = new JSONObject();
                            jsonRemind.put("studyOrRestSign", "0");

                        } catch (JSONException ex) {
                            // 键为null或使用json不支持的数字格式(NaN, infinities)
                            throw new RuntimeException(ex);
                        }
                        break;
                    case 2:
                        try {
                            // 首先最外层是{}，是创建一个对象
                            jsonRemind = new JSONObject();
                            jsonRemind.put("studyOrRestSign", "1");

                        } catch (JSONException ex) {
                            // 键为null或使用json不支持的数字格式(NaN, infinities)
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        };




        initView();
        initList();
        initListener();
        setHoursStart();
        setMinuteStart();

    }


    private void initView() {
        tv__setting_remind = (TextView) findViewById(R.id.tv__setting_remind);
        pv_study = (PickerView) findViewById(R.id.pv_hour);
        pv_rest = (PickerView) findViewById(R.id.pv_minute);
        img_setting_back = (ImageView) findViewById(R.id.img_setting_back);
    }

    private void initListener() {
        tv__setting_remind.setOnClickListener(this);
        img_setting_back.setOnClickListener(this);
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
        studySetting = "12";
        pv_study.setData(hours);
        pv_study.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                toastCommom = ToastCommom.createToastConfig();
                toastCommom.ToastShow(SettingActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "选择了 " + text + " 时");
                studySetting = text;
            }
        });

    }

    private void setMinuteStart() {
        for (int i = 0; i < 60; i++) {
            minute.add(i < 10 ? "0" + i : "" + i);
        }
        restSetting = "30";
        pv_rest.setData(minute);
        pv_rest.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                toastCommom = ToastCommom.createToastConfig();
                toastCommom.ToastShow(SettingActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "选择了 " + text + " 分");
                restSetting = text;
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
            Response response = okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {
                //打印服务端返回结果
                Log.e("success", response.body().string());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_setting_back:
                Intent intent_settingBack = new Intent();
                intent_settingBack.setClass(SettingActivity.this, MainActivity.class);
                startActivity(intent_settingBack);
                break;
            case R.id.tv__setting_remind:
                //开启一个线程，做联网操作
                new Thread() {
                    @Override
                    public void run() {
//                        postJson(jsonRemind);
                        Log.e("string", studySetting);
                        Log.e("string", restSetting);
                        if (timer_study == null) {
                            taskStudy = new TimerTask() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    Message message = new Message();
                                    message.what = 1;
                                    settingHandler.sendMessage(message);
                                }
                            };

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toastCommom = ToastCommom.createToastConfig();
                                    toastCommom.ToastShow(SettingActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "设置成功");                                }
                            });
                            taskRest = new TimerTask() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    Message messageRest = new Message();
                                    messageRest.what = 2;
                                    settingHandler.sendMessage(messageRest);
                                }
                            };

                            timer_study = new Timer();
                            timer_study.schedule(taskStudy, Integer.valueOf(studySetting).intValue());
                            timer_rest = new Timer();
                            timer_rest.schedule(taskRest, Integer.valueOf(studySetting).intValue() + Integer.valueOf(restSetting).intValue());

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SettingDialog.Builder builder=new SettingDialog.Builder(SettingActivity.this);
                                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            //设置你的操作事项
                                            timer_study.cancel();
                                            timer_study=null;
                                            taskStudy.cancel();
                                            taskStudy=null;
                                            timer_rest.cancel();
                                            timer_rest=null;
                                            taskRest.cancel();
                                            taskRest=null;
                                        }
                                    });
                                    builder.setNegativeButton("取消",
                                            new android.content.DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    builder.create().show();

                                }
                            });
                        }
                    }
                }.start();


                break;
        }
    }
}
