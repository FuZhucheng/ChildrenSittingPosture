package com.android.administrator.childrensittingposture.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.dialog.ToastCommom;

public class WifiActivity extends Activity {
    private int SYSTEM_STATE = 0x3bafda;          //设置状态栏颜色
    private ToastCommom toastCommom;        //自定义吐司

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_wifi);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_wifi);


        Button btnConfirm=(Button)findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(WifiActivity.this,MainActivity.class);
                startActivity(intent);
                toastCommom = ToastCommom.createToastConfig();
                toastCommom.ToastShow(WifiActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "设置wifi密码成功");
            }
        });
        ImageView img_wifi_back=(ImageView)findViewById(R.id.img_wifi_back);
        img_wifi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_historyBack=new Intent();
                intent_historyBack.setClass(WifiActivity.this,MainActivity.class);
                startActivity(intent_historyBack);
            }
        });
    }
}
