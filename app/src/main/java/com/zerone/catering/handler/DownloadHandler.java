package com.zerone.catering.handler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

/**
 * 更新app的handler机制
 */
public class DownloadHandler extends Handler{


 	public static final int DOWNLOAD = 1;
      //下载完成时的常量
	public static final int DOWNLOAD_FINISH = 2;

	private Activity activity;
	/**
	 * 下载安装的进度条
	 */
	private ProgressBar mProgress;
	private TextView flag;
	private String mSavePath;
	private String newVersion;
	
	public DownloadHandler(Activity activity, ProgressBar mProgress, TextView flag, String mSavePath, String newVersion) {
		super();
		this.activity = activity;
		this.mProgress = mProgress;
		this.flag = flag;
		this.mSavePath = mSavePath;
		this.newVersion = newVersion;
	}

	public void handleMessage(Message msg) {
		switch (msg.what) {
		case DOWNLOAD:
			mProgress.setProgress(msg.arg1);
			if(msg.arg1 >= 100){
				flag.setText("。。。下载完成，马上安装！");
			}else{
				flag.setText("已下载: " + msg.arg1 + "%");
			}
			break;
		case DOWNLOAD_FINISH:
			installApk();
			break;
		default:
			break;
		}
	};

	/**
	 * 安装apk
	 */
	private void installApk() {



		File apkfile = new File(mSavePath, "lykj.apk");
		if (!apkfile.exists()) {
			return;
		}


		String[] command = {"chmod", "777",apkfile.getPath() };
		ProcessBuilder builder = new ProcessBuilder(command);
		try {
			builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.i("URL","apkfile=="+apkfile.toString());
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.getAbsolutePath()), "application/vnd.android.package-archive");
		activity.startActivity(i);
		activity.finish();
	}
}
