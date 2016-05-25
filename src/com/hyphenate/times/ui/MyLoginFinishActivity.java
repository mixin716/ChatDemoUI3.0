package com.hyphenate.times.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.times.R;

/**
 * @author GY
 * @date 16/5/25
 * @Description:
 */
public class MyLoginFinishActivity extends BaseActivity implements View.OnClickListener{

    private View viewTop;
    private EditText etName;
    private TextView tvNext,tvLength;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_login_finish);
        initView();
    }

    private void initView(){
        viewTop = View.inflate(this,R.layout.login_finish_top,null);
        tvNext = (TextView) viewTop.findViewById(R.id.login_finish_next);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewTop);
        etName = (EditText) findViewById(R.id.login_finish_et_name);
        tvLength = (TextView) findViewById(R.id.login_finish_tv_length);
        tvNext.setOnClickListener(this);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String msg = s.toString();
                int legth = msg.length();
                if(legth> 25){
                    msg = msg.substring(0,25);
                    etName.setText(msg);
                    tvLength.setText("0");
                } else {
                    tvLength.setText((25 - legth)+"");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_finish_next:
                Intent intent = new Intent(this,MyLoginInitActivity.class);
                startActivity(intent);
                break;
        }
    }
}
