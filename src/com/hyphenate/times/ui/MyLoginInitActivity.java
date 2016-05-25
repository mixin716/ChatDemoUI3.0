package com.hyphenate.times.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.times.R;

/**
 * @author GY
 * @date 16/5/25
 * @Description:
 */
public class MyLoginInitActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvMsg,tvNext;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_login_init);
        getActionBar().setTitle("    欢迎使用Times");
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        intiView();
    }

    private void intiView(){
        tvMsg = (TextView) findViewById(R.id.login_init_tv_msg);
        tvNext = (TextView) findViewById(R.id.login_init_tv_agree);
        tvNext.setOnClickListener(this);
        String clause = tvMsg.getText().toString().trim();
        SpannableString spannableString = new SpannableString(clause);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#00887a")), clause.indexOf("的") , clause.indexOf("的") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvMsg.setText(spannableString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_init_tv_agree:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
