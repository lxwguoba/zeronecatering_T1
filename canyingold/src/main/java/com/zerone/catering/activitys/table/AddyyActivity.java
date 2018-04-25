package com.zerone.catering.activitys.table;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zerone.catering.R;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.adapter.ListViewOrderItemsYuYueAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.YuYueOrderListBean;
import com.zerone.catering.refreshandclose.shuaxin.TableFlush;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4.
 */

public class AddyyActivity extends BaseActvity implements View.OnClickListener {
    private static final int LOADINGDATA_ADDYYORDER = 1005;  //增加预订
    private static final int LOADINGDATA_GETYUYUEDATA = 1006;  //获取网络预约订单
    private static final int LOADINGDATA_MESSAGE = 1007;  //listview的点击事件
    private DatePicker datepicker;
    private DatePicker datepicker2;
    private TimePicker timepicker;
    private TimePicker timepicker2;
    private Button yy_submit_btn;
    private Button yy_close_btn;
    private String tableid;
    private ACache mCache;
    private Branch branch;
    private TextView select_ordersn;
    private View orderNumberListView;
    private ListView oNListView;
    private EditText inputPeopleNum;
    private List<YuYueOrderListBean.DataBean> list;
    private ListViewOrderItemsYuYueAdapter yyAdapter;
    private PopupWindow popupWindowGuanli;
    private Intent intent;
    private String tabledateid;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        mCache = ACache.get(this);
        branch = (Branch) mCache.getAsObject("branch");
        tableid = intent.getStringExtra("tableid");
        tabledateid = intent.getStringExtra("tabledateid");
        setContentView(R.layout.activity_addyy);
        list=new ArrayList<>();
        LoadingGetYuYueData();
        initView();
        actionListview();
    }
    //listview的items的点击事件
    private void actionListview() {
        oNListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = new Message();
                 message.what=LOADINGDATA_MESSAGE;
                 message.obj=list.get(position).getOrdersn();
                 mCache.put("orderid",list.get(position).getId());
                 handlerMsg.sendMessage(message);

            }
        });
    }

     //加载预约订单数据
    private void LoadingGetYuYueData() {
//        http://ctest.greengod.cn/site.php?act=module&name=bj_qmxk&do=app_api&branchid=3&
        Map<String, String> map = MapUtilsSetParam.getMap(AddyyActivity.this);
        map.put("opp", "getyuyueorderlist");
        map.put("tableid", tableid);
        map.put("branchid", branch.getId());
        NetUtils.netWorkByMethodPost(AddyyActivity.this, map, IpConfig.URL, handlerMsg, LOADINGDATA_GETYUYUEDATA);

    }

    public void initView(){
        datepicker = (DatePicker)findViewById(R.id.datepicker);
        datepicker2 = (DatePicker)findViewById(R.id.datepicker);
        timepicker = (TimePicker)findViewById(R.id.timepicker);
        timepicker.setIs24HourView(true);
        timepicker2 = (TimePicker)findViewById(R.id.timepicker2);
        timepicker2.setIs24HourView(true);
        yy_close_btn = (Button)findViewById(R.id.yy_close_btn);
        yy_submit_btn = (Button)findViewById(R.id.yy_submit_btn);
        if ("-100".equals(tabledateid)){
            yy_submit_btn.setText("新增");
        }else {
            yy_submit_btn.setText("修改");
        }

        yy_close_btn.setOnClickListener(closedialog);
        yy_submit_btn.setOnClickListener(submityd);
        //订单编号的view
        orderNumberListView = LayoutInflater.from(this).inflate(R.layout.activity_ordernumberlist_yuyue,null);
        oNListView = (ListView) orderNumberListView.findViewById(R.id.listview_order);
        yyAdapter = new ListViewOrderItemsYuYueAdapter(this,list);
        oNListView.setAdapter(yyAdapter);
        select_ordersn = (TextView) findViewById(R.id.select_ordersn);
        select_ordersn.setOnClickListener(this);
        inputPeopleNum = (EditText) findViewById(R.id.select_peopleNum);


    }
    View.OnClickListener submityd = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //2017-1113 搞定
            Map<String, String> map = MapUtilsSetParam.getMap(AddyyActivity.this);
            String y_st = String.valueOf(datepicker.getYear());
            String M_st = String.valueOf(datepicker.getMonth()+1);
            String d_st = String.valueOf(datepicker.getDayOfMonth());
            String h_st = String.valueOf(timepicker.getCurrentHour());
            String m_st =  String.valueOf(timepicker.getCurrentMinute());
            String s_st = "00";
            String datetime_st = y_st+"-"+formatd(M_st)+"-"+formatd(d_st)+" "+formatd(h_st)+":"+formatd(m_st)+":"+s_st;

            String y_nd = String.valueOf(datepicker2.getYear());
            String M_nd = String.valueOf(datepicker2.getMonth()+1);
            String d_nd = String.valueOf(datepicker2.getDayOfMonth());
            String h_nd = String.valueOf(timepicker2.getCurrentHour());
            String m_nd =  String.valueOf(timepicker2.getCurrentMinute());
            String s_nd = "00";
            String datetime_nd = y_nd+"-"+formatd(M_nd)+"-"+formatd(d_nd)+" "+formatd(h_nd)+":"+formatd(m_nd)+":"+s_nd;
            String ordersn= mCache.getAsString("orderid");
            String number = inputPeopleNum.getText().toString().trim();
