package com.hyphenate.times.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.times.R;
import com.hyphenate.times.utils.SharedPrefUtil;

/**
 * @author GY
 * @date 16/5/27
 * @Description:
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvName;
    private LinearLayout llAccount,llAbout;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_set);
        getActionBar().setTitle("  设置");
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView(){
        tvName = (TextView) findViewById(R.id.set_tv_name);
        llAbout = (LinearLayout) findViewById(R.id.set_ll_about);
        llAccount = (LinearLayout) findViewById(R.id.set_ll_account);

        llAbout.setOnClickListener(this);
        llAccount.setOnClickListener(this);
        tvName.setText(SharedPrefUtil.get("name",""));
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_ll_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.set_ll_account:
                startActivity(new Intent(this, MyCountActivity.class));
                break;
        }
    }
}
