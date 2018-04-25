package com.zerone.catering.activitys.branch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.action.ckeckupdataapp.CancelUpdateAction;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.handler.DownloadHandler;
import com.zerone.catering.thread.DownloadThread;

/**
 * 更新app的页面
 */
public class UpdateActivity extends BaseActvity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		Intent intent = getIntent();
		String version = intent.getStringExtra("version");
		String url = intent.getStringExtra("url");
		registerCompant(version);
		startDownload(version,url);

	}




	private void registerCompant(String appVersion) {

		Button cancel = (Button) findViewById(R.id.update_cancel);
		cancel.setOnClickListener(new CancelUpdateAction());
	}



	/**
	 *
	 *  对比版本是否相同  不同则以服务器的版本为主  更新安装
	 * @param appVersion
	 */
	private void startDownload(String appVersion,String url) {
		String sdpath = Environment.getExternalStorageDirectory() + "/";
		String mSavePath = sdpath + "download";
		ProgressBar mProgress = (ProgressBar) findViewById(R.id.update_progress);
		TextView flag = (TextView) findViewById(R.id.update_int);
		Handler handler = new DownloadHandler(this, mProgress, flag, mSavePath, appVersion);
		Thread thread = new Thread(new DownloadThread(this, handler, mSavePath, appVersion,url));
		thread.start();
	}
}
