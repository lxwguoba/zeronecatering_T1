package com.zerone.catering.action.ckeckupdataapp;

import android.view.View;

import com.zerone.catering.thread.DownloadThread;


/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class CancelUpdateAction implements View.OnClickListener {

	public CancelUpdateAction() {
        super();
    }

    @Override
     public void onClick(View v) {
        DownloadThread.cancelUpdate = true;
    }
}
