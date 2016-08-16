package com.android.administrator.childrensittingposture.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.administrator.childrensittingposture.R;

/**
 * Created by asus on 2016/8/16.
 */
public class SettingDialog extends Dialog{


    public SettingDialog(Context context) {
        super(context);
    }

    public SettingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder{
        private Context context;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }
        public SettingDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final SettingDialog dialog = new SettingDialog(context,R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_setting, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }



//    private void init() {
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_setting, null);
//        setContentView(view);
//        //radiobutton的初始化
//        Button positiveButton=(Button)findViewById(R.id.positiveButton);
//        Button negativeButton=(Button)findViewById(R.id.negativeButton);
//
//        //设置dialog大小
//        Window dialogWindow = getWindow();
//        WindowManager manager = ((Activity) context).getWindowManager();
//        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        dialogWindow.setGravity(Gravity.CENTER);
//        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
//        params.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
//        dialogWindow.setAttributes(params);
//
//
//
//        positiveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        negativeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//    }

}
