package com.android.administrator.childrensittingposture.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.android.administrator.childrensittingposture.dao.SendRequest;
import com.android.administrator.childrensittingposture.dialog.AddPopWindow;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tv_studyOrRest;            //学习或休息状态
    private TextView tv_studyOrRestTime;        //学习或休息时间
    private TextView tv_mainRemind;                 //提醒学习或休息按键
    private TextView  tv_main_todayLearn;       //今天学习了的时间
    private TextView  tv_main_todayRest;            //今天休息了的时间
    private TextView  tv_main_todayRectify;         //今天坐姿修正次数
    private TextView  tv_main_todayScore;           //今天学习得分
    private ImageView img_main_setting;                //设置
    private ImageView img_heart;                        //心心跳动


    private Handler mHandler;
    private SwipeRefreshLayout mSwipeRefreshLayout;                 //下拉刷新的Layout
    private RotateAnimation rotateAnimation;                        //设置的旋转动画
    private ScaleAnimation scaleAnimation;                          //心心跳动效果


    public static final int TODAY_STUDY_CUMULATION_TIME = 1;            //今天学习时间
    public static final int TODAY_REST_CUMULATION_TIME=2;               //今天休息时间
    public static final int REMIND_NUMBER = 3;                                  //今天提醒矫正次数
    public static final int TODAY_SCORE = 4;                                    //今天的得分
    public static final int BAR_DATA = 5;
    public static final int SUCCESS = 7;
    public static final int ALREADY_STUDY=8;                                //已经连续学习
    public static final int ALREADY_REST=9;                                     //已经连续休息

    private int SYSTEM_STATE=0x3bafda;          //设置状态栏颜色

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        tv_studyOrRest=(TextView)findViewById(R.id.tv_studyOrRest);
        tv_studyOrRestTime=(TextView)findViewById(R.id.tv_studyOrRestTime);
        tv_mainRemind=(TextView)findViewById(R.id.tv_mainRemind);
        tv_main_todayLearn=(TextView)findViewById(R.id.tv_main_todayLearn);
        tv_main_todayRest=(TextView)findViewById(R.id.tv_main_todayRest);
        tv_main_todayRectify=(TextView)findViewById(R.id.tv_main_todayRectify);
        tv_main_todayScore=(TextView)findViewById(R.id.tv_main_todayScore);
        img_main_setting=(ImageView)findViewById(R.id.img_main_setting);
        img_heart=(ImageView)findViewById(R.id.img_heart);

        //设置的旋转动画
        rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        //设置心心和提醒跳动效果
        scaleAnimation=new ScaleAnimation(1.0f,1.4f,1.0f,1.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        rotateAnimation.setFillBefore(true);


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case TODAY_STUDY_CUMULATION_TIME:
                        if (msg.obj != null) {
//                            tv_time.setText("1:20:12");
                        }
                        break;
                    case REMIND_NUMBER:
//                            tv_remind.setText("8");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                 tv_frequency.setText("8");
//                                CultivateDb firstDb = DataSupport.findFirst(CultivateDb.class);
//                                tv_frequency.setText(firstDb.getRemindFrequency()+"");

                            }
                        });


                        break;
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
            case R.id.tv_mainRemind :
                if (tv_mainRemind.getText().toString()==getString(R.string.rest)) {
                    tv_mainRemind.setTextSize(19f);
                    tv_mainRemind.setText(getString(R.string.study));
                    tv_mainRemind.startAnimation(scaleAnimation);
                }
                else if (tv_mainRemind.getText().toString()==getString(R.string.study)){
                    tv_mainRemind.setTextSize(23f);
                    tv_mainRemind.setText(getString(R.string.rest));
                    tv_mainRemind.startAnimation(scaleAnimation);
                }
//                tv_mainRemind.setTextSize(19f);
//                tv_mainRemind.setText("是时候学习了喔!");
                break;
            case R.id.img_main_setting :
                img_main_setting.startAnimation(rotateAnimation);
                //封装了popupwindow，并实现动画效果
                AddPopWindow addPopWindow = new AddPopWindow(MainActivity.this);
                addPopWindow.showPopupWindow(img_main_setting);
                break;
            case R.id.img_heart :
                img_heart.startAnimation(scaleAnimation);

            default:
                break;
        }

    }

}
