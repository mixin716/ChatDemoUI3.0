package com.hyphenate.times.utils;

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
 * @Description:
 */
public class DialogPhone {

    private static DialogError.MyDialog loadingDialog;

    public static void showDialog(Context context, String phone, final View.OnClickListener clickListener){
        View contentView = View.inflate(context, R.layout.dialog_phone,null);
        loadingDialog = new DialogError.MyDialog(context, R.style.dialog_loading_default);
        TextView tvPhone = (TextView) contentView.findViewById(R.id.dialog_phone_tv_phone);
        TextView tvSure = (TextView) contentView.findViewById(R.id.dialog_phone_tv_sure);
        TextView tvEdit = (TextView) contentView.findViewById(R.id.dialog_phone_tv_edit);
        if(!TextUtils.isEmpty(phone)){
            tvPhone.setText(phone);
        }
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v);
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
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


    public static void disDialog(){
        if(loadingDialog != null){
            loadingDialog.dismiss();
        }
    }
}
