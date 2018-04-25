package com.zerone.catering.dialog;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.zerone.catering.R;
import com.zerone.catering.base.BaseActvity;

/**
 * Created by Administrator on 2017/7/24.
 */

public class LoginProgressBar extends BaseActvity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_loginprogressbar);
    }
}
