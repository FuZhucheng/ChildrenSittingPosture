package com.android.administrator.childrensittingposture.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.dialog.ToastCommom;

public class HeightActivity extends Activity {
    private ToastCommom toastCommom;        //自定义吐司

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_height);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_height);
        Button btnConfirm=(Button)findViewById(R.id.btnConfirmHeight);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(HeightActivity.this,MainActivity.class);
                startActivity(intent);
                toastCommom = ToastCommom.createToastConfig();
                toastCommom.ToastShow(HeightActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "设置孩子身高成功");
            }
        });
        ImageView img_height_back=(ImageView)findViewById(R.id.img_height_back);
        img_height_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_historyBack=new Intent();
                intent_historyBack.setClass(HeightActivity.this,MainActivity.class);
                startActivity(intent_historyBack);
            }
        });
    }
}
