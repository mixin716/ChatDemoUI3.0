package com.hyphenate.times.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.times.R;

/**
 * @author GY
 * @date 16/5/26
 * @Description:
 */
public class PhoneFragment extends Fragment {

    private View mRoot;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        if(mRoot== null){
            mRoot = inflater.inflate(R.layout.fragment_phone,null);
        }
        return mRoot;
    }
}
