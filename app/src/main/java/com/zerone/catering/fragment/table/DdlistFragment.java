package com.zerone.catering.fragment.table;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.zerone.catering.R;
import com.zerone.catering.activitys.table.TableCtrlActivity;
import com.zerone.catering.adapter.table.TableOrderAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.table.TableOrderData;
import com.zerone.catering.domain.table.TableOrderList;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/5.
 */

public class DdlistFragment extends Fragment {

    private static final int LOADINGDATA_GDORDERLIST = 1008;//获取挂单列表
    private String tableid;
    private String tablename;
    private TableCtrlActivity activity;
    private ACache mCache;
    private TableOrderList tableOrderList;
    private GridView gridview_gddd;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablectrl_ddlistfragment, container, false);
        activity = (TableCtrlActivity) getActivity();
        Bundle bundle = getArguments();//从activity传过来的Bundle
        mCache = ACache.get(activity);
        if (bundle != null) {
            //Toast.makeText(activity, "成功加载页面", Toast.LENGTH_SHORT).show();
            tableid = bundle.getString("tableid");
            tablename = bundle.getString("tablename");
            initNetOrderListData();
        } else {
            Toast.makeText(activity, "请先选择桌子", Toast.LENGTH_SHORT).show();
            return null;
        }

        gridview_gddd = (GridView) view.findViewById(R.id.gridview_gddd);
        return view;
    }

    public void initNetOrderListData() {
        Map<String, String> map = MapUtilsSetParam.getMap(getActivity());
        map.put("opp", "getordergdlist");
        map.put("tableid", tableid);
        NetUtils.netWorkByMethodPost(activity, map, IpConfig.URL, handlerMsg, LOADINGDATA_GDORDERLIST);
    }

    public void initGdorderListData(String jsongdorderlist) {

        tableOrderList = new TableOrderList();
        try {
            JSONObject jsonObj = new JSONObject(jsongdorderlist);
            Log.i("URL", "initGdorderListData: " + jsongdorderlist);
            JSONArray orderlist = jsonObj.getJSONArray("data");

            for (int i = 0; i < orderlist.length(); i++) {
                JSONObject obj = (JSONObject) orderlist.get(i);
                String status = obj.getString("status");
                if ("0".equals(status)) {
                    String data = jsonObj.getString("data");
                    Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
                } else {
//                     JSONArray dataArray = obj.getJSONArray("orderinfo");
//                     for (int f= 0; f < dataArray.length(); f++) {
                    JSONObject dataObject = (JSONObject) obj.getJSONObject("orderinfo");
                    TableOrderData tableOrderData = new TableOrderData();
                    tableOrderData.setId(dataObject.getString("id"));
                    tableOrderData.setOrderid(obj.getString("orderid"));
                    tableOrderData.setTableid(obj.getString("tableid"));
                    tableOrderData.setOrdersn(dataObject.getString("ordersn"));
                    tableOrderData.setDatenum(obj.getString("datenum"));
                    tableOrderData.setDispatchchoice(obj.getString("dispatchchoice"));
                    tableOrderData.setStatus(obj.getString("status"));
                    tableOrderData.setDatetime_st(obj.getString("datetime_st"));
                    tableOrderData.setDatetime_nd(obj.getString("datetime_nd"));
                    tableOrderList.add(tableOrderData);
                    tableOrderList.addSize();
//                     }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initGdorderListDataToPage();
    }

    public void initGdorderListDataToPage() {
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < tableOrderList.getSize(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", tableOrderList.getId(i));
            map.put("orderid", tableOrderList.getOrderid(i));
            map.put("tableid", tableOrderList.getTableid(i));
            map.put("datenum", tableOrderList.getDatenum(i));
            map.put("ordersn", tableOrderList.getOrdersn(i));
            map.put("tablename", tablename);
            data_list.add(map);
        }
        TableOrderAdapter adapter = new TableOrderAdapter(activity, data_list);
        gridview_gddd.setAdapter(adapter);
        gridview_gddd.setOnItemClickListener(showorderinfo);
    }

    GridView.OnItemClickListener showorderinfo = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            activity.init_gd_fragment(tableOrderList.getOrderid((position)));

        }
    };

    Handler handlerMsg = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADINGDATA_GDORDERLIST:
                    String jsongdorderlist = (String) msg.obj;
//                    Log.d("jsongdorderlist",jsongdorderlist);
                    initGdorderListData(jsongdorderlist);
                    break;
                case 1:
//                    Log.d("URL", (String) msg.obj);
                    break;
            }
        }
    };
}
