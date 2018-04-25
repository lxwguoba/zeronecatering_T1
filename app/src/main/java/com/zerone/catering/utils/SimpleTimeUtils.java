package com.zerone.catering.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/5.
 * 时间选择
 */

public class SimpleTimeUtils {

    public static   void selectTime(Activity c, final Handler timeHandler, final String timeState){

        DatePickDialog dialog = new DatePickDialog(c);
        //
        dialog.setYearLimt(50);
        //
        dialog.setTitle("选择时间");
        //
        dialog.setType(DateType.TYPE_ALL);
        //
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //
        dialog.setOnChangeLisener(null);
        //
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                long lt=date.getTime();
                SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String time=   sdf.format(date);
                Message msg  = new Message();
                if ("start".equals(timeState)){
                    msg.what = 2017;
                }else if ("end".equals(timeState)){
                    msg.what = 2018;
                }

                msg.obj = time;
                timeHandler.sendMessage(msg);
            }
        });
        dialog.show();
    }
}
