/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyphenate.times.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.times.R;
import com.hyphenate.times.utils.DialogError;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends EaseBaseActivity {

    private DialogError.MyDialog loadingDialog ;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // umeng
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng
        MobclickAgent.onPause(this);
    }


    public void showLoading(String msg){
        View contentView = View.inflate(BaseActivity.this, R.layout.dialog_loading,null);
        loadingDialog = new DialogError.MyDialog(BaseActivity.this, R.style.dialog_loading);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.dialog_loading_tv);
        if(!TextUtils.isEmpty(msg)){
            tvMsg.setText(msg);
        }

        loadingDialog.setContentView(contentView);// 设置布局
        loadingDialog.show();
        Window win = loadingDialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
    }

    public void dismissDialog(){
        if(loadingDialog != null){
            loadingDialog.dismiss();
        }
    }

}
