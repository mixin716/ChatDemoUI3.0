package com.hyphenate.times.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.times.DemoApplication;
import com.hyphenate.times.DemoHelper;
import com.hyphenate.times.R;
import com.hyphenate.times.utils.PopManager;

/**
 * 开屏页
 */
public class SplashActivity extends BaseActivity {

    private TextView agree;
    private Intent intent;
    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle arg0) {
        setContentView(R.layout.em_activity_splash);
        super.onCreate(arg0);

        frameHandler.sendEmptyMessageDelayed(0, 1000);
        agree = (TextView) findViewById(R.id.iv_splash_agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DemoHelper.getInstance().isLoggedIn()) {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();

                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } else {
                    intent = new Intent(SplashActivity.this, MyLoginSelectPhoneActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private Handler frameHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Rect frame = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            DemoApplication.frameTop = frame.top;
        }
    };


    /**
     * 获取当前应用程序的版本号
     */
    private String getVersion() {
        return EMClient.getInstance().getChatConfig().getVersion();
    }
}
