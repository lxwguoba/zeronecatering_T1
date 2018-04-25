package com.zerone.catering.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2017/8/9.
 */

public class OutActivityUtils {

    public  static void out(final Activity activity, String title , String msg, final ACache mCache, final Handler handler){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //  改改
                mCache.remove("foodjson");
                Message message =new Message();
                message.what=120000;
                handler.sendMessage(message);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

}
