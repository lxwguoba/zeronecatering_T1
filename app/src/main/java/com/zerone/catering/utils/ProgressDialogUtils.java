package com.zerone.catering.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/6/12.
 */

public class ProgressDialogUtils  {
    /**
     * ��ʾ������
     * @param context
     * @param msg  ��Ϣ
     */

    static ProgressDialog pg =null;
    public static void showProgress(Context context,String msg, Activity activity){
        if (pg==null){
            pg= new ProgressDialog(activity);
        }

        pg.setMessage(msg);
        pg.setTitle("���Ե�...");
        if (activity.isFinishing()){
            return;
        }
        pg.show();
        //�޸Ľ������Ŀ��
        WindowManager windowManager =activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = pg.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()/4); //���ÿ��
        pg.getWindow().setAttributes(lp);
    }

    /**
     * �رս�����
     */
    public static void closeProgress(){
        if (pg!=null){
            pg.dismiss();
        }
    }
}
