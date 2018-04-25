package com.zerone.catering.fragment.table;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.zerone.catering.BuildConfig;
import com.zerone.catering.R;
import com.zerone.catering.activitys.order.OrderControl;
import com.zerone.catering.activitys.table.TableCtrlActivity;
import com.zerone.catering.adapter.table.TableGoodsAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.table.TableGoodsData;
import com.zerone.catering.domain.table.TableGoodsList;
import com.zerone.catering.refreshandclose.shuaxin.TableFlush;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4.
 */

public class DdFragment extends Fragment {
    private static final int LOADINGDATA_TABLEORDERGOOD = 1006;  //取消预订
    private static final int LOADINGDATA_SUBMITORDERTABLE = 1007;//提交订单
    private static final int LOADINGDATA_JIEZHANG = 1008;
    private String tableid;
    private String tablename;
    //订单id
    private String theorderid;
    private TableCtrlActivity activity;
    private View view;
    private String orderid;
    private String ordersn;
    private String orderprice;
    private TableGoodsList tableGoodsList;
    private TextView table_name;
    private TextView order_price;
    private ListView listview_orderinfo;
    private Button tijiao_btn;
    private ACache mCache;
    private String iskt;
    private RelativeLayout bottom_btn_box;
    private RelativeLayout bottom_btn_box2;
    private Button jiezhang_btn;
    private View dialogView;
    private AlertDialog alertDialog;
    private RadioGroup radioGroup;
    private Button paybtn;
    private Button cancle;
    private List<Map<String, Object>> data_list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (TableCtrlActivity)getActivity();
        Bundle bundle = getArguments();//从activity传过来的Bundle
        mCache = ACache.get(activity);
        if(bundle!=null){
            //Toast.makeText(activity, "成功加载页面", Toast.LENGTH_SHORT).show();
            tableid = bundle.getString("tableid");
            tablename = bundle.getString("tablename");
            theorderid = bundle.getString("theorderid");
            iskt = bundle.getString("iskt");
        }else{
            Toast.makeText(activity, "请先选择桌子", Toast.LENGTH_SHORT).show();
            return null;
        }
        initNetGoodsData();
        view = inflater.inflate(R.layout.tablectrl_ddinfofragment, container, false);

         //对话框
        dialogView = inflater.inflate(R.layout.activity_tablejiezhang, container, false);
        //对话框的结账view设置

        radioGroup = (RadioGroup) dialogView.findViewById(R.id.group);
        paybtn = (Button) dialogView.findViewById(R.id.submit_surepay);
        cancle = (Button) dialogView.findViewById(R.id.cancle);

        //对话框的view设置
        table_name = (TextView)view.findViewById(R.id.table_name);
        order_price = (TextView)view.findViewById(R.id.order_price);
        listview_orderinfo = (ListView)view.findViewById(R.id.listview_orderinfo);
        bottom_btn_box = (RelativeLayout)view.findViewById(R.id.bottom_btn_box);
        bottom_btn_box2= (RelativeLayout)view.findViewById(R.id.bottom_btn_box2);

        jiezhang_btn = (Button)view.findViewById(R.id.jiezhang_btn);
        tijiao_btn = (Button)view.findViewById(R.id.tijiao_btn);
        if("1".equals(iskt)){
            bottom_btn_box2.setVisibility(View.VISIBLE);
            bottom_btn_box.setVisibility(View.GONE);
        }else{
            bottom_btn_box2.setVisibility(View.GONE);
            bottom_btn_box.setVisibility(View.VISIBLE);
        }

        //对话框的设置
        alertDialog = new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .create();

         initRadioGroupAction();

        return view;
    }

    private void initRadioGroupAction() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.xianjin:
                        //4 原来
                         mCache.put("payStaus","4");
                        break;

                    case R.id.shuaka:
                        //5
                        mCache.put("payStaus","5");
                        break;


                    case R.id.zhifubao:
                        //6
                        mCache.put("payStaus","6");
                        break;

                    case R.id.weixin:
                        //7
                        mCache.put("payStaus","7");
                        break;
                }
            }
        });

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payStaus = mCache.getAsString("payStaus");
                Map<String, String> map = MapUtilsSetParam.getMap(getActivity());
                //结账用：
                map.put("opp", "jiezhang");
                map.put("tableid", tableid);
                map.put("orderid",theorderid);
                map.put("paytype",payStaus);
                NetUtils.netWorkByMethodPost(activity, map, IpConfig.URL, handlerMsg, LOADINGDATA_JIEZHANG);

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        tijiao_btn.setOnClickListener(submitorder);
        jiezhang_btn.setOnClickListener(jiezhang);
    }
    Button.OnClickListener submitorder = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            String number=Utils.getACache(getActivity()).getAsString("people");
             if (number==null){
                 number="0";
             }
            final EditText jcnum = new EditText(activity);

            jcnum.setText(number);
            jcnum.setInputType(InputType.TYPE_CLASS_NUMBER);
           new AlertDialog.Builder(activity).setTitle("请输入就餐人数，原就餐人数："+number).setIcon(android.R.drawable
                   .ic_dialog_info).setView(jcnum).setPositiveButton("确定", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   String num = jcnum.getText().toString();
                   initNetSubmitOrder(num);
               }
           }).setNegativeButton("取消", null).show();
        }
    };
    Button.OnClickListener jiezhang = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            // alertDialog
            alertDialog.show();
            WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
