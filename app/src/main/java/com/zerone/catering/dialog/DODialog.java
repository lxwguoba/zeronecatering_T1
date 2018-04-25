package com.zerone.catering.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class DODialog {

    /**
     * �����Ի���
     * @param activity  ��ǰactivity����
     * @param alertview  Ҫչʾ����ͼ
     * @return
     */
    public  static AlertDialog getDialog(Activity activity, View alertview){
        //�Ի��������
        AlertDialog  alertDialog = new AlertDialog.Builder(activity)
                .setView(alertview)
                .create();
//        //����Ӧview�Ŀ��
//        WindowManager wm = activity.getWindowManager();
//        Display display = wm.getDefaultDisplay();
//        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
//        params.width=WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        alertDialog.getWindow().setAttributes(params);
//        //����ⲿ����ʧ
//        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    /**
     * չʾ�Ի���
     * @param dialog  �Ի����ʵ�����
     * @param activity  ��ǰactivity
     */
    public  static void  showDialog(AlertDialog  dialog,Activity activity){
        dialog.show();
        //����Ӧview�Ŀ��-------------------------------
        WindowManager wm =activity.getWindowManager();
        Display display = wm.getDefaultDisplay();
        WindowManager.LayoutParams params =dialog.getWindow().getAttributes();
        params.width=400;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        //����ⲿ����ʧ
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * �رնԻ���
     * @param dialog  �رյĶ���
     */
    public static void colseDialog(AlertDialog dialog) {
        dialog.dismiss();
    }
}
