package com.zerone.catering.fragment.table;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zerone.catering.R;
import com.zerone.catering.activitys.table.AddyyActivity;
import com.zerone.catering.adapter.table.YyAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.table.YyData;
import com.zerone.catering.domain.table.YyList;
import com.zerone.catering.refreshandclose.shuaxin.TableFlush;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4.
 */

public class YyFragment extends Fragment{
    private static final int LOADINGDATA_YYORDER = 1003;    //预订订单列表
    private static final int LOADINGDATA_REVOKEORDER = 1004;  //取消预订
    private static final int ADDYY_REQUESTCODE = 999;  //
    private String tableid;
    private String tablename;
    private Activity activity;
    private YyList yylist;
    private View view;
    private GridView gridview_yy;
    private Button yuyue_add;
    private  List<String> idarr;
    private Button revoke_btn;
    private ACache mCache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tablectrl_yyfragment, container, false);

        Bundle bundle = getArguments();//从activity传过来的Bundle
        activity = getActivity();
        yylist = new YyList();
        idarr = new ArrayList<String>();
        gridview_yy = (GridView)view.findViewById(R.id.gridview_yy);
        yuyue_add = (Button)view.findViewById(R.id.yuyue_add);
        revoke_btn = (Button)view.findViewById(R.id.revoke_btn);
        mCache = ACache.get(activity);

        if(bundle!=null){
            //Toast.makeText(activity, "成功加载页面", Toast.LENGTH_SHORT).show();
            tableid = bundle.getString("tableid");
            tablename = bundle.getString("tablename");
            initNetYyList();
        }else{
            Toast.makeText(activity, "请先选择桌子", Toast.LENGTH_SHORT).show();
            return null;
        }
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        revoke_btn.setOnClickListener(revokeorder);
        yuyue_add.setOnClickListener(changeaddlist);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDYY_REQUESTCODE) {
            if(resultCode == 2){
                Toast.makeText(activity,data.getStringExtra("remsg"),Toast.LENGTH_SHORT).show();
            }else if(resultCode==1){
                Toast.makeText(activity,data.getStringExtra("remsg"),Toast.LENGTH_SHORT).show();
                initNetYyList();
            }
        }else{
            Toast.makeText(activity,"异常操作",Toast.LENGTH_SHORT).show();
        }
    }
    View.OnClickListener changeaddlist = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, AddyyActivity.class);
            intent.putExtra("tabledateid","-100");
            intent.putExtra("tableid",tableid);
            intent.putExtra("session", Utils.getSeesion(getActivity()));
            startActivityForResult(intent,ADDYY_REQUESTCODE);
        }
    };
    View.OnClickListener revokeorder = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(idarr.size()>0){
                String tableids = "";
                for(int i=0;i<idarr.size();i++){
                    if(i==idarr.size()-1){
                        tableids+= idarr.get(i);
                    }else {
                        tableids+= idarr.get(i) + ",";
                    }
                }
//                Log.i("调试tableids",tableids);
                initNetRevokeorder(tableids);
            }else{
                Toast.makeText(activity,"请先选择要撤销的选项",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void initNetYyList() {
        Map<String, String> map = MapUtilsSetParam.getMap(getActivity());
        map.put("opp", "gettableyyorder");
        map.put("tableid", tableid);
        NetUtils.netWorkByMethodPost(activity, map, IpConfig.URL, handlerMsg, LOADINGDATA_YYORDER);
    }
    private void initNetRevokeorder(String tableids) {
        Map<String, String> map =MapUtilsSetParam.getMap(getActivity());
        map.put("opp", "revokeyyorder");
        map.put("tableids", tableids);
        NetUtils.netWorkByMethodPost(activity, map, IpConfig.URL, handlerMsg, LOADINGDATA_REVOKEORDER);
    }
    //格式化json数据到yylist
    private void initYylistData(String jsonyyorder){
        yylist.clearSize();
        yylist.clearList();
        try {
            JSONObject jsonObj = new JSONObject(jsonyyorder);
            String status = jsonObj.getString("status");
            if("0".equals(status)){
                String data = jsonObj.getString("data");
                Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
            }else{
                JSONArray dataArray = jsonObj.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = (JSONObject) dataArray.get(i);
                    YyData yydata = new YyData();
                    yydata.setId(dataObject.getString("id"));
                    yydata.setDatetime_st(dataObject.getString("datetime_st"));
                    yydata.setDatetime_nd(dataObject.getString("datetime_nd"));
                    yydata.setDatetime_st_format(dataObject.getString("datetime_st_format"));
                    yydata.setDatetime_nd_format(dataObject.getString("datetime_nd_format"));
 //报错了 1110 刘兴文改
                   yydata.setOrdersn(dataObject.getString("ordersn"));
                    yylist.add(yydata);
                    yylist.addSize();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initYylistDataToPage();
    }

    //判断撤销操作的接口返回值并做对应的操作
    private void initRevokeorderData(String jsonrevokeorder){
        try {
            JSONObject jsonObj = new JSONObject(jsonrevokeorder);
            String status = jsonObj.getString("status");
            String data = jsonObj.getString("data");
            if("0".equals(status)){
                Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
            }else{
                initNetYyList();//撤销成功重新加载列表
                Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
                //发送广播刷新页面
                 EventBus.getDefault().post(new TableFlush(1,"刷新桌子的数据"));
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    //格式化数据到页面
    private void initYylistDataToPage(){
        //格式化数据
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<yylist.getSize();i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",yylist.getId(i));
            map.put("tablename",tablename);
            map.put("tableid",yylist.getTableid(i));
            map.put("datetime_st",yylist.getDatetime_st(i));
            map.put("datetime_nd",yylist.getDatetime_nd(i));
            map.put("datetime_st_format",yylist.getDatetime_st_format(i));
            map.put("datetime_nd_format",yylist.getDatetime_nd_format(i));
            map.put("ordernumber",yylist.getOrderSn(i));
            data_list.add(map);
        }
        YyAdapter adapter = new YyAdapter(activity,data_list,handlerMsg);
//        Log.d("调试",data_list.toString());
        gridview_yy.setAdapter(adapter);
        gridview_yy.setOnItemClickListener(selectedyyorder);
    }
    GridView.OnItemClickListener selectedyyorder = new GridView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //YyAdapter adapter = (YyAdapter) parent.getAdapter();
            LinearLayout llview = (LinearLayout)view;
            CheckBox ckb = (CheckBox) llview.findViewById(R.id.item_checked_btn);
            if(ckb.isChecked()) {
                ckb.setChecked(false);
                if(hasObj(idarr,yylist.getId(position))){
                   idarr = removeObj(idarr,yylist.getId(position));
                }
            }else{
                if(!hasObj(idarr,yylist.getId(position))){
                    idarr.add(yylist.getId(position));
                }
                ckb.setChecked(true);
            }
//            Log.i("调试", idarr.toString());
        }
    };
    /**
     * 移除list<string>中某个元素
     *
     */
    public List<String> removeObj(List<String> list,String target){
        Iterator it = list.iterator();
        while (it.hasNext())
        {
            String str = (String)it.next();
            if (str.equals(target))  //needDelete返回boolean，决定是否要删除
            {
                it.remove();
            }
        }
        return list;
    }
    /**
     * 查找list<string>某个元素是否存在
     *
     */
    public boolean hasObj(List<String> list,String target){
        Iterator it = list.iterator();
        boolean flag = false;
        while (it.hasNext())
        {
            String str = (String)it.next();
            if (str.equals(target))  //needDelete返回boolean，决定是否要删除
            {
                flag = true;
            }
        }
        return flag;
    }
    Handler handlerMsg = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADINGDATA_YYORDER:
                    String jsonyyorder = (String) msg.obj;
//                    Log.d("jsonyyorder",jsonyyorder);
                    initYylistData(jsonyyorder);
                    break;
                case LOADINGDATA_REVOKEORDER:
                    String jsonrevokeorder = (String) msg.obj;
//                    Log.d("jsonrevokeorder",jsonrevokeorder);
                    initRevokeorderData(jsonrevokeorder);
                    break;
                case 1:
//                    Log.d("URL", (String) msg.obj);
                    break;
                case 2:
                    String mes= (String) msg.obj;
                    Intent intent = new Intent(activity, AddyyActivity.class);
                    intent.putExtra("tableid",tableid);
                    intent.putExtra("session",Utils.getSeesion(getActivity()));
                    intent.putExtra("tabledateid",mes);
                    startActivityForResult(intent,ADDYY_REQUESTCODE);
                break;
            }
        }
    };



}
