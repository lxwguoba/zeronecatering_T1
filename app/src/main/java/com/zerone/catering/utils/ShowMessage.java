package com.zerone.catering.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/6/13.
 */

public class ShowMessage {

    /**
     * չʾ��Ϣ
     * @param activity
     * @param mess
     */
      public static void showMessage(Activity activity, String mess){
          Toast.makeText(activity,mess,Toast.LENGTH_SHORT).show();
      }
}
