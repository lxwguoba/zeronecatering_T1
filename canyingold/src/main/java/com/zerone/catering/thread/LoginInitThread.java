package com.zerone.catering.thread;

import android.app.Activity;

import com.zerone.catering.handler.LoginHandler;
import com.zerone.catering.utils.Base64Util;
import com.zerone.catering.utils.SharedPreferencesUtils;


public class LoginInitThread implements Runnable  {
    private Activity activity;
    private LoginHandler handler;

    public LoginInitThread(Activity activity, LoginHandler handler) {
        this.activity = activity;
        this.handler = handler;
    }


    @Override
    public void run() {
        try {
            String nameAndPwd= SharedPreferencesUtils.getNameAndPassword(activity);
            if (nameAndPwd==null){
                return;
            }
            String[] arrNameAndPwd= nameAndPwd.split("==");
            handler.showUserName(arrNameAndPwd[0]);
            String userPwd = new String(Base64Util.decrypt(arrNameAndPwd[1]));
            handler.showUserPwd(userPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
