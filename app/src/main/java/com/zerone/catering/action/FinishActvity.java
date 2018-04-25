package com.zerone.catering.action;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2017/9/18 0018.
 * �ر�activityͨ�õķ���
 */

public class FinishActvity implements View.OnClickListener {

     private Activity activity;

    public FinishActvity(Activity activity) {
         this.activity=activity;
    }

    @Override
    public void onClick(View v) {
           activity.finish();
    }
}
