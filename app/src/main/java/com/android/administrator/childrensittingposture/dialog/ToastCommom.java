package com.android.administrator.childrensittingposture.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.administrator.childrensittingposture.R;

/**
 * Created by asus on 2016/7/25.
 */
public class ToastCommom {

    private static ToastCommom toastCommom;

    private Toast toast;

    private ToastCommom() {
    }

    public static ToastCommom createToastConfig() {
        if (toastCommom == null) {
            toastCommom = new ToastCommom();
        }
        return toastCommom;
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param root
     * @param tvString
     */

    public void ToastShow(Context context, ViewGroup root, String tvString) {
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_xml, root);
        TextView text = (TextView) layout.findViewById(R.id.tv_Toast);
        ImageView mImageView = (ImageView) layout.findViewById(R.id.iv);
        mImageView.setBackgroundResource(R.drawable.bear);
        text.setText(tvString);
//        text.setTextColor(R.color.white);
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 750);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}