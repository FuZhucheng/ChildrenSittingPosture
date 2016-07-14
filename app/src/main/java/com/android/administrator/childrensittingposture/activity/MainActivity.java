package com.android.administrator.childrensittingposture.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.administrator.childrensittingposture.R;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    private TextView tv_time;
    private TextView tv_frequency;
    private TextView tv_state;
    private TextView tv_remind;
    private TextView tv_automaticallyRemind;
    private TextView tv_CumulativeQuantity;
    private TextView tv_history;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();




}
    private void initView(){
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_frequency=(TextView)findViewById(R.id.tv_frequency);
        tv_state=(TextView)findViewById(R.id.tv_state);
        tv_remind=(TextView)findViewById(R.id.tv_remind);
        tv_automaticallyRemind=(TextView)findViewById(R.id.tv_automaticallyRemind);
        tv_CumulativeQuantity=(TextView)findViewById(R.id.tv_CumulativeQuantity);
        tv_history=(TextView)findViewById(R.id.tv_history);

    }
    private void initListener(){
        tv_time.setOnClickListener(this);
        tv_frequency.setOnClickListener(this);
        tv_state.setOnClickListener(this);
        tv_remind.setOnClickListener(this);
        tv_automaticallyRemind.setOnClickListener(this);
        tv_CumulativeQuantity.setOnClickListener(this);
        tv_history.setOnClickListener(this);

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_time  :
                break;
            case R.id.tv_automaticallyRemind  :
                Intent intent_automaticallyRemind=new Intent();
                intent_automaticallyRemind.setClass(MainActivity.this,SettingActivity.class);
                startActivity(intent_automaticallyRemind);
                break;
            case R.id.tv_history  :
                Intent intent_history=new Intent();
                intent_history.setClass(MainActivity.this,HistoryActivity.class);
                startActivity(intent_history);
                break;
            default:
                break;
        }

    }
}
