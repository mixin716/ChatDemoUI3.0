package com.hyphenate.times.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.times.R;

/**
 * @author GY
 * @date 16/5/25
 * @Description:错误dialog提示
 */
public class DialogError {
    private static MyDialog loadingDialog;

    public static void showError(Context context, String errorMsg) {
        View contentView = View.inflate(context, R.layout.dialog_error, null);
        loadingDialog = new MyDialog(context, R.style.dialog_loading_default);
        LinearLayout llMain = (LinearLayout) contentView.findViewById(R.id.dilog_error_ll_main);
        TextView tvError = (TextView) contentView.findViewById(R.id.dialog_error_tv_msg);
        TextView tvSure = (TextView) contentView.findViewById(R.id.dialog_error_tv_sure);
        if (!TextUtils.isEmpty(errorMsg)) {
            tvError.setText(errorMsg);
        }
        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.dismiss();
            }
        });


        loadingDialog.setContentView(contentView);// 设置布局
        loadingDialog.show();
        Window win = loadingDialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
    }

    public static void disDialog() {
        if(loadingDialog != null){
            loadingDialog.dismiss();
        }
    }

    public static class MyDialog extends Dialog {

        public MyDialog(Context context) {
            super(context);
            setOwnerActivity((Activity) context);
        }

        public MyDialog(Context context, int theme) {
            super(context, theme);
            setOwnerActivity((Activity) context);
        }

        protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
            setOwnerActivity((Activity) context);
        }
    }

}
