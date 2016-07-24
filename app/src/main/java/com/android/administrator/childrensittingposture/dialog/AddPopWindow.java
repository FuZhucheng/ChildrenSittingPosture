package com.android.administrator.childrensittingposture.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.activity.HistoryActivity;
import com.android.administrator.childrensittingposture.activity.SettingActivity;

/**
 * Created by asus on 2016/7/24.
 */
public class AddPopWindow extends PopupWindow {
    private View conentView;

    public AddPopWindow(final Activity context) {
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
        llayout_remind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                AddPopWindow.this.dismiss();
                Intent intent_automaticallyRemind = new Intent();
                intent_automaticallyRemind.setClass(context, SettingActivity.class);
                context.startActivity(intent_automaticallyRemind);
            }
        });

        llayout_history.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_history = new Intent();
                intent_history.setClass(context, HistoryActivity.class);
                context.startActivity(intent_history);
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