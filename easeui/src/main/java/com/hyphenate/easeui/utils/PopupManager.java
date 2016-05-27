package com.hyphenate.easeui.utils;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyphenate.easeui.R;

/**
 * @author GY
 * @date 16/5/27
 * @Description:
 */
public class PopupManager {

    public static void showSelect(final Context context, View rootView) {
        View view = View.inflate(context, R.layout.pop_chat_select, null);

        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout llMain = (LinearLayout) view.findViewById(R.id.pop_select_main);
        LinearLayout lldocument = (LinearLayout) view.findViewById(R.id.pop_select_ll_document);
        LinearLayout llcamera = (LinearLayout) view.findViewById(R.id.pop_select_ll_camera);
        LinearLayout llgallery = (LinearLayout) view.findViewById(R.id.pop_select_ll_gallery);
        LinearLayout llaudio = (LinearLayout) view.findViewById(R.id.pop_select_ll_audio);
        LinearLayout lllocation = (LinearLayout) view.findViewById(R.id.pop_select_ll_location);
        LinearLayout llcontact = (LinearLayout) view.findViewById(R.id.pop_select_ll_contact);
        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        lldocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("chat");
                intent.putExtra("select", "document");
                context.sendBroadcast(intent);
                popupWindow.dismiss();
            }
        });
        llcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("chat");
                intent.putExtra("select", "camera");
                context.sendBroadcast(intent);
                popupWindow.dismiss();
            }
        });
        llgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("chat");
                intent.putExtra("select", "gallery");
                context.sendBroadcast(intent);
                popupWindow.dismiss();
            }
        });
        llaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("chat");
                intent.putExtra("select", "audio");
                context.sendBroadcast(intent);
                popupWindow.dismiss();
            }
        });
        lllocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("chat");
                intent.putExtra("select", "location");
                context.sendBroadcast(intent);
                popupWindow.dismiss();
            }
        });
        llcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("chat");
                intent.putExtra("select", "contact");
                context.sendBroadcast(intent);
                popupWindow.dismiss();
            }
        });

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(rootView);
//        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

}
