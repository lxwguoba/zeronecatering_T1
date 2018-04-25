package com.zerone.catering.handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import com.zerone.catering.R;

/**
 * Created by Administrator on 2017/6/13.
 */

public class LoginHandler  extends Handler {

    private Activity activity;

    public static final int TYPE_USER_NAME = 1;
    public static final int TYPE_USER_PWD = 2;
    public static final int TYPE_REM_USER = 3;

    public static final int REM_OK = 1;

    public LoginHandler(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case TYPE_USER_NAME:
                EditText userComp = (EditText) activity.findViewById(R.id.username);
                userComp.setText((String)msg.obj);
                userComp.setSelection(userComp.getText().toString().length());
                break;
            case TYPE_USER_PWD:
                String pwd = (String)msg.obj;
                EditText pwdComp = (EditText) activity.findViewById(R.id.password);
                pwdComp.setText(pwd);
                pwdComp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            default:
                break;
        }
        super.handleMessage(msg);
    }

    /**
     * ��ס����
     */
    public void remember(){
        Message msg = new Message();
        msg.what = LoginHandler.TYPE_REM_USER;
        msg.arg1 = LoginHandler.REM_OK;
        sendMessage(msg);
    }

    /**
     * ��ʾ�˺�
     */
    public void showUserName(String userName){
        Message msg = new Message();
        msg.what = LoginHandler.TYPE_USER_NAME;
        msg.obj = userName;
        sendMessage(msg);
    }

    /**
     * ��ʾ����
     */
    public void showUserPwd(String password){
        Message msg = new Message();
        msg.what = LoginHandler.TYPE_USER_PWD;
        msg.obj = password;
        sendMessage(msg);
    }

}

