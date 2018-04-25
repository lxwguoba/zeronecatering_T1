package com.zerone.catering.action;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2017/9/15 0015.
 */

public class BackAction implements View.OnClickListener {
     private Activity acticity;

    public BackAction(Activity acticity) {
        this.acticity=acticity;
    }

    @Override
    public void onClick(View v) {
        acticity.finish();
    }
}
