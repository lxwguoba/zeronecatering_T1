package com.zerone.catering.activitys.branch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zerone.catering.R;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.activitys.main.OrderMainActivity;
import com.zerone.catering.adapter.branch.BranchSelectedAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.db.impl.BranchDao;
import com.zerone.catering.db.impl.CatgoryGoodsDao;
import com.zerone.catering.db.impl.GoodDetailsDao;
import com.zerone.catering.db.impl.WorkerTabeDao;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.Worker;
import com.zerone.catering.handler.UpdateHandler;
import com.zerone.catering.refreshandclose.CloseLoginActvity;
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
 * Created by Administrator on 2017/6/23.
 * 这个是分店选择页面的activity
 * 主要功能是：获取分店的选择、版本是否升级等问题。
 */

public class BranchActivity extends BaseActvity {

    //展示分店用的列表控件
    private RecyclerView recyclerView;
    //存放分店数据的集合
    private List<Branch> branchList;
    //分店数据展示的适配器
    private BranchSelectedAdapter fdAdapter;
    private static final int LOADINGDATAGET = 11;
    private static final int LOADINGDATA = 12;

    //数据的持久化   把分店的数据存入sqlite数据库中
    private BranchDao branchDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fendianselected);
        branchDao = new BranchDao(this);
        GoodDetailsDao gdl = new GoodDetailsDao(this);
        CatgoryGoodsDao cg = new CatgoryGoodsDao(this);
        try {
            cg.del();
            gdl.del();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        /清除原来桌面的说有缓存
        String size = Utils.getACache(this).getAsString("size");
        Utils.getACache(this).remove("foodjson");
        if (size != null) {
            int sizef = Integer.parseInt(size);
            for (int i = 0; i < sizef; i++) {
                Utils.getACache(this).remove("" + i);
            }
        }


        /** 判断是否需要更新APP **/
        checkUpdate();
        initView();
        loadingData();
    }

    /**
     * 网络请求获取分店的数据 并展示数据
     */
    private void loadingData() {
        //这个是新的 有待测试
        Map<String, String> map = MapUtilsSetParam.getMap(this);
        map.put("opp", "getbranch");
        NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handlerMsg, LOADINGDATA);
    }

    /**
     *
     */
    private void initView() {
        branchList = new ArrayList<Branch>();
        recyclerView = (RecyclerView) findViewById(R.id.fendanselect_recycle);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);
    }

    private void setRecyclerViewEvent(final List<Branch> list) {
        fdAdapter.setOnItemClickListener(new BranchSelectedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = list.get(position).getId();
                Branch branch = list.get(position);
                Utils.getACache(BranchActivity.this).put("branch", branch);
                //我想在获取分店时把接待员获取 并存入数据库

                //获取店铺人员的数据
                Map<String, String> mapWorker = new HashMap<String, String>();
                mapWorker.put("act", "module");
                mapWorker.put("name", "bj_qmxk");
                mapWorker.put("do", "app_api");
                mapWorker.put("opp", "getworker");
                mapWorker.put("session", Utils.getSeesion(BranchActivity.this));
                mapWorker.put("branchid", Utils.getBranch(BranchActivity.this).getId());
                NetUtils.netWorkByMethodPost(BranchActivity.this, mapWorker, IpConfig.URL, handler, 10);
                //发送通知
                EventBus.getDefault().post(new CloseLoginActvity(1, "获取到了分店，所以要关闭login页面"));
            }
        });
    }

    Handler handlerMsg = new Handler() {

        private Handler handler;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADINGDATAGET:
                    String response = (String) msg.obj;
//                      Log.d("URL",response);
                    break;
                case LOADINGDATA:
                    String respo = (String) msg.obj;
//                     Log.d("URL","==============="+respo);
                    try {
                        //这个由于session会过期  所以登录后只有获取了分店数据后才算登录成功。
                        Toast.makeText(BranchActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        branchList = JSON.parseArray(respo, Branch.class);
                        if (branchList.size() == 0) {
                            Toast.makeText(BranchActivity.this, "该店暂无分店", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        fdAdapter = new BranchSelectedAdapter(branchList);
                        recyclerView.setAdapter(fdAdapter);
                        setRecyclerViewEvent(branchList);
                    } catch (com.alibaba.fastjson.JSONException e) {
                        //出现这个错误是session的值过期 登录后获取不到值 所以我们需要重新登录
                        Toast.makeText(BranchActivity.this, "获取数据失败，别灰心请重新登录即可。", Toast.LENGTH_SHORT).show();
                        BranchActivity.this.finish();
                    }
                    //把数据插入数据库中
                    try {
                        insterinto(branchList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                //检测更新返回的数据
                case 100:
                    String upjson = (String) msg.obj;
//                     Log.i("URL","upjson="+upjson);
                    try {
                        JSONObject jsonObject = new JSONObject(upjson);
                        int status = jsonObject.getInt("status");
                        if (status == 1) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            String version = data.getString("version");
                            String download_url = data.getString("download_url");
                            handler = new UpdateHandler(BranchActivity.this, version, download_url);
                            Message ms = handler.obtainMessage();
                            ms.what = UpdateHandler.TYPE_UPDATE;
                            handler.sendMessage(ms);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //出现这个错误是session的值过期 登录后获取不到值 所以我们需要重新登录
                        Toast.makeText(BranchActivity.this, "获取数据失败，别灰心请重新登录即可。", Toast.LENGTH_SHORT).show();
                        BranchActivity.this.finish();
//                         Log.d("BranchActivity", e.getMessage().toString());

                    }
                    break;
            }
        }
    };

    /**
     * 把数据放入分店数据表中
     *
     * @param branchList
     */
    private void insterinto(List<Branch> branchList) throws Exception {
        //每次添加前给数据清空了
        branchDao.del();
        for (int i = 0; i < branchList.size(); i++) {
            branchDao.add(branchList.get(i));
        }
    }

    //检查是否需要更新版本
    private void checkUpdate() {
        Map<String, String> map = MapUtilsSetParam.getMap(this);
        map.put("opp", "getappversion");
        NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handlerMsg, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当页面摧毁后 关闭登录页面
//        EventBus.getDefault().post(new CloseLoginActvity(1,"获取到了分店，所以要关闭login页面"));
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    String wkJson = (String) msg.obj;
                    Log.i("TAG", "handleMessage: "+wkJson);
                      List<Worker> workerList = new ArrayList<>();
                    try {
                        JSONArray array = new JSONArray(wkJson);
                        if (array!=null){
                            for (int i = 0; i < array.length(); i++) {
                                Worker worker = new Worker();
                                worker.setName(array.getJSONObject(i).getString("name"));
                                worker.setWorkerid(array.getJSONObject(i).getString("workerid"));
                                worker.setReception_qr(array.getJSONObject(i).getString("reception_qr"));
                                workerList.add(worker);
                            }
                            insertIntoWorkerTable(workerList);
                            Intent intent = new Intent(BranchActivity.this, OrderMainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(BranchActivity.this, "暂无工作人员，请去后台设置工作人员后再登录", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


            }
        }
    };


    /**
     * 把数据插入数据库
     * @param workerList
     */
    private void insertIntoWorkerTable(List<Worker> workerList) throws Exception {
        WorkerTabeDao wtd= new WorkerTabeDao(BranchActivity.this);
         wtd.deltable();
         for (int i=0;i<workerList.size();i++){
             wtd.addWorker(workerList.get(i));
         }
    }
}
