package com.android.administrator.childrensittingposture.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.activity.HeightActivity;
import com.android.administrator.childrensittingposture.activity.HistoryActivity;
import com.android.administrator.childrensittingposture.activity.SettingActivity;
import com.android.administrator.childrensittingposture.activity.WifiActivity;
import com.android.administrator.childrensittingposture.bean.CultivateDb;

import org.litepal.crud.DataSupport;

/**
 * Created by 符柱成 on 2016/7/24.
 */
public class MyPopWindow extends PopupWindow {
    private View conentView;
    private Activity context;

    public MyPopWindow(final Activity context) {
        super(context);
        this.context = context;
        this.initPopupWindow();


    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popuo_dialog, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 + 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
        LinearLayout llayout_remind = (LinearLayout) conentView
                .findViewById(R.id.llayout_remind);
        LinearLayout llayout_history = (LinearLayout) conentView
                .findViewById(R.id.llayout_history);
        LinearLayout llayout_height = (LinearLayout) conentView
                .findViewById(R.id.llayout_height);
        LinearLayout llayout_wifi = (LinearLayout) conentView
                .findViewById(R.id.llayout_wifi);
        llayout_remind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent_automaticallyRemind = new Intent();
                intent_automaticallyRemind.setClass(context, SettingActivity.class);
                context.startActivity(intent_automaticallyRemind);
            }
        });
        llayout_wifi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent_wifi = new Intent();
                intent_wifi.setClass(context, WifiActivity.class);
                context.startActivity(intent_wifi);
            }
        });
        llayout_height.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent_height = new Intent();
                intent_height.setClass(context, HeightActivity.class);
                context.startActivity(intent_height);
            }
        });
        llayout_history.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 CultivateDb popDb = DataSupport.findLast(CultivateDb.class);
                if (popDb ==null){
                    PopDialog.Builder builder=new PopDialog.Builder(context);
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                        }
                    });
                    builder.setNegativeButton("取消",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();

                }else {
                    Intent intent_history = new Intent();
                    intent_history.setClass(context, HistoryActivity.class);
                    context.startActivity(intent_history);
                }
            }
        });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }
}