package com.hyphenate.times.utils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * @author GY
 * @date 16/5/25
 * @Description:
 */
public class LoginModel {

    /** 发送验证码*/
    public static void sendCode(String phone,Callback callBack){
        OkHttpClient client = new OkHttpClient();
        String url = Urls.login_get_code + phone;
        final Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    /** 验证验证码*/
    public static void sendVerify(String phone,String code,Callback callBack){
        OkHttpClient client = new OkHttpClient();
        String url = Urls.login_get_verify + phone+"&verifycode="+code;
        final Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    /** 登录*/
    public static void sendLogin(String phone,Callback callBack){
        OkHttpClient client = new OkHttpClient();
        String url = Urls.login + phone;
        final Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

}
