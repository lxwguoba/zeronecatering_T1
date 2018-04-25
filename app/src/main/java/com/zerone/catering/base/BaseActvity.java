package com.zerone.catering.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.zerone.catering.application.MyApplication;

/**
 * Created by on 2017/12/21 0021 10 07.
 * Author  LiuXingWen
 */

public class BaseActvity extends Activity {
    public MyApplication baseApp;
    private BaseActvity oContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
//        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#0eb468"));
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (baseApp == null) {
            // 得到Application对象
            baseApp = (MyApplication) getApplication();
        }
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
    }

    // 添加Activity方法
    public void addActivity() {
        baseApp.addActivity(oContext);// 调用myApplication的添加Activity方法
    }
    //销毁当个Activity方法
    public void removeActivity() {
        baseApp.removeActivity(oContext);// 调用myApplication的销毁单个Activity方法
    }
    //销毁所有Activity方法
    public void removeALLActivity() {
        baseApp.removeALLActivity();// 调用myApplication的销毁所有Activity方法
    }

    /* 把Toast定义成一个方法  可以重复使用，使用时只需要传入需要提示的内容即可*/
    public void showToast(String text) {
        Toast.makeText(oContext, text, Toast.LENGTH_SHORT).show();
    }


}
