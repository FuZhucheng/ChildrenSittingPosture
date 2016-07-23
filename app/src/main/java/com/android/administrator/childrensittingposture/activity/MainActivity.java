package com.android.administrator.childrensittingposture.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.dao.SendRequest;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    private TextView tv_frequency;
    private TextView tv_state;
    private TextView tv_remind;


    private Handler mHandler;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static final int CUMULATION_TIME=1;
    public static final int REMIND_NUMBER=2;
    public static final int SUM_REMIND_NUMBER=3;
    public static final int BAR_DATA=4;
    public static final int SUCCESS=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);


        initView();
        initListener();



}
    private void initView(){

        tv_frequency=(TextView)findViewById(R.id.tv_frequency);
        tv_state=(TextView)findViewById(R.id.tv_state);
        tv_remind=(TextView)findViewById(R.id.tv_remind);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case CUMULATION_TIME :
                        if (msg.obj!=null){
//                            tv_time.setText("1:20:12");
                        }
                        break;
                    case REMIND_NUMBER :
//                            tv_remind.setText("8");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                        tv_frequency.setText("8");
//                                CultivateDb firstDb = DataSupport.findFirst(CultivateDb.class);
//                                tv_frequency.setText(firstDb.getRemindFrequency()+"");

                            }
                        });


                        break;
                }
            }
        };


        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.srlayout);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //自定义加载的圆形背景颜色
//        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.gray);
        //自定义加载的圆条颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.title_blue,R.color.green,R.color.orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
//                        Log.e("readytosend",time+"");
                        new SendRequest().SendUid(1,mHandler,MainActivity.this);
                    }
                }, 2500);
                //正常情况下是在加载完成后回调，这里简单模拟延时
                Toast.makeText(MainActivity.this,"正在刷新",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"刷新完成",Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },3000);//6000指延时6s
            }
        });
    }


    private void initListener(){

        tv_frequency.setOnClickListener(this);
        tv_state.setOnClickListener(this);
        tv_remind.setOnClickListener(this);

    }
    public void onClick(View view){
        switch (view.getId()){

//            case R.id.tv_automaticallyRemind  :
//                Intent intent_automaticallyRemind=new Intent();
//                intent_automaticallyRemind.setClass(MainActivity.this,SettingActivity.class);
//                startActivity(intent_automaticallyRemind);
//                break;
//            case R.id.tv_history  :
//                Intent intent_history=new Intent();
//                intent_history.setClass(MainActivity.this,HistoryActivity.class);
//                startActivity(intent_history);
//                break;

            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        switch (item_id) {
            case R.id.remindStatistics:
                Intent intent_history=new Intent();
                intent_history.setClass(MainActivity.this,HistoryActivity.class);
                startActivity(intent_history);
                break;
            case R.id.automaticSetting:
                Intent intent_automaticallyRemind=new Intent();
                intent_automaticallyRemind.setClass(MainActivity.this,SettingActivity.class);
                startActivity(intent_automaticallyRemind);
                break;
            default:
                return false;
        }
        return true;
    }
}
