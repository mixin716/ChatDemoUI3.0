package com.hyphenate.times.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.hyphenate.times.R;

/**
 * @author GY
 * @date 16/5/26
 * @Description:
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.about);
        getActionBar().setTitle("  关于我们");
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
