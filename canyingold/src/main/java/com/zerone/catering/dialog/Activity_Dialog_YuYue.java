package com.zerone.catering.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zerone.catering.R;
import com.zerone.catering.adapter.PopList03Adapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.GeneralBean;
import com.zerone.catering.refreshandclose.shuaxin.ShuaXinShuJu;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class Activity_Dialog_YuYue extends Activity implements View.OnClickListener {

    private PopupWindow popupListWorker;
    private View popConfirmWorker;
    private ListView listWorker;
    private Button confirm_btn1;
    private Button confirm_btn2;
    private EditText checkwork;
    private ImageView close_dialog;
    private ACache mCache;
    private String session;
    private Branch branch;
    private String workerId=null;
    //派送外卖
    private static final int LOADINGDATA_CONFIRMORDERSEND = 225;
    private PopList03Adapter workerAdapter;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_orderinfo_complete_confirm_dialog_yuding);
        mCache = ACache.get(this);
        intent = getIntent();
        session = mCache.getAsString("session");
        branch = (Branch) mCache.getAsObject("branch");
        initView();
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
        checkwork = (EditText)findViewById(R.id.checkWorker);
        //关闭对话框
        close_dialog = (ImageView) findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(this);
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //确认发货
            case R.id.complete_confirm:
                String ziTiCode= checkwork.getText().toString().trim();
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
                 if (ziTiCode==null||"".equals(ziTiCode)){
                     Toast.makeText(Activity_Dialog_YuYue.this,"请输入自提码",Toast.LENGTH_SHORT).show();
                     return;
                 }
                mapWorker.put("ziticode", ziTiCode);
                NetUtils.netWorkByMethodPost(this, mapWorker, IpConfig.URL, handler, LOADINGDATA_CONFIRMORDERSEND);
                break;
            //关闭对话框
            case R.id.complete_close:
                 finish();
                break;
            //关闭对话框
            case R.id.close_dialog:
                finish();
                break;
        }
    }


    Handler  handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOADINGDATA_CONFIRMORDERSEND:
                    String confirmpayJson = (String) msg.obj;
                    GeneralBean generalBean = JSON.parseObject(confirmpayJson, GeneralBean.class);
                    //1为成功  0为失败
                    if (generalBean.getStatus() == 1) {
                        Toast.makeText(Activity_Dialog_YuYue.this, "确认到位就餐", Toast.LENGTH_SHORT).show();
                        Activity_Dialog_YuYue.this.finish();
                        EventBus.getDefault().post(new ShuaXinShuJu(10,"预约就餐"));
                    } else if (generalBean.getStatus() == 0) {
                        Toast.makeText(Activity_Dialog_YuYue.this, generalBean.getData(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
