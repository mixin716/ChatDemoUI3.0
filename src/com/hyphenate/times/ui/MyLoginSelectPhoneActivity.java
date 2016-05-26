package com.hyphenate.times.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.times.DemoApplication;
import com.hyphenate.times.R;
import com.hyphenate.times.country.CharacterParserUtil;
import com.hyphenate.times.country.CountryActivity;
import com.hyphenate.times.country.CountrySortModel;
import com.hyphenate.times.country.GetCountryNameSort;
import com.hyphenate.times.utils.DialogError;
import com.hyphenate.times.utils.DialogPhone;
import com.hyphenate.times.utils.LoginModel;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author GY
 * @date 16/5/24
 * @Description:
 */
public class MyLoginSelectPhoneActivity extends BaseActivity implements Callback{

    private String TAG = "XINWEI";
    private LinearLayout relative_choseCountry;
    private EditText editText_countryNum,etPhone;
    private TextView tv_countryName;
    private ImageView imgNext;
    private GetCountryNameSort countryChangeUtil;
    private CharacterParserUtil characterParserUtil;
    private List<CountrySortModel> mAllCountryList;
    private String beforText = null;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_login_select_phone);
        getActionBar().setTitle("    验证您的电话号码");
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        initView();
        initCountryList();
        setListener();
        handler.sendEmptyMessageDelayed(0,500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.em_connect, menu);
        return true;
    }


    private void initView() {
        relative_choseCountry = (LinearLayout) findViewById(R.id.rala_chose_country);
        editText_countryNum = (EditText) findViewById(R.id.edt_chosed_country_num);
        etPhone = (EditText) findViewById(R.id.et_usertel);
        tv_countryName = (TextView) findViewById(R.id.tv_chosed_country);
        imgNext = (ImageView) findViewById(R.id.activity_login_phone_img_next);

        mAllCountryList = new ArrayList<CountrySortModel>();
        countryChangeUtil = new GetCountryNameSort();
        characterParserUtil = new CharacterParserUtil();

    }

    private void initCountryList() {
        String[] countryList = getResources().getStringArray(R.array.country_code_list_ch);

        for (int i = 0, length = countryList.length; i < length; i++) {
            String[] country = countryList[i].split("\\*");

            String countryName = country[0];
            String countryNumber = country[1];
            String countrySortKey = characterParserUtil.getSelling(countryName);
            CountrySortModel countrySortModel = new CountrySortModel(countryName, countryNumber,
                    countrySortKey);
            String sortLetter = countryChangeUtil.getSortLetterBySortKey(countrySortKey);
            if (sortLetter == null) {
                sortLetter = countryChangeUtil.getSortLetterBySortKey(countryName);
            }

            countrySortModel.sortLetters = sortLetter;
            mAllCountryList.add(countrySortModel);
        }

    }

    private void setListener() {
        relative_choseCountry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyLoginSelectPhoneActivity.this, CountryActivity.class);
                startActivityForResult(intent, 12);
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etPhone.getText().toString().toString())){
                    DialogError.showError(MyLoginSelectPhoneActivity.this,"请输入您的电话号码");
                } else {
                    DialogPhone.showDialog(MyLoginSelectPhoneActivity.this, editText_countryNum.getText().toString() +"  "+ etPhone.getText().toString().trim(),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showLoading("");
                                    DialogPhone.disDialog();
                                    LoginModel.sendCode(editText_countryNum.getText().toString()+""+etPhone.getText().toString().trim(),
                                            MyLoginSelectPhoneActivity.this);
                                }
                            });
                }
            }
        });

        editText_countryNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                String contentString = editText_countryNum.getText().toString();
                CharSequence contentSeq = editText_countryNum.getText();
                if (contentString.length() > 1) {
                    // 按照输入内容进行匹配
                    List<CountrySortModel> fileterList = (ArrayList<CountrySortModel>) countryChangeUtil
                            .search(contentString, mAllCountryList);
                    if (fileterList.size() == 1) {
                        tv_countryName.setText(fileterList.get(0).countryName);
                    } else {
                        tv_countryName.setText("国家代码无效");
                    }

                } else {
                    if (contentString.length() == 0) {
                        editText_countryNum.setText(beforText);
                        tv_countryName.setText("从列表选择");
                    } else if (contentString.length() == 1 && contentString.equals("+")) {
                        tv_countryName.setText("从列表选择");
                    }
                }

                if (contentSeq instanceof Spannable) {
                    Spannable spannable = (Spannable) contentSeq;
                    Selection.setSelection(spannable, contentSeq.length());
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(resultCode == 101){
            finish();
        }
        switch (requestCode) {
            case 12:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String countryName = bundle.getString("countryName");
                    String countryNumber = bundle.getString("countryNumber");

                    editText_countryNum.setText(countryNumber);
                    tv_countryName.setText(countryName);

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int actionBarHeight = getActionBar().getHeight();
            DemoApplication.actionbarHeight = actionBarHeight;
        }
    };

    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {
        dismissDialog();
        String result = response.body().string();
        try {
            result = result.substring(result.indexOf("{"),result.indexOf("}")+1);
            JSONObject obj = new JSONObject(result);
            int code = obj.optInt("code");
            if(code == 1){
                Intent intent = new Intent(MyLoginSelectPhoneActivity.this,MyLoginCheckActivity.class);
                intent.putExtra("phone",etPhone.getText().toString().trim());
                intent.putExtra("code",editText_countryNum.getText().toString().trim());
                startActivityForResult(intent,101);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
