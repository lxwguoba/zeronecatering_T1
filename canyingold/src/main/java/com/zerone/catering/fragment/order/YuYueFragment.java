package com.zerone.catering.fragment.order;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zerone.catering.R;
import com.zerone.catering.activitys.table.TableCtrlActivity;
import com.zerone.catering.adapter.order.YuYueAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.YuYueOrderListBean;
import com.zerone.catering.domain.order.YuYueBean;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.Utils;
import com.zerone.catering.utils.UtilsTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by on 2017/11/20 0020 14 39.
 * Author  LiuXingWen
 */

public class YuYueFragment extends Fragment {
    private static final int GETYUYUEORDER = 0;
    private ListView listview;
    private List<YuYueBean> listY;
    private Branch branch;
    private YuYueAdapter yuYueAdapter;
    private LinearLayout data_layout;
    private RelativeLayout layout_tishi;
    private Thread threadead;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yuyue, container, false);
        branch = (Branch) Utils.getACache(getContext()).getAsObject("branch");

        //开qi一个子线程
        threadead = new Thread(ruanable);
        if (!threadead.isAlive()){
            threadead.start();
        }

        setData();
        initview(view);
        return view;
    }

    boolean la = true;
    Runnable ruanable = new Runnable() {
        @Override
        public void run() {
//            int i = 0;
            while (la) {
//                i++;
                try {
                    Thread.sleep(2000);
//                    Log.i("URL", "run: " + "子线程获取数据  预约订单" + i);
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 更新数据
     */
    private void update() {
        Map<String, String> map = MapUtilsSetParam.getMap(getContext());
        map.put("opp", "opdatalist");
        map.put("branchid", branch.getId());
        NetUtils.netWorkByMethodPost(getContext(), map, IpConfig.URL, handler, GETYUYUEORDER);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            la=false;
        }else {
            la=true;
            if (!threadead.isAlive()){
                new Thread(ruanable).start();
            }
        }
    }

    /**
     * 从网络中获取所有的预约订单列表
     */
    private void setData() {
        listY = new ArrayList<>();
        update();
    }

    /**
     * 初始化view的值
     *
     * @param view
     */
    private void initview(View view) {
        //这个是用来显示有预约订单时要打开的
        data_layout = view.findViewById(R.id.layout_data);
        data_layout.setVisibility(View.GONE);
        //这个是用来显示没有预约订单时要打开的
        layout_tishi = view.findViewById(R.id.layout_tishi);
        //用来展示预约列表
        listview = view.findViewById(R.id.yuyue_listview);
        yuYueAdapter = new YuYueAdapter(listY, getContext(), handler);
        listview.setAdapter(yuYueAdapter);


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETYUYUEORDER:
                    String yyJson = (String) msg.obj;
                    try {
                        JSONObject jsonobject = new JSONObject(yyJson);
                        int status = jsonobject.getInt("status");
                        if (status == 1) {
                            listY.clear();
                            data_layout.setVisibility(View.VISIBLE);
                            layout_tishi.setVisibility(View.GONE);
                            //有预约订单
                            JSONArray data = jsonobject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                YuYueBean yuYueBean = new YuYueBean();
                                yuYueBean.setBranchid(data.getJSONObject(i).getString("branchid"));
                                yuYueBean.setCreate_at(data.getJSONObject(i).getString("create_at"));
                                yuYueBean.setId(data.getJSONObject(i).getString("id"));
                                yuYueBean.setWeid(data.getJSONObject(i).getString("weid"));
                                yuYueBean.setDatenum(data.getJSONObject(i).getString("datenum"));
                                yuYueBean.setFrom_user(data.getJSONObject(i).getString("from_user"));
                                yuYueBean.setMobile(data.getJSONObject(i).getString("mobile"));
                                yuYueBean.setRealname(data.getJSONObject(i).getString("realname"));
                                yuYueBean.setDatetime_st(data.getJSONObject(i).getString("datetime_st"));
                                yuYueBean.setStatus(data.getJSONObject(i).getString("status"));
                                yuYueBean.setIs_delete(data.getJSONObject(i).getString("is_delete"));
                                listY.add(yuYueBean);
                            }
                            yuYueAdapter.notifyDataSetChanged();
                        } else {
                            //无预约订单
                            data_layout.setVisibility(View.GONE);
                            layout_tishi.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

                 //这个是点击了取消按钮返回来的命令执行操作
                case 100:
                        int postion = (int) msg.obj;
                        Utils.getACache(getContext()).put("yyindex", postion + "");
                        Map<String, String> map = MapUtilsSetParam.getMap(getContext());
                        map.put("opp", "deletelist");
                        map.put("id", listY.get(postion).getId());
                        NetUtils.netWorkByMethodPost(getContext(), map, IpConfig.URL, handler, 101);
                    break;
                case 101:
                    //这个是点击取消后的执行操作
                    int pos = Integer.parseInt(Utils.getACache(getContext()).getAsString("yyindex"));
                    String deleteJson = (String) msg.obj;
                    try {
                        JSONObject jsonobject = new JSONObject(deleteJson);
                        int status = jsonobject.getInt("status");
                        if (status == 1) {
                            Toast.makeText(getContext(), "取消成功", Toast.LENGTH_SHORT).show();
                            listY.remove(pos);
                        } else {
                            Toast.makeText(getContext(), "取消失败，等会再试", Toast.LENGTH_SHORT).show();
                        }
                     yuYueAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }finally {
                         //不管最终中有没有取消成功的要把index remove掉
                         Utils.getACache(getContext()).remove("yyindex");
                         if (listY.size()<=0){
                             data_layout.setVisibility(View.GONE);
                             layout_tishi.setVisibility(View.VISIBLE);
                         }
                    }
                    break;
            }
        }
    };
}
