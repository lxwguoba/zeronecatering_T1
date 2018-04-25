package com.zerone.catering.thread;

import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.zerone.catering.handler.DownloadHandler;
import com.zerone.catering.handler.MessageHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class DownloadThread implements Runnable {

    private Activity activity;
    private Handler mHandler;
    private String mSavePath;
    private String newVersion;
    private int progress;
    private  String ur;

    public static boolean cancelUpdate = false;

    public DownloadThread(Activity activity, Handler mHandler, String mSavePath, String newVersion, String ur) {
        super();
        this.activity = activity;
        this.mHandler = mHandler;
        this.mSavePath = mSavePath;
        this.newVersion = newVersion;
        this.ur= ur;
    }

    @Override
    public void run() {
        try {

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cancelUpdate = false;
                URL url=new URL(ur);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                int length = conn.getContentLength();
                // ����������
                InputStream is = conn.getInputStream();

                File file = new File(mSavePath);

                if (!file.exists()) {
                    file.mkdir();
                }
                File apkFile = new File(mSavePath, "lykj.apk");
                FileOutputStream fos = new FileOutputStream(apkFile);
                int count = 0;

                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;

                    progress = (int) (((float) count / length) * 100);
                    if(progress == 99){
                        progress = 100;
                    }
                    if (numread <= 0) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = DownloadHandler.DOWNLOAD_FINISH;
                        msg.arg1 = progress;
                        mHandler.sendMessage(msg);
                        break;
                    } else {
                        Message msg = mHandler.obtainMessage();
                        msg.what = DownloadHandler.DOWNLOAD;
                        msg.arg1 = progress;
                        mHandler.sendMessage(msg);
                    }
                    fos.write(buf, 0, numread);
                } while (!DownloadThread.cancelUpdate);
                fos.close();
                is.close();
            } else {
                Handler handler = new MessageHandler(activity);
                Toast.makeText(activity, "没有找到对应的apk",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(activity, "下载失败，请重新打开app" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
