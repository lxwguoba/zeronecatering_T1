package com.zerone.catering.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zerone.catering.R;
import com.zerone.catering.adapter.PopList03Adapter;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.Worker;
import com.zerone.catering.domain.GeneralBean;
import com.zerone.catering.refreshandclose.shuaxin.ShuaXinShuJu;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.ListViewUtils;
import com.zerone.catering.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class Activity_Dialog extends BaseActvity implements View.OnClickListener {

    private PopupWindow popupListWorker;
    private View popConfirmWorker;
    private ListView listWorker;
    private Button confirm_btn1;
    private Button confirm_btn2;
    private TextView checkwork;
    private ImageView close_dialog;
    private ACache mCache;
    private String session;
    private Branch branch;
    //员工的集合类
    private List<Worker> workerList;
    //店员的获取
    private static final int LOADINGDATA_WORKER = 224;
    //派送外卖
    private static final int LOADINGDATA_CONFIRMORDERSEND = 225;
    private PopList03Adapter workerAdapter;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_orderinfo_complete_confirm_dialog);
        mCache = ACache.get(this);
        intent = getIntent();
        session = mCache.getAsString("session");
        branch = (Branch) mCache.getAsObject("branch");
        initView();
        initLoadingData();
        initData();
        event();
    }

    /**
     * 点击事件的处理
     */
    private void event() {
        listWorker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = new Message();
                 message.what=1;
                 message.obj=position;
                handler.sendMessage(message);
            }
        });
    }

    private void initData() {
        //获取派送人员
        workerList = new ArrayList<>();
        workerAdapter = new PopList03Adapter(this, workerList);
        listWorker.setAdapter(workerAdapter);
        ListViewUtils.setListViewHeightBasedOnChildren(listWorker);
    }

    private void initLoadingData() {
        //获取店员的网络请求
        String workerjson = mCache.getAsString("worker");
        if (workerjson != null && workerjson.length() > 0) {
            Message message = new Message();
            message.obj = workerjson;
            message.what = LOADINGDATA_WORKER;
            handler.sendMessage(message);
        } else {
            Map<String, String> mapWorker = new HashMap<String, String>();
            mapWorker.put("act", "module");
            mapWorker.put("name", "bj_qmxk");
            mapWorker.put("do", "app_api");
            mapWorker.put("opp", "getworker");
            mapWorker.put("session", session);
            mapWorker.put("branchid", branch.getId());
            NetUtils.netWorkByMethodPost(this, mapWorker, IpConfig.URL, handler, LOADINGDATA_WORKER);

        }
    }

    private void initView() {
        popConfirmWorker = LayoutInflater.from(this).inflate(R.layout.activity_xianxia_edt_03, null);
        listWorker = (ListView) popConfirmWorker.findViewById(R.id.xianxia_edt_list03);
        //确认发货
        confirm_btn1 = (Button) findViewById(R.id.complete_confirm);
        confirm_btn1.setOnClickListener(this);
        //关闭对话框
        confirm_btn2 = (Button) findViewById(R.id.complete_close);
        confirm_btn2.setOnClickListener(this);
        //选择外卖派送员
        checkwork = (TextView)findViewById(R.id.checkWorker);
        checkwork.setOnClickListener(this);
        //关闭对话框
        close_dialog = (ImageView) findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPop03() {
        popupListWorker = new PopupWindow(popConfirmWorker,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                getWindow().getWindowManager().getDefaultDisplay().getHeight() / 3);
        popupListWorker.setFocusable(true);
        popupListWorker.setBackgroundDrawable(new BitmapDrawable());
        popupListWorker.setOutsideTouchable(true);
        popupListWorker.setTouchable(true);
        popupListWorker.showAsDropDown(checkwork);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //确认发货
            case R.id.complete_confirm:
               // workerid=3 //外卖    员工ID
               // ziticode=’’ //可选参数，到店自提密码
                Map<String, String> mapWorker = new HashMap<String, String>();
                mapWorker.put("act", "module");
                mapWorker.put("name", "bj_qmxk");
                mapWorker.put("do", "app_api");
                mapWorker.put("opp", "confirmsend");
                mapWorker.put("session", session);
                mapWorker.put("branchid", branch.getId());
                mapWorker.put("orderid",intent.getStringExtra("orderId"));
                 if (workerId==null){
                     Toast.makeText(Activity_Dialog.this,"请选择派送员工",Toast.LENGTH_SHORT).show();
                     return;
                 }
                mapWorker.put("workerid",  workerId);
                NetUtils.netWorkByMethodPost(this, mapWorker, IpConfig.URL, handler, LOADINGDATA_CONFIRMORDERSEND);
                break;
            //关闭对话框
            case R.id.complete_close:
                 finish();
                break;
            //选择外卖派送员
            case R.id.checkWorker:
                    showPop03();
                break;
            //关闭对话框
            case R.id.close_dialog:
                finish();
                break;
        }
    }

    private String workerId=null;
    Handler  handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int position = (int) msg.obj;
                    checkwork.setText(workerList.get(position).getName());
                    workerId = workerList.get(position).getWorkerid();
                    popupListWorker.dismiss();
                break;
                case LOADINGDATA_WORKER:
                    String workerJson = (String) msg.obj;
                    mCache.remove("workerjson");
                    mCache.put("workerjson", workerJson);
                    List<Worker> wkData = JSON.parseArray(workerJson, Worker.class);
                    for (int i = 0; i < wkData.size(); i++) {
                        if ("1".equals(wkData.get(i).getWorkerid())) {
                            return;
                        } else {
                            workerList.add(wkData.get(i));
                        }
                    }
                    workerAdapter.notifyDataSetChanged();
                    ListViewUtils.setListViewHeightBasedOnChildren(listWorker);
                break;
                case LOADINGDATA_CONFIRMORDERSEND:
                    String confirmpayJson = (String) msg.obj;
                    GeneralBean generalBean = JSON.parseObject(confirmpayJson, GeneralBean.class);
                    //1为成功  0为失败
                    if (generalBean.getStatus() == 1) {
                        Toast.makeText(Activity_Dialog.this, "外卖已发送", Toast.LENGTH_SHORT).show();
                        Activity_Dialog.this.finish();
                        EventBus.getDefault().post(new ShuaXinShuJu(10,"发货"));
                    } else if (generalBean.getStatus() == 0) {
                        Toast.makeText(Activity_Dialog.this, generalBean.getData(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
