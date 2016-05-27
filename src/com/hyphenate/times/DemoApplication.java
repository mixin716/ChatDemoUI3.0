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
package com.hyphenate.times;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.hyphenate.times.utils.SharedPrefUtil;

public class DemoApplication extends Application {

	public static Context applicationContext;
	private static DemoApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
	public static int actionbarHeight = 0;
	/**
	 * 手机屏幕的高度和宽度
	 */
	public static int screenWidth;
	public static int screenHeight;
	public static float density;
	public static float curDensity;// 实际的density
	public static float frameTop;//状态栏高度
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";

	@Override
	public void onCreate() {
		MultiDex.install(this);
		super.onCreate();
        applicationContext = this;
        instance = this;
		SharedPrefUtil.init(getApplicationContext());
		//init demo helper
        DemoHelper.getInstance().init(applicationContext);
		getScreenSize();
	}

	public static DemoApplication getInstance() {
		return instance;
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	/**
	 * 获取屏幕分辨率方法
	 */
	private void getScreenSize() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getApplicationContext().getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		density = dm.density;
		curDensity = (float) screenWidth / 320f;
	}

}
