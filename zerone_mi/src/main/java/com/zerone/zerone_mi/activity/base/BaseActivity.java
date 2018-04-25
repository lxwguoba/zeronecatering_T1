package com.zerone.zerone_mi.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import com.zerone.zerone_mi.application.MyApplication;

/**
 * Created by on 2017/12/28 0028 10 52.
 * Author  LiuXingWen
 * 让所有的activity继承自这个baseActivity
 */

public class BaseActivity extends Activity {
    private MyApplication application;
    private  BaseActivity oContext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (application == null) {
            // 得到Application对象
            application = (MyApplication) getApplication();
        }
        // 把当前的上下文对象赋值给BaseActivity
        oContext = this;
        // 调用添加方法
        addActivity();
    }
    /**
     *  添加Activity方法
     */
    public void addActivity() {
        //调用myApplication的添加Activity方法
        application.addActivity(oContext);
    }

    /**
     * 销毁当个Activity方法
     */
    public void removeActivity() {
        application.removeActivity(oContext);// 调用myApplication的销毁单个Activity方法
    }

    /**
     * 销毁所有Activity方法
     */
    public void removeALLActivity() {
        // 调用myApplication的销毁所有Activity方法
        application.removeALLActivity();

    }
    /**
     * 把Toast定义成一个方法  可以重复使用，使用时只需要传入需要提示的内容即可
     * @param text
     */
    public void showToast(String text) {
        Toast.makeText(oContext, text, Toast.LENGTH_SHORT).show();
    }
}
