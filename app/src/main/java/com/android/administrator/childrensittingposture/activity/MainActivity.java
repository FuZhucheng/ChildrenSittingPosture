package com.android.administrator.childrensittingposture.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.bean.CultivateDb;
import com.android.administrator.childrensittingposture.dao.SendRequest;
import com.android.administrator.childrensittingposture.dialog.MyPopWindow;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tv_studyOrRest;            //学习或休息状态
    private TextView tv_studyOrRestTime;        //学习或休息时间
    private TextView tv_mainRemind;                 //提醒学习或休息按键
    private TextView tv_main_todayLearn;       //今天学习了的时间
    private TextView tv_main_todayRest;            //今天休息了的时间
    private TextView tv_main_todayRectify;         //今天坐姿修正次数
    private TextView tv_main_todayScore;           //今天学习得分
    private ImageView img_main_setting;                //设置
    private ImageView img_heart;                        //心心跳动


    private Handler mHandler;
    private SwipeRefreshLayout mSwipeRefreshLayout;                 //下拉刷新的Layout
    private RotateAnimation rotateAnimation;                        //设置的旋转动画
    private ScaleAnimation scaleAnimation;                          //心心跳动效果


    public static final int TODAY_STUDY_CUMULATION_TIME = 1;            //今天学习时间
    public static final int TODAY_REST_CUMULATION_TIME = 2;               //今天休息时间
    public static final int REMIND_NUMBER = 3;                                  //今天提醒矫正次数
    public static final int TODAY_SCORE = 4;                                    //今天的得分
    public static final int BAR_DATA = 5;
    public static final int SUCCESS = 7;
    public static final int ALREADY_STUDY = 8;                                //已经连续学习
    public static final int ALREADY_REST = 9;                                     //已经连续休息

    private int SYSTEM_STATE = 0x3bafda;          //设置状态栏颜色

    JSONObject jsonStudyOrRest;     //学习与休息的标签
    OkHttpClient okHttpClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private CultivateDb mainDb = DataSupport.findLast(CultivateDb.class);          //作圆圈里面的读写数据库单例

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashReport.initCrashReport(getApplicationContext(), "b9188325fd", false);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
        Window window = MainActivity.this.getWindow();
//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//设置状态栏颜色
        window.setStatusBarColor(SYSTEM_STATE);

        initView();
        initListener();

    }

    private void initView() {
        tv_studyOrRest = (TextView) findViewById(R.id.tv_studyOrRest);
        tv_studyOrRestTime = (TextView) findViewById(R.id.tv_studyOrRestTime);
        tv_mainRemind = (TextView) findViewById(R.id.tv_mainRemind);
        tv_main_todayLearn = (TextView) findViewById(R.id.tv_main_todayLearn);
        tv_main_todayRest = (TextView) findViewById(R.id.tv_main_todayRest);
        tv_main_todayRectify = (TextView) findViewById(R.id.tv_main_todayRectify);
        tv_main_todayScore = (TextView) findViewById(R.id.tv_main_todayScore);
        img_main_setting = (ImageView) findViewById(R.id.img_main_setting);
        img_heart = (ImageView) findViewById(R.id.img_heart);

        if (mainDb != null) {
            tv_studyOrRestTime.setText("0:" + String.valueOf(mainDb.getCultivateTime()) + ":26");
            tv_main_todayLearn.setText(String.valueOf(mainDb.getCultivateTime()));
            tv_main_todayRest.setText("30");
            tv_main_todayRectify.setText(String.valueOf(mainDb.getRemindFrequency()));
            tv_main_todayScore.setText("88");

        }
        //设置的旋转动画
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        //设置心心和提醒跳动效果
        scaleAnimation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        rotateAnimation.setFillBefore(true);


        mHandler = new Handler() {
            @Override
            public void handleMessage(final Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case TODAY_STUDY_CUMULATION_TIME:
                        if (msg.obj != null) {
//                            tv_time.setText("1:20:12");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_main_todayLearn.setText(String.valueOf(msg.obj));
                                }
                            });
                        }
                        break;
                    case REMIND_NUMBER:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_main_todayRectify.setText(String.valueOf(msg.obj));
                            }
                        });
                    case TODAY_REST_CUMULATION_TIME:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_main_todayRest.setText("30");
                            }
                        });
                        break;
                    case TODAY_SCORE:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_main_todayScore.setText("88");
                            }
                        });
                }
            }
        };


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srlayout);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //自定义加载的圆形背景颜色
//        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.gray);
        //自定义加载的圆条颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.green, R.color.orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
//                        Log.e("readytosend",time+"");
                        new SendRequest().SendUid(1, mHandler, MainActivity.this);
                    }
                }, 2500);
                //正常情况下是在加载完成后回调，这里简单模拟延时
                Toast.makeText(MainActivity.this, "正在刷新", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);//6000指延时6s
            }
        });
    }


    private void initListener() {
        tv_mainRemind.setOnClickListener(this);
        img_main_setting.setOnClickListener(this);
        img_heart.setOnClickListener(this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mainRemind:
                if (tv_mainRemind.getText().toString() == getString(R.string.rest)) {
                    //提醒字体与动画
                    tv_mainRemind.setTextSize(19f);
                    tv_mainRemind.setText(getString(R.string.study));
                    tv_mainRemind.startAnimation(scaleAnimation);
                    //圆圈的数据
                    tv_studyOrRest.setText("REST");
                    if (mainDb.getCultivateTime() < 60) {
                        tv_studyOrRestTime.setText("0:" + mainDb.getCultivateTime() + ":20");
                        jsonStudyOrRest = new JSONObject();
                        try {
                            jsonStudyOrRest.put("studyOrRestSign", "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //开启一个线程，做联网操作
                        new Thread() {
                            @Override
                            public void run() {
                                postJson(jsonStudyOrRest);
                            }
                        }.start();
                    }

                } else if (tv_mainRemind.getText().toString() == getString(R.string.study)) {
                    //提醒字体与动画
                    tv_mainRemind.setTextSize(23f);
                    tv_mainRemind.setText(getString(R.string.rest));
                    tv_mainRemind.startAnimation(scaleAnimation);
                    //圆圈的数据
                    tv_studyOrRest.setText("STUDY");
                    tv_studyOrRestTime.setText("0:20:26");
                    jsonStudyOrRest = new JSONObject();
                    try {
                        jsonStudyOrRest.put("studyOrRestSign", "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //开启一个线程，做联网操作
                    new Thread() {
                        @Override
                        public void run() {
                            postJson(jsonStudyOrRest);
                        }
                    }.start();
                }
                break;
            case R.id.img_main_setting:
                img_main_setting.startAnimation(rotateAnimation);
                //封装了popupwindow，并实现动画效果
                MyPopWindow myPopWindow = new MyPopWindow(MainActivity.this);
                myPopWindow.showPopupWindow(img_main_setting);
                break;
            case R.id.img_heart:
                img_heart.startAnimation(scaleAnimation);

            default:
                break;
        }

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
}
