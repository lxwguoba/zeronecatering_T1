package com.zerone.catering.action;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.zerone.catering.constant.ConstantS;
import com.zerone.catering.setbasedata.TestJson;

/**
 * Created by on 2017/10/27 0027 11 40.
 * Author  LiuXingWen
 * service
 * 这个是用来做动作处理的
 */

public class OptionsAction  implements View.OnClickListener {
    private Intent intent;
    private Activity activity;

    public OptionsAction( Activity activity) {
        this.intent = activity.getIntent();
        this.activity = activity;
    }
    @Override
    public void onClick(View view) {


    }
}