//            params.width = 450;
//            params.height = 330;
            alertDialog.getWindow().setAttributes(params);
        }
    };

    public void initNetSubmitOrder(String num){
        Map<String, String> map =  MapUtilsSetParam.getMap(getActivity());

        map.put("opp", "submitorderfordc");
        map.put("tableid", tableid);
        map.put("orderid",theorderid);
        map.put("datenum",num);
        Log.i("URL","tableid="+tableid);
        Log.i("URL","orderid="+theorderid);

//         String url=IpConfig.URL+"act=module&name=bj_qmxk&do=app_api&opp=submitorderfordc&tableid="+tableid
//                 +"&orderid="+theorderid+"&datenum="+num+"&session="+ Utils.getSeesion(getActivity());
//
//        Log.i("URL", "initNetSubmitOrder:===== "+url);

        NetUtils.netWorkByMethodPost(activity, map, IpConfig.URL, handlerMsg, LOADINGDATA_SUBMITORDERTABLE);
    }
    public void initNetGoodsData(){

        Map<String, String> map = MapUtilsSetParam.getMap(getActivity());
        map.put("opp", "gettableorderdetailnew");
        map.put("tableid", tableid);
        map.put("orderid",theorderid);
        NetUtils.netWorkByMethodPost(activity, map, IpConfig.URL, handlerMsg, LOADINGDATA_TABLEORDERGOOD);
    }

    public void initGoodsData(String jsontablegoods){
         if (BuildConfig.DEBUG) Log.d("DdFragment", jsontablegoods);

        tableGoodsList = new TableGoodsList();
        try {
            JSONObject jsonObj = new JSONObject(jsontablegoods);
            String status = jsonObj.getString("status");
            String data = jsonObj.getString("data");
            if("0".equals(status)){
                Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
            }else{
                JSONObject jsonObj2 = new JSONObject(data);
                orderid = jsonObj2.getString("id");
                ordersn = jsonObj2.getString("ordersn");
                orderprice = jsonObj2.getString("goodsprice");
                JSONArray dataArray = jsonObj2.getJSONArray("goods");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = (JSONObject) dataArray.get(i);
                    TableGoodsData tableGoodsData = new TableGoodsData();
                    tableGoodsData.setId(dataObject.getString("id"));
                   // Log.i("调试:",dataObject.getString("id"));
                    tableGoodsData.setPrice(dataObject.getString("price"));
                    //Log.i("调试:",dataObject.getString("price"));
                    tableGoodsData.setTotal(dataObject.getString("total"));
                   // Log.i("调试:",dataObject.getString("total"));
                    tableGoodsData.setTitle(dataObject.getString("title"));
                   // Log.i("调试:",dataObject.getString("title"));
//                   原来的
//                    tableGoodsData.setMarketprice(dataObject.getString("marketprice"));
                    //兴文添加的
                    tableGoodsData.setMarketprice(dataObject.getString("price"));
                    //Log.i("调试:",dataObject.getString("marketprice"));
                    tableGoodsList.add(tableGoodsData);
                    tableGoodsList.addSize();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initGoodsDataToPage();
    }
    public void initGoodsDataToPage(){
        table_name.setText("餐桌："+tablename);
        order_price.setText(orderprice);
        data_list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<tableGoodsList.getSize();i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",tableGoodsList.getId(i));
            map.put("price",tableGoodsList.getPrice(i));
            map.put("total",tableGoodsList.getTotal(i));
            map.put("title",tableGoodsList.getTitle(i));
            map.put("marketprice",tableGoodsList.getMarketprice(i));
            data_list.add(map);
        }
        TableGoodsAdapter adapter = new TableGoodsAdapter(activity, data_list);
        listview_orderinfo.setAdapter(adapter);
    }
    public void initOrderSubmitData(String jsonsubmitordertable){
        try {
            JSONObject jsonObj = new JSONObject(jsonsubmitordertable);
            String status = jsonObj.getString("status");
            String data = jsonObj.getString("data");
            if("0".equals(status)){
                Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity,OrderControl.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Handler handlerMsg = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADINGDATA_TABLEORDERGOOD:
                    String jsontablegoods = (String) msg.obj;
                    Log.d("jsontablegoods",jsontablegoods);
                    initGoodsData(jsontablegoods);
                    break;
                case LOADINGDATA_SUBMITORDERTABLE:
                    String jsonsubmitordertable = (String) msg.obj;
                    Log.d("jsonsubmitordertable",jsonsubmitordertable);
                    initOrderSubmitData(jsonsubmitordertable);
                    break;
                case 1:
                    Log.d("URL", (String) msg.obj);
                    break;

                case LOADINGDATA_JIEZHANG:
                    /**
                     * 兴文改的  2017-09-15
                     */
                  String jiezhangJson= (String) msg.obj;
                    Log.d("DdFragment", "json:" + jiezhangJson);
                    try {
                        JSONObject json = new JSONObject(jiezhangJson);
                        Log.d("DdFragment", "json:" + json);
                        String status= json.getString("status");
                        String data= (String) json.getString("data");
                         if ("1".equals(status)){

                             alertDialog.dismiss();
                             //发送广播 给table刷新和 ddlist刷新
                             EventBus.getDefault().post(new TableFlush(1,"刷新桌子的数据"));
                             ///清空lsit和总计000
                             order_price.setText("0.0");
                             //结账成功清空列表
                             data_list.clear();
                             table_name.setText("");
                             //1107有问题
                             Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
                         }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
