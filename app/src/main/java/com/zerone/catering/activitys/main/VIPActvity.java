package com.zerone.catering.activitys.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.zerone.catering.R;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.adapter.VIPAdapter;
import com.zerone.catering.domain.VIPBean;
import com.zerone.catering.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by on 2017/10/19 0019 14 17.
 * Author  LiuXingWen
 */

public class VIPActvity extends BaseActvity{


     //展示菜品价格的信息表；
    private ListView priceList;
    private List<VIPBean.DataBean> list;
    private Intent intent;
    private VIPAdapter vipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        intent = this.getIntent();
        initData();
        initView();
    }

    /**
     *  数据的初始化
     */
    private void initData() {
        list = new ArrayList<>();
        String response= intent.getStringExtra("response");
       String  oldmoney= Utils.getACache(this).getAsString("vipmoney");
        VIPBean.DataBean fistdb=new VIPBean.DataBean();
        fistdb.setLevelid("0");
        fistdb.setLevelname("原价");
        fistdb.setLevelprice(oldmoney);
        list.add(0,fistdb);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("status");
             if (status==1){
                 JSONArray data = jsonObject.getJSONArray("data");
                 for (int i=0;i<data.length();i++){
                      VIPBean.DataBean dbinfo=new VIPBean.DataBean();
                      JSONObject vipobject = data.getJSONObject(i);
                      dbinfo.setWeid(vipobject.getString("weid"));
                      dbinfo.setGoodsid(vipobject.getString("goodsid"));
                      dbinfo.setLevelcommission(vipobject.getString("levelcommission"));
                      dbinfo.setLevelid(vipobject.getString("levelid"));
                      dbinfo.setLevelname(vipobject.getString("levelname"));
                      dbinfo.setLevelprice(vipobject.getString("levelprice"));
                      list.add(dbinfo);
                 }
             }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化view的内容
     */
    private void initView() {
        Log.i("TAG", "initView: "+list.toString());
        vipAdapter = new VIPAdapter(VIPActvity.this,list);
        priceList = (ListView) findViewById(R.id.pricelsitview);
        priceList.setAdapter(vipAdapter);

         findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 VIPActvity.this.finish();
             }
         });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.getACache(this).remove("vipmoney");
    }
}
