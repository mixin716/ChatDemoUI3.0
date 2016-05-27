package com.hyphenate.times.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.times.DemoApplication;
import com.hyphenate.times.DemoHelper;
import com.hyphenate.times.R;
import com.hyphenate.times.utils.DialogError;
import com.hyphenate.times.utils.LoginModel;
import com.hyphenate.times.utils.SharedPrefUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author GY
 * @date 16/5/25
 * @Description:
 */
public class MyLoginCheckActivity extends BaseActivity implements View.OnClickListener,Callback{

    private EditText etCode;
    private LinearLayout llResend;
    private TextView tvPhone,tvResend;
    private ImageView imgResend;
    private String phone,code;
    private boolean resendFlag = true;
    private int time = 60;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_login_check);
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
        getActionBar().setTitle("  验证 "+code+" "+phone);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.em_connect, menu);
        return true;
    }

    private void initView(){
        etCode = (EditText) findViewById(R.id.activity_login_check_et_code);
        llResend = (LinearLayout) findViewById(R.id.activity_login_check_ll_resend);
        tvPhone = (TextView) findViewById(R.id.activity_login_check_tv_phone);
        tvResend = (TextView) findViewById(R.id.activity_login_check_tv_resend);
        imgResend = (ImageView) findViewById(R.id.activity_login_check_img_resend);

        llResend.setOnClickListener(this);
        tvPhone.setText(code+" "+phone+"。电话号码不正确吗？");
        String clause = tvPhone.getText().toString().trim();
        SpannableString spannableString = new SpannableString(clause);
        spannableString.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);//设置超链
                ds.setColor(Color.parseColor("#FF0000"));
            }

            @Override
            public void onClick(View widget) {
                //设置超链点击事件
                MyLoginCheckActivity.this.finish();
            }
        },clause.indexOf("。")+1,clause.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#09ABF4")), clause.indexOf("。") + 1,clause.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPhone.setText(spannableString);
        tvPhone.setMovementMethod(LinkMovementMethod.getInstance());

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    if(s.length() == 6){
                        showLoading("");
                        SharedPrefUtil.put("phone",code+phone);
                        LoginModel.sendVerify(code+phone,s.toString(),MyLoginCheckActivity.this);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_login_check_ll_resend:
                if(resendFlag){
                    resendFlag = false;
                    showLoading("正在发送，请稍后...");
                    handler.sendEmptyMessage(0);
                    LoginModel.sendCode(code + phone, new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            dismissDialog();
                        }
                    });
                }
                break;
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if(time == 0){
                resendFlag = true;
                tvResend.setText("重发短信");
                tvResend.setTextColor(MyLoginCheckActivity.this.getResources().getColor(R.color.common_bg_color));
                imgResend.setImageResource(R.drawable.ic_reg_sms_normal);
            } else {
                resendFlag = false;
                tvResend.setText(time+"秒后重发");
                tvResend.setTextColor(Color.parseColor("#999999"));
                imgResend.setImageResource(R.drawable.ic_reg_sms_disabled);
                handler.sendEmptyMessageDelayed(0,1000);
            }
        }
    };

    @Override
    public void onFailure(Request request, IOException e) {
        dismissDialog();
    }

    @Override
    public void onResponse(Response response) throws IOException {

        String result = response.body().string();
        try {
            Log.e("---",result+"  "+result.length());
            result = result.substring(result.indexOf("{"),result.indexOf("}")+1);
            JSONObject obj = new JSONObject(result);
            int code = obj.optInt("code");
            int error_type = obj.optInt("error_type");

            if(code == 1){
                try {
                    EMClient.getInstance().createAccount(phone, phone);
                    DemoHelper.getInstance().setCurrentUserName(phone);
                    try {
                        Thread.sleep(3 * 10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dismissDialog();
                    EMClient.getInstance().login(phone, phone, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();

                            // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                            boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                                    DemoApplication.currentUserNick.trim());
                            if (!updatenick) {
                                Log.e("LoginActivity", "update current user nick fail");
                            }
                            //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
                            DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                            Intent intent = new Intent(MyLoginCheckActivity.this,MyLoginFinishActivity.class);
                            startActivity(intent);
                            setResult(101);
                            MyLoginCheckActivity.this.finish();
                        }

                        @Override
                        public void onError(int i, String s) {

                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                } catch (HyphenateException e) {
                    dismissDialog();
                    e.printStackTrace();
                    int errorCode=e.getErrorCode();
                    Log.e("--",errorCode+"---");
                    if(errorCode == EMError.USER_ALREADY_EXIST){
                        EMClient.getInstance().login(phone, phone, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                EMClient.getInstance().groupManager().loadAllGroups();
                                EMClient.getInstance().chatManager().loadAllConversations();

                                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                                        DemoApplication.currentUserNick.trim());
                                if (!updatenick) {
                                    Log.e("LoginActivity", "update current user nick fail");
                                }
                                //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
                                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                                Intent intent = new Intent(MyLoginCheckActivity.this,MyLoginFinishActivity.class);
                                startActivity(intent);
                                setResult(101);
                                MyLoginCheckActivity.this.finish();
                            }

                            @Override
                            public void onError(int i, String s) {

                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                    }
                }

            } else {
                dismissDialog();
                errorHandler.sendEmptyMessage(error_type);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Handler errorHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    DialogError.showError(MyLoginCheckActivity.this,"验证码不对");
                    break;
                case 2:
                    DialogError.showError(MyLoginCheckActivity.this,"已注册");
                    break;
                case 3:
                    DialogError.showError(MyLoginCheckActivity.this,"保存用户失败");
                    break;
                case 4:
                    DialogError.showError(MyLoginCheckActivity.this,"验证码过期");
                    break;
            }
        }
    };
}
