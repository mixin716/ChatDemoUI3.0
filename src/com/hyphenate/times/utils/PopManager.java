package com.hyphenate.times.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.times.DemoApplication;
import com.hyphenate.times.R;

/**
 * @author GY
 * @date 16/5/27
 * @Description:
 */
public class PopManager {

    /** main pop*/
    public static void showPopMain(Context context, View rootView, final View.OnClickListener onClickListener){
        View view = View.inflate(context, R.layout.pop_main_right,null);
        TextView tvSet = (TextView) view.findViewById(R.id.pop_main_tv_set);
        LinearLayout llMain = (LinearLayout) view.findViewById(R.id.pop_set_main);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (DemoApplication.screenHeight - DemoApplication.frameTop));
        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        tvSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

}
