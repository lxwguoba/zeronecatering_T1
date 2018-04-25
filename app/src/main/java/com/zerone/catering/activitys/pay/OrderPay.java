package com.zerone.catering.activitys.pay;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zerone.catering.R;
import com.zerone.catering.base.BaseActvity;

/**
 * Created by on 2018/3/24 0024 16 29.
 * Author  LiuXingWen
 */

public class OrderPay extends BaseActvity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpay);
        initView();
        initListenner();
    }


    private void initView() {
        editText = (EditText) findViewById(R.id.pay_qrinfo);
    }


    private void initListenner() {

//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                Toast.makeText(OrderPay.this,s,Toast.LENGTH_SHORT).show();
//            }
//        });

    }

}
