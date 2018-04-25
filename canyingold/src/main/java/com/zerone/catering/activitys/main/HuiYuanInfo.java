package com.zerone.catering.activitys.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zerone.catering.R;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.adapter.HuiYuanInfoAdapter;
import com.zerone.catering.adapter.HuiYuanInfoBean_XianChang;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.HuiYuanInfoBean;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/3.
 */

public class HuiYuanInfo extends BaseActvity implements View.OnClickListener {

    private ListView huiyuan_listview;

    private List<HuiYuanInfoBean.MemberlistBean> list;
    //会员信息的adptera
    private HuiYuanInfoAdapter hyAdapter;
    private Button btn_shouye;
    private Button btn_shangyiye;
    private Button btn_xiayiye;
    //会员信息列表
    private static final int HUIYUANXINFOLIEBAO= 0;
    //现场顾客
    private static final int XIANGCHANGGUKE=1;
    //搜索的顾客
    private static final int SEARCHCUSTOM=2;
    private GoogleApiClient client;
    private EditText serch_info;
    private ImageView search_btn;
    private Intent intent;
    private Intent responseIntent;
    private ACache mCache;
    private String session;
    private Branch branch;
    private int status;
    private ImageView huiyuan_search;
    private RelativeLayout serch_edit;
    private ImageView huiyuanxini_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCache = ACache.get(this);
        session = mCache.getAsString("session");
        branch = (Branch) mCache.getAsObject("branch");
        //返回请求数据用的Intent的
        responseIntent = getIntent();
        status = responseIntent.getIntExtra("status",0);
        setContentView(R.layout.activity_neworder_huiyuan);
        LoaingData();
        initData();
        initView();
        ActionListView();
    }

    private void LoaingData() {

        //0为会员的展示 1为现场顾客
        if (status==0){
            Map<String, String> map = MapUtilsSetParam.getMap(HuiYuanInfo.this);
            map.put("opp", "getmember");
            map.put("memberid","" );
            map.put("realname","");
            map.put("mobile", "");
            String url= IpConfig.URL+"act=module&"+"name=bj_qmxk&"+"do=app_api&"+"op=android_app&"+"opp=getmember&"+"session="+session+"&memberid="
                    +""+"&realname="+""+"&mobile="+"";

            Log.d("TAG","000000000000000000=="+url);
            NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handler, HUIYUANXINFOLIEBAO);
        }else {
            Map<String, String> xianchang = MapUtilsSetParam.getMap(HuiYuanInfo.this);
            xianchang.put("opp", "getviewing");
            xianchang.put("branchid", branch.getId());
            String ur=  IpConfig.URL+"act=module&"+"name=bj_qmxk&"+"do=app_api&"+"opp=getviewing&"+"session="+session+"&branchid="+branch.getId();
            Log.d("URL",ur);
            NetUtils.netWorkByMethodPost(this, xianchang, IpConfig.URL,  handler,XIANGCHANGGUKE);

        }

    }

    /**
     * listview的点击事件
     */
    private void ActionListView() {
        huiyuan_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                HuiYuanInfoBean.MemberlistBean hmb = new HuiYuanInfoBean.MemberlistBean();
                hmb.setId(list.get(i).getId());
                hmb.setWeid(list.get(i).getWeid());
                hmb.setBranchid(list.get(i).getBranchid());
                hmb.setShareid(list.get(i).getShareid());
                hmb.setFrom_user(list.get(i).getFrom_user());
                hmb.setRealname(list.get(i).getRealname());
                hmb.setMobile(list.get(i).getMobile());
                hmb.setMyqq(list.get(i).getMyqq());
                hmb.setCommission(list.get(i).getCommission());
                hmb.setZhifu(list.get(i).getZhifu());
                hmb.setContent(list.get(i).getContent());
                hmb.setCreatetime(list.get(i).getCreatetime());
                hmb.setFlag(list.get(i).getFlag());
                hmb.setLevelid(list.get(i).getLevelid());
                hmb.setFlagtime(list.get(i).getFlagtime());
                hmb.setCredit2(list.get(i).getCredit2());
                hmb.setCredit1(list.get(i).getCredit1());
                hmb.setStatus(list.get(i).getStatus());
                hmb.setClickcount(list.get(i).getClickcount());
                hmb.setMbbinded(list.get(i).getMbbinded());
                hmb.setBirthdate(list.get(i).getBirthdate());
                hmb.setRemark(list.get(i).getRemark());
                hmb.setIsworking(list.get(i).getIsworking());
                hmb.setAvatar(list.get(i).getAvatar());
                hmb.setTitle(list.get(i).getTitle());
                //把返回数据存入Intent
                responseIntent.putExtra("huiyuaninfo_data",hmb);
                //设置返回数据
                HuiYuanInfo.this.setResult(200, responseIntent);
                //关闭Activity
                HuiYuanInfo.this.finish();

                mCache.remove("huiyuaninf");
                mCache.remove("huiyuanifoxianchang");

            }
        });

    }

    /**
     * 数据模拟
     */
    private void initData() {
        list = new ArrayList<>();
    }

    /**
     * 初始化view
     */
    private void initView() {
        huiyuan_listview = (ListView) findViewById(R.id.huiyuan_listview);
        hyAdapter = new HuiYuanInfoAdapter(this, list);
        huiyuan_listview.setAdapter(hyAdapter);

//        btn_shouye = (Button) findViewById(R.id.shouye);
//        btn_shouye.setOnClickListener(this);
//        btn_shangyiye = (Button) findViewById(R.id.shangyiye);
//        btn_shangyiye.setOnClickListener(this);
//        btn_xiayiye = (Button) findViewById(R.id.xiayiye);
//        btn_xiayiye.setOnClickListener(this);

         //输入的信息
        serch_info = (EditText) findViewById(R.id.serch_info);
        //输入信息后查询
        search_btn = (ImageView) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);

        //没有输入框的效果
        huiyuan_search = (ImageView) findViewById(R.id.huiyuan_search);
        huiyuan_search.setOnClickListener(this);

        serch_edit = (RelativeLayout) findViewById(R.id.serch_edit);

         //退出activity
        huiyuanxini_return = (ImageView) findViewById(R.id.huiyuanxini_return);
        huiyuanxini_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCache.remove("huiyuaninf");
                mCache.remove("huiyuanifoxianchang");
                HuiYuanInfo.this.finish();
            }
        });

    }

    /**
     * 按钮的点击事件
     *
     * @param v
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            //首页
//            case R.id.shouye:
//                btn_shangyiye.setTextColor(Color.rgb(51, 51, 51));
//                btn_shouye.setTextColor(Color.rgb(255, 255, 255));
//                btn_xiayiye.setTextColor(Color.rgb(51, 51, 51));
//                btn_shangyiye.setBackgroundResource(R.mipmap.huiyuanxinxi_yema02);
//                btn_xiayiye.setBackgroundResource(R.mipmap.huiyuanxinxi_yema02);
//                btn_shouye.setBackgroundResource(R.mipmap.huiyuanxinxi_yema01);
//                break;
//
//            //上一页
//            case R.id.shangyiye:
//                btn_shangyiye.setTextColor(Color.rgb(255, 255, 255));
//                btn_shouye.setTextColor(Color.rgb(51, 51, 51));
//                btn_xiayiye.setTextColor(Color.rgb(51, 51, 51));
//                btn_shangyiye.setBackgroundResource(R.mipmap.huiyuanxinxi_yema01);
//                btn_xiayiye.setBackgroundResource(R.mipmap.huiyuanxinxi_yema02);
//                btn_shouye.setBackgroundResource(R.mipmap.huiyuanxinxi_yema02);
//                break;
//            //下一页
//            case R.id.xiayiye:
//                btn_shangyiye.setTextColor(Color.rgb(51, 51, 51));
//                btn_shouye.setTextColor(Color.rgb(51, 51, 51));
//                btn_xiayiye.setTextColor(Color.rgb(255, 255, 255));
//                btn_shangyiye.setBackgroundResource(R.mipmap.huiyuanxinxi_yema02);
//                btn_xiayiye.setBackgroundResource(R.mipmap.huiyuanxinxi_yema01);
//                btn_shouye.setBackgroundResource(R.mipmap.huiyuanxinxi_yema02);
//                break;

            case R.id.search_btn:
//                serch_edit.setVisibility(View.GONE);
//                huiyuan_search.setVisibility(View.VISIBLE);
//                serch_edit.setAnimation(AnimationUtils.makeOutAnimation(this,true));
//                huiyuan_search.setAnimation(AnimationUtils.makeInAnimation(this,true));
                String searchinfostr = serch_info.getText().toString().trim();
                if (searchinfostr!=null&&searchinfostr.length()>0){
                    Map<String, String> map = MapUtilsSetParam.getMap(HuiYuanInfo.this);
                    map.put("opp", "getmember");
                    map.put("memberid","" );
                    map.put("keyword",searchinfostr);
                    NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handler, SEARCHCUSTOM);
                }
                break;

            case R.id.huiyuan_search:
                //2017-10-19
                serch_edit.setVisibility(View.VISIBLE);
                huiyuan_search.setVisibility(View.GONE);
                serch_edit.setAnimation(AnimationUtils.makeInAnimation(this,true));
                huiyuan_search.setAnimation(AnimationUtils.makeOutAnimation(this,true));

                break;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                  switch (msg.what){
                      case HUIYUANXINFOLIEBAO:
                          String  huiyuanifo= (String) msg.obj;
                          mCache.put("huiyuaninf",huiyuanifo);
                          Log.i("HUIYUANINFO", "handleMessage: "+huiyuanifo);
                          HuiYuanInfoBean huiYuanInfo = JSON.parseObject(huiyuanifo, HuiYuanInfoBean.class);
                          if (huiYuanInfo.getMembernum()>0){
                          List<HuiYuanInfoBean.MemberlistBean> hm =huiYuanInfo.getMemberlist();
                          for (int i=0;i<hm.size();i++){
                              HuiYuanInfoBean.MemberlistBean hmb = new HuiYuanInfoBean.MemberlistBean();
                              hmb.setId(hm.get(i).getId());
                              hmb.setWeid(hm.get(i).getWeid());
                              hmb.setBranchid(hm.get(i).getBranchid());
                              hmb.setShareid(hm.get(i).getShareid());
                              hmb.setFrom_user(hm.get(i).getFrom_user());
                              hmb.setRealname(hm.get(i).getRealname());
                              hmb.setMobile(hm.get(i).getMobile());
                              hmb.setMyqq(hm.get(i).getMyqq());
                              hmb.setCommission(hm.get(i).getCommission());
                              hmb.setZhifu(hm.get(i).getZhifu());
                              hmb.setContent(hm.get(i).getContent());
                              hmb.setCreatetime(hm.get(i).getCreatetime());
                              hmb.setFlag(hm.get(i).getFlag());
                              hmb.setLevelid(hm.get(i).getLevelid());
                              hmb.setFlagtime(hm.get(i).getFlagtime());
                              hmb.setCredit2(hm.get(i).getCredit2());
                              hmb.setCredit1(hm.get(i).getCredit1());
                              hmb.setStatus(hm.get(i).getStatus());
                              hmb.setClickcount(hm.get(i).getClickcount());
                              hmb.setMbbinded(hm.get(i).getMbbinded());
                              hmb.setBirthdate(hm.get(i).getBirthdate());
                              hmb.setRemark(hm.get(i).getRemark());
                              hmb.setIsworking(hm.get(i).getIsworking());
                              hmb.setAvatar(hm.get(i).getAvatar());
                              hmb.setTitle(hm.get(i).getTitle());
                              list.add(hmb);
                            }
                          }
                          hyAdapter.notifyDataSetChanged();
                          break;
                      case XIANGCHANGGUKE:
                      String  huiyuanifoxianchang= (String) msg.obj;
                       Log.d("HuiYuanInfo", "member===="+huiyuanifoxianchang);
                       mCache.put("huiyuanifoxianchang",huiyuanifoxianchang);
                         HuiYuanInfoBean_XianChang huiYuanInfoxianchang = JSON.parseObject( huiyuanifoxianchang,HuiYuanInfoBean_XianChang.class);
                          List<HuiYuanInfoBean_XianChang.MemberlistBean> hm =huiYuanInfoxianchang .getMemberlist();
                          if (huiYuanInfoxianchang.getResult()>0){
                          for (int i=0;i<hm.size();i++){
                              HuiYuanInfoBean.MemberlistBean hmb = new HuiYuanInfoBean.MemberlistBean();
                              hmb.setId(hm.get(i).getId());
                              hmb.setWeid(hm.get(i).getWeid());
                              hmb.setBranchid(hm.get(i).getBranchid());
                              hmb.setShareid(hm.get(i).getShareid());
                              hmb.setFrom_user(hm.get(i).getFrom_user());
                              hmb.setRealname(hm.get(i).getRealname());
                              hmb.setMobile(hm.get(i).getMobile());
                              hmb.setMyqq(hm.get(i).getMyqq());
                              hmb.setCommission(hm.get(i).getCommission());
                              hmb.setZhifu(hm.get(i).getZhifu());
                              hmb.setContent(hm.get(i).getContent());
                              hmb.setCreatetime(hm.get(i).getCreatetime());
                              hmb.setFlag(hm.get(i).getFlag());
                              hmb.setLevelid(hm.get(i).getLevelid());
                              hmb.setFlagtime(hm.get(i).getFlagtime());
                              hmb.setCredit2(hm.get(i).getCredit2());
                              hmb.setCredit1(hm.get(i).getCredit1());
                              hmb.setStatus(hm.get(i).getStatus());
                              hmb.setClickcount(hm.get(i).getClickcount());
                              hmb.setMbbinded(hm.get(i).getMbbinded());
                              hmb.setBirthdate(hm.get(i).getBirthdate());
                              hmb.setRemark(hm.get(i).getRemark());
                              hmb.setIsworking(hm.get(i).getIsworking());
                              hmb.setAvatar(hm.get(i).getAvatar());
                              hmb.setTitle(hm.get(i).getTitle());
                              list.add(hmb);
                            }
                        }
                        hyAdapter.notifyDataSetChanged();
                          break;

                      case SEARCHCUSTOM:
                          String searchJson= (String) msg.obj;
                          list.clear();
                          HuiYuanInfoBean huiYuanInf = JSON.parseObject(searchJson, HuiYuanInfoBean.class);
                          if (huiYuanInf.getMembernum()>0){
                              List<HuiYuanInfoBean.MemberlistBean> h =huiYuanInf.getMemberlist();
                              for (int i=0;i<h.size();i++){
                                  HuiYuanInfoBean.MemberlistBean hmb = new HuiYuanInfoBean.MemberlistBean();
                                  hmb.setId(h.get(i).getId());
                                  hmb.setWeid(h.get(i).getWeid());
                                  hmb.setBranchid(h.get(i).getBranchid());
                                  hmb.setShareid(h.get(i).getShareid());
                                  hmb.setFrom_user(h.get(i).getFrom_user());
                                  hmb.setRealname(h.get(i).getRealname());
                                  hmb.setMobile(h.get(i).getMobile());
                                  hmb.setMyqq(h.get(i).getMyqq());
                                  hmb.setCommission(h.get(i).getCommission());
                                  hmb.setZhifu(h.get(i).getZhifu());
                                  hmb.setContent(h.get(i).getContent());
                                  hmb.setCreatetime(h.get(i).getCreatetime());
                                  hmb.setFlag(h.get(i).getFlag());
                                  hmb.setLevelid(h.get(i).getLevelid());
                                  hmb.setFlagtime(h.get(i).getFlagtime());
                                  hmb.setCredit2(h.get(i).getCredit2());
                                  hmb.setCredit1(h.get(i).getCredit1());
                                  hmb.setStatus(h.get(i).getStatus());
                                  hmb.setClickcount(h.get(i).getClickcount());
                                  hmb.setMbbinded(h.get(i).getMbbinded());
                                  hmb.setBirthdate(h.get(i).getBirthdate());
                                  hmb.setRemark(h.get(i).getRemark());
                                  hmb.setIsworking(h.get(i).getIsworking());
                                  hmb.setAvatar(h.get(i).getAvatar());
                                  hmb.setTitle(h.get(i).getTitle());
                                  list.add(hmb);
                              }
                          }else{
                              Toast.makeText(HuiYuanInfo.this, "没有这个会员，请重新查找", Toast.LENGTH_SHORT).show();
                               if (status==0){
                                   String huiyuaninfo = mCache.getAsString("huiyuaninfo");
                                   setlistdata(huiyuaninfo);
                               }else{
                                   String   huiyuanifoxianchan= mCache.getAsString("huiyuanifoxianchang");
                                   setlistdata(huiyuanifoxianchan);
                              }
                          }
                          hyAdapter.notifyDataSetChanged();
                          serch_info.setText("");
                          break;
                  }
        }
    };


    public void  setlistdata(String listjson){
        HuiYuanInfoBean huiYuanInfo = JSON.parseObject(listjson, HuiYuanInfoBean.class);
        if (huiYuanInfo.getMembernum()>0){
            List<HuiYuanInfoBean.MemberlistBean> hm =huiYuanInfo.getMemberlist();
            for (int i=0;i<hm.size();i++){
                HuiYuanInfoBean.MemberlistBean hmb = new HuiYuanInfoBean.MemberlistBean();
                hmb.setId(hm.get(i).getId());
                hmb.setWeid(hm.get(i).getWeid());
                hmb.setBranchid(hm.get(i).getBranchid());
                hmb.setShareid(hm.get(i).getShareid());
                hmb.setFrom_user(hm.get(i).getFrom_user());
                hmb.setRealname(hm.get(i).getRealname());
                hmb.setMobile(hm.get(i).getMobile());
                hmb.setMyqq(hm.get(i).getMyqq());
                hmb.setCommission(hm.get(i).getCommission());
                hmb.setZhifu(hm.get(i).getZhifu());
                hmb.setContent(hm.get(i).getContent());
                hmb.setCreatetime(hm.get(i).getCreatetime());
                hmb.setFlag(hm.get(i).getFlag());
                hmb.setLevelid(hm.get(i).getLevelid());
                hmb.setFlagtime(hm.get(i).getFlagtime());
                hmb.setCredit2(hm.get(i).getCredit2());
                hmb.setCredit1(hm.get(i).getCredit1());
                hmb.setStatus(hm.get(i).getStatus());
                hmb.setClickcount(hm.get(i).getClickcount());
                hmb.setMbbinded(hm.get(i).getMbbinded());
                hmb.setBirthdate(hm.get(i).getBirthdate());
                hmb.setRemark(hm.get(i).getRemark());
                hmb.setIsworking(hm.get(i).getIsworking());
                hmb.setAvatar(hm.get(i).getAvatar());
                hmb.setTitle(hm.get(i).getTitle());
                list.add(hmb);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCache.remove("huiyuaninf");
        mCache.remove("huiyuanifoxianchang");
    }
}
