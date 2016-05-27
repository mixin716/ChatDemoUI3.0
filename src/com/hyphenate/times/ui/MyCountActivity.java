package com.hyphenate.times.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.hyphenate.times.R;
import com.hyphenate.times.utils.SharedPrefUtil;

/**
 * @author GY
 * @date 16/5/26
 * @Description:
 */
public class MyCountActivity extends BaseActivity {

    private TextView tvName,tvPhone;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_my_acount);
        getActionBar().setTitle("  账号信息");
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView(){
        tvName = (TextView) findViewById(R.id.my_acount_tv_name);
        tvPhone = (TextView) findViewById(R.id.my_acount_tv_phone);
        tvName.setText(SharedPrefUtil.get("name",""));
        tvPhone.setText(SharedPrefUtil.get("phone",""));
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
}
