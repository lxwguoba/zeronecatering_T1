package com.zerone.catering.action.orderfragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.zerone.catering.activitys.table.TableCtrlActivity;

/**
 * Created by Administrator on 2017/10/12 0012.
 * 用来跳转到tablectrlactivity
 */

public class GoToTableActivityAction implements View.OnClickListener {
    private Activity activity;

    public GoToTableActivityAction(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent =new Intent(activity, TableCtrlActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
