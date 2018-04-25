package com.zerone.catering.domain;


import android.view.GestureDetector;
import android.view.MotionEvent;


import com.zerone.catering.refreshandclose.shuaxin.OrderListFlush;
import com.zerone.catering.refreshandclose.shuaxin.OrderListFlushLeft;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/9/16 0016.
 */

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

        private int index;
        private String name;

        public MyGestureListener(int index, String name) {
                this.index = index;
                this.name = name;
        }

        @Override//此方法必须重写且返回真，否则onFling不起效
        public boolean onDown(MotionEvent e) {
                return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if((e1.getX()- e2.getX()>80)&&Math.abs(velocityX)>100){
                        //左边上一页
                        //发送广播
                        EventBus.getDefault().post(new OrderListFlushLeft(index,name));
                        return true;
                }else if((e2.getX()- e1.getX()>80)&&Math.abs(velocityX)>100){
                        //右边 下一页
                        EventBus.getDefault().post(new OrderListFlush(index,name));
                        return true;
                }
                return false;
        }
}