//            增加了3个字段datenum，orderid，tabledateid
            map.put("opp", "addyyorder");
            map.put("tableid", tableid);
            map.put("datetime_st",datetime_st);
            map.put("datetime_nd",datetime_nd);
            if ("-100".equals(tabledateid)){
            }else {
                map.put("tabledateid",tabledateid);
            }
            if (ordersn!=null){
                map.put("orderid",ordersn);
            }
            if (!("".equals(number))){
                if (number.startsWith("0")){
                    Toast.makeText(AddyyActivity.this,"人数不能已0开头",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    map.put("datenum", number);
                    Log.i("URL","number="+number);
                }
            }

            NetUtils.netWorkByMethodPost(AddyyActivity.this, map, IpConfig.URL, handlerMsg, LOADINGDATA_ADDYYORDER);
        }

    };
    View.OnClickListener closedialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(AddyyActivity.this, AddyyActivity.class);
            intent.putExtra("remsg","取消预订操作");
            setResult(2,intent);
            finish();
        }
    };

    public String formatd(String str){
        if(str.length()==1){
            str = "0"+str;
        }
        return str;
    }

    public void initAddyyData(String jsonyyorder){
        try {
            JSONObject jsonObj = new JSONObject(jsonyyorder);
            String status = jsonObj.getString("status");
            if("0".equals(status)){
                String data = jsonObj.getString("data");
                Toast.makeText(AddyyActivity.this, data, Toast.LENGTH_SHORT).show();
            }else{
                String data = jsonObj.getString("data");
                Log.i("URL","新增的信息Response："+data);
//                Intent intent = new Intent(AddyyActivity.this, AddyyActivity.class);
                intent.putExtra("remsg",data);
                setResult(1,intent);
                finish();
                //发送广播刷新桌子页面
                EventBus.getDefault().post(new TableFlush(1,"刷新桌子的数据"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mCache.remove("orderid");
            select_ordersn.setText("请选择");
        }
    }

    Handler handlerMsg = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADINGDATA_ADDYYORDER:
                    String jsonyyorder = (String) msg.obj;
                    Log.d("jsonyyorder",jsonyyorder);
                     initAddyyData(jsonyyorder);
                    break;
                case 1:
                    Log.d("URL", (String) msg.obj);
                    break;
                case  LOADINGDATA_GETYUYUEDATA:
                    String jsongetyuyue= (String) msg.obj;
                    Log.i("URL","jsongetyuyue"+jsongetyuyue);
                    try {
                        JSONObject jsonObject = new JSONObject(jsongetyuyue);
                        int status = jsonObject.getInt("status");
                        if (status==1){
                        YuYueOrderListBean yyb= JSON.parseObject(jsongetyuyue, YuYueOrderListBean.class);
                         List<YuYueOrderListBean.DataBean> data = yyb.getData();
                            for (int i=0;i<data.size();i++){
                                list.add(data.get(i));
                            }
                         yyAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case LOADINGDATA_MESSAGE:
                     String ordersn= (String) msg.obj;
                    select_ordersn.setText(ordersn);
                    popupWindowGuanli.dismiss();
                    break;
            }
        }
    };


    private void showPopOrderNumber() {
        popupWindowGuanli = new PopupWindow(orderNumberListView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindowGuanli.setFocusable(true);
        popupWindowGuanli.setBackgroundDrawable(new BitmapDrawable());
        popupWindowGuanli.setOutsideTouchable(true);
        popupWindowGuanli.setTouchable(true);
        popupWindowGuanli.showAsDropDown(select_ordersn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_ordersn:
                showPopOrderNumber();
            break;
        }
    }
}
