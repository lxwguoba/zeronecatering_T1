package com.zerone.catering.application;
import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by on 2017/11/13 0013 11 24.
 * Author  LiuXingWen
 */

public class MyApplication extends Application {

    /**
     * 请求栈
     */
    public static RequestQueue queues;
    //用于存放所有启动的Activity的集合
    private List<Activity> activityList;
    @Override
    public void onCreate() {
        super.onCreate();
        activityList=new ArrayList<Activity>();
        queues = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getQueues() {
        return queues;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // 程序终止的时候执行
    }

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!activityList.contains(activity)) {
             activityList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity(Activity activity) {
        //判断当前集合中存在该Activity
        if (activityList.contains(activity)) {
            activityList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }


    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}

