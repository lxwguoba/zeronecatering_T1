package com.zerone.catering.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zerone.catering.R;
import com.zerone.catering.activitys.login.LoginOrderActivity;

/**
 * Created by on 2017/11/15 0015 10 28.
 * Author  LiuXingWen
 */

public class ThreadGetData extends AppCompatActivity {

    private TextView starttime;
    private TextView time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ttttttttttttttttttttttttttttttttttttt);
        initView();
    }


    //初始化数据
    private void initView() {
        final Thread thread = new Thread(ruanable);
        starttime = (TextView) findViewById(R.id.start_time);
        time = (TextView) findViewById(R.id.time);
        final boolean[] lean = {false};
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lean[0]) {
                    lean[0] = false;
                } else {
                    lean[0] = true;
                    thread.start();
                }

            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThreadGetData.this, LoginOrderActivity.class);
                startActivity(intent);
                ThreadGetData.this.finish();
            }
        });
    }

    int i = 0;
    boolean fla = true;
    Runnable ruanable = new Runnable() {
        @Override
        public void run() {
            while (fla) {
                try {
                    Thread.sleep(1 * 500);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = i + "";
                    Log.i("URL", "run: "+i);
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                }
            }
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    i++;
                    String string = (String) msg.obj;
                    if ("stop".equals(string)) {
                        if (fla) {
                            fla = false;
                        }
                    } else {
                        time.setText(string);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Message message = new Message();
        message.what = 0;
        message.obj = "stop";
        handler.sendMessage(message);
    }
}
