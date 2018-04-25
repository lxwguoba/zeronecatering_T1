package com.zerone.catering.action.ckeckupdataapp;

import android.app.Activity;
import android.content.Intent;

import com.zerone.catering.activitys.branch.UpdateActivity;
import com.zerone.catering.utils.PromptUtil;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class InitUpdateActivity implements PromptUtil.PromptOk {

    private Activity activity;
    private String version;
    private  String url;

    public InitUpdateActivity(Activity activity, String version, String url) {
        super();
        this.activity = activity;
        this.version = version;
        this.url=url;
    }

    @Override
    public void click() {
        Intent intent = new Intent(activity, UpdateActivity.class);
        intent.putExtra("version", version);
        intent.putExtra("url", url);
        activity.startActivity(intent);
        activity.finish();
    }

}