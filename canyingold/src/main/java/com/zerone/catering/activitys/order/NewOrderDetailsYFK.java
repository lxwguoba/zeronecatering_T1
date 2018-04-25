package com.zerone.catering.activitys.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zerone.catering.R;
import com.zerone.catering.action.FinishActvity;
import com.zerone.catering.adapter.ListViewOrderDetailsItemsAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.dialog.Activity_Dialog;
import com.zerone.catering.dialog.Activity_Dialog_YuYue;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.OrderInfo;
import com.zerone.catering.refreshandclose.shuaxin.ShuaXinShuJu;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.UtilsTime;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */

public class NewOrderDetailsYFK extends AppCompatActivity implements View.OnClickListener {
    //获取订单详情的
    private static final int LOADINGDATA_ORDERDETAILS = 0;
    //结账
    private static final int LOADINGDATA_JIEZHANG = 1;
    //确认退款
    private static final int LOADINGDATA_CONFIRMPAY = 2;

    private List<OrderInfo.DataBean.ItemBean.GoodsBean> listBeen;
    private ListView orderDetailsListView;
    private ListViewOrderDetailsItemsAdapter listAdapter;
    private CheckBox checkweixinzhifu;
    private CheckBox checkzhifubao;
    private CheckBox checkxianjin;
    private CheckBox checkshuaka;
    private ACache mCache;
    private String session;
    private Branch branch;
    private Intent intent;
    private TextView shouhuoren;
    private TextView tableName;
    private TextView dingdanbianhao;
    private TextView shouhuorendianhua;
    private TextView shouhuodizhi;
    private TextView zhifufangshi;
    private TextView dingdanzhuantai;
    private TextView tichengrenyuan;
    private TextView xiadanzhuantai;
    private TextView xiadanshijian;
    private TextView maijialiuyan;
    private TextView youhuizhekou;
    private TextView dingdanjiner;
    private TextView yingshoujiner;

    private OrderInfo orderInfo;
    private Button submit_pay;
    private Button zhuozi;

    //返回来的json数据
    private JSONObject json;
    private TextView seat_fee;
    private TextView tablecost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworderdetailssix);
        //注册 ，判断是否存在  存在不用注册不存在 在注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        intent = getIntent();
        mCache = ACache.get(this);
        session = mCache.getAsString("session");
        branch = (Branch) mCache.getAsObject("branch");
        initData();
        initView();
        LoagingData();
        initListViewAdapter();
        setCheckBox();

    }

    private void LoagingData() {
        String orderid = intent.getStringExtra("orderid");
        Map<String, String> map = MapUtilsSetParam.getMap(NewOrderDetailsYFK.this);
        map.put("opp", "getorderdetail");
        map.put("branchid", branch.getId());
        map.put("orderid", orderid);
        NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handler, LOADINGDATA_ORDERDETAILS);
    }


    /**
     * view的初始话
     */
    private void initView() {
        //商品列表
        orderDetailsListView = (ListView) findViewById(R.id.orderDetailsListView);

        Button addAndcancelFood = (Button) findViewById(R.id.addAndcancelFood);
        addAndcancelFood.setVisibility(View.GONE);

        //四个checkox
        LinearLayout checkLayout = (LinearLayout) findViewById(R.id.checkLayout);
        checkLayout.setVisibility(View.GONE);
        checkweixinzhifu = (CheckBox) findViewById(R.id.checkweixinzhifu);
        checkzhifubao = (CheckBox) findViewById(R.id.checkzhifubao);
        checkxianjin = (CheckBox) findViewById(R.id.checkxianjin);
        checkshuaka = (CheckBox) findViewById(R.id.checkshuaka);

        //详情的信息控件
        shouhuoren = (TextView) findViewById(R.id.details_consignee);

        tableName = (TextView) findViewById(R.id.tableName);

        dingdanbianhao = (TextView) findViewById(R.id.details_ordernum);

        shouhuorendianhua = (TextView) findViewById(R.id.details_userphone);

        shouhuodizhi = (TextView) findViewById(R.id.details_orderadress);

        zhifufangshi = (TextView) findViewById(R.id.details_orderpay);

        dingdanzhuantai = (TextView) findViewById(R.id.details_orderstatus);

        tichengrenyuan = (TextView) findViewById(R.id.details_ordertichengrenyuan);

        xiadanzhuantai = (TextView) findViewById(R.id.details_orderxiadanstatus);

        xiadanshijian = (TextView) findViewById(R.id.details_time);

        maijialiuyan = (TextView) findViewById(R.id.details_liuyann);

        youhuizhekou = (TextView) findViewById(R.id.youhuizhekou);

        dingdanjiner = (TextView) findViewById(R.id.digndanjiner);

        yingshoujiner = (TextView) findViewById(R.id.yingshoujiner);

        //---------按钮区------------
        submit_pay = (Button) findViewById(R.id.submit_pay);
        submit_pay.setOnClickListener(this);

        zhuozi = (Button) findViewById(R.id.zhuozi);
        zhuozi.setOnClickListener(this);
        ImageView backimg = (ImageView) findViewById(R.id.imgbutton_two);
        backimg.setOnClickListener(new FinishActvity(this));
        //餐位费
        seat_fee = (TextView) findViewById(R.id.seat_fee);
        //设置是餐位费还是配送费
        tablecost = (TextView) findViewById(R.id.tablecost);

    }

    private void initListViewAdapter() {
        listAdapter = new ListViewOrderDetailsItemsAdapter(this, listBeen);
        orderDetailsListView.setAdapter(listAdapter);
    }

    /**
     * 数据测试
     */
    private void initData() {
        listBeen = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_pay:
                //已付款  submit_pay 是确认发货
                try {
                    //外卖
                    if ("1".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                        Intent intent = new Intent(NewOrderDetailsYFK.this, Activity_Dialog.class);
                        String orderId = null;
                        orderId = json.getJSONObject("data").getJSONObject("item").getString("id");
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);
                    } else if ("2".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                        //预约
                        Intent intent = new Intent(NewOrderDetailsYFK.this, Activity_Dialog_YuYue.class);
                        String orderId = json.getJSONObject("data").getJSONObject("item").getString("id");
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);

                    } else if ("3".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                        //现场订单  就是关闭页面返回开单
                        //发送同通知
                        //发送广播
                        EventBus.getDefault().post(new ShuaXinShuJu(4, "已付款"));
                        NewOrderDetailsYFK.this.finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.zhuozi:
                //已付款  zhuozi 是确认退款
                try {
                    //确认退款
                    Map<String, String> mapSure = MapUtilsSetParam.getMap(NewOrderDetailsYFK.this);
                    mapSure.put("orderid", json.getJSONObject("data").getJSONObject("item").getString("id"));
                    mapSure.put("opp", "returnpay");
                    NetUtils.netWorkByMethodPost(NewOrderDetailsYFK.this, mapSure, IpConfig.URL, handler, LOADINGDATA_CONFIRMPAY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    private void setCheckBox() {
        checkweixinzhifu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCache.put("paystaus", "7");
                    checkshuaka.setClickable(true);
                    checkweixinzhifu.setClickable(false);
                    checkzhifubao.setClickable(true);
                    checkxianjin.setClickable(true);

                    checkzhifubao.setChecked(false);
                    checkxianjin.setChecked(false);
                    checkshuaka.setChecked(false);
                }
//                checkweixinzhifu.setChecked(true);

            }
        });
        checkzhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCache.put("paystaus", "6");
                    checkshuaka.setClickable(true);
                    checkweixinzhifu.setClickable(true);
                    checkzhifubao.setClickable(false);
                    checkxianjin.setClickable(true);

                    checkweixinzhifu.setChecked(false);
                    checkxianjin.setChecked(false);
                    checkshuaka.setChecked(false);
                }
            }
        });

        checkxianjin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCache.put("paystaus", "4");
                    checkshuaka.setClickable(true);
                    checkweixinzhifu.setClickable(true);
                    checkzhifubao.setClickable(true);
                    checkxianjin.setClickable(false);
                    checkweixinzhifu.setChecked(false);
                    checkzhifubao.setChecked(false);
                    checkshuaka.setChecked(false);
                }

            }
        });

        checkshuaka.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCache.put("paystaus", "5");
                    checkshuaka.setClickable(false);
                    checkweixinzhifu.setClickable(true);
                    checkzhifubao.setClickable(true);
                    checkxianjin.setClickable(true);

                    checkweixinzhifu.setChecked(false);
                    checkzhifubao.setChecked(false);
                    checkxianjin.setChecked(false);
                }

            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADINGDATA_ORDERDETAILS:
                    String orderdetailsJson = (String) msg.obj;
                    Log.i("TAG", orderdetailsJson);
                    try {
                        json = new JSONObject(orderdetailsJson);
//                     Log.i("TAG",orderdetailsJson);
                        String userstr = json.getJSONObject("data").getJSONObject("item").getString("user");
//                     Log.d("NewOrderDetails", userstr);
                        String shrPhone = "";
                        String shAddress = "";
                        if (!"".equals(userstr)) {
                            shrPhone = json.getJSONObject("data").getJSONObject("item").getJSONObject("user").getString("mobile");
                            shAddress = json.getJSONObject("data").getJSONObject("item").getJSONObject("user").getString("address");
                        }
                        String shrname = json.getJSONObject("data").getJSONObject("orderownner").getString("realname");
                        String ordersn = json.getJSONObject("data").getJSONObject("item").getString("ordersn");

                        String createTime = json.getJSONObject("data").getJSONObject("item").getString("createtime");
                        long time = Long.parseLong(createTime);
                        xiadanshijian.setText(UtilsTime.getTime(time));
                        if ("1.00".equals(json.getJSONObject("data").getJSONObject("item").getString("sale"))) {
                            yingshoujiner.setText("￥" + json.getJSONObject("data").getJSONObject("item").getString("price"));
                            youhuizhekou.setText("未打折");
                        } else {
                            double yhzk = Double.parseDouble(json.getJSONObject("data").getJSONObject("item").getString("sale")) * 10;
                            youhuizhekou.setText(yhzk + "折");
                            double ysje = Double.parseDouble(json.getJSONObject("data").getJSONObject("item").getString("price")) * Double.parseDouble(json.getJSONObject("data").getJSONObject("item").getString("sale"));
                            yingshoujiner.setText("￥" +ysje );
                        }
                        dingdanjiner.setText("￥" + json.getJSONObject("data").getJSONObject("item").getString("goodsprice"));
                        maijialiuyan.setText(json.getJSONObject("data").getJSONObject("item").getString("remark"));
                        shouhuoren.setText(shrname);
                        dingdanbianhao.setText(ordersn);
                        shouhuorendianhua.setText(shrPhone);
                        shouhuodizhi.setText(shAddress);
                        String jsonObject = json.getJSONObject("data").getString("sharemem");
                        if ("{}".equals(jsonObject)) {
                            tichengrenyuan.setText("");
                        } else {
                            tichengrenyuan.setText(json.getJSONObject("data").getJSONObject("sharemem").getString("realname"));
                        }
                        tableName.setText(json.getJSONObject("data").getJSONObject("tableinfo").getString("tablename"));


                        if ("1".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            //外卖
                            submit_pay.setText("确认发货");
                            zhuozi.setVisibility(View.GONE);
//                            zhuozi.setText("确认退款");
                            tablecost.setText("配送费");
                            //这个是配送费
                            seat_fee.setText("￥" + json.getJSONObject("data").getJSONObject("item").getString("dispatchprice"));
                        } else if ("2".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            //2.预约就餐订单
                            submit_pay.setText("确认就餐");
                            zhuozi.setVisibility(View.GONE);
//                            zhuozi.setText("确认退款");
                            //这个是餐位费
                            seat_fee.setText("￥" + json.getJSONObject("data").getJSONObject("item").getString("seat_fee"));
                        } else if ("3".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            //2.现场订单
                            submit_pay.setText("返回开单");
                            zhuozi.setVisibility(View.GONE);
                            //这个是餐位费
                            seat_fee.setText("￥" + json.getJSONObject("data").getJSONObject("item").getString("seat_fee"));
                        }
                        ///--------------------
                        //支付方式
                        if ("1".equals(json.getJSONObject("data").getJSONObject("item").getString("paytype"))) {
                            zhifufangshi.setText("在线余额支付");
                        } else if ("2".equals(json.getJSONObject("data").getJSONObject("item").getString("paytype"))) {
                            zhifufangshi.setText("在线微信支付,微支付单号：" + json.getJSONObject("data").getJSONObject("item").getString("transid"));
                        } else if ("3".equals(json.getJSONObject("data").getJSONObject("item").getString("paytype"))) {
                            zhifufangshi.setText("现场现金支付，,现场确认人ID号：" + json.getJSONObject("data").getJSONObject("item").getString("confirmedby"));
                        } else if ("4".equals(json.getJSONObject("data").getJSONObject("item").getString("paytype"))) {
                            zhifufangshi.setText("现金支付,现场确认人ID号：" + json.getJSONObject("data").getJSONObject("item").getString("confirmedby"));
                        } else if ("5".equals(json.getJSONObject("data").getJSONObject("item").getString("paytype"))) {
                            zhifufangshi.setText("现场刷卡支付,现场确认人ID号：" + json.getJSONObject("data").getJSONObject("item").getString("confirmedby"));
                        } else if ("6".equals(json.getJSONObject("data").getJSONObject("item").getString("paytype"))) {
                            zhifufangshi.setText("现场支付宝付,现场确认人ID号：" + json.getJSONObject("data").getJSONObject("item").getString("confirmedby"));
                        } else if ("7".equals(json.getJSONObject("data").getJSONObject("item").getString("paytype"))) {
                            zhifufangshi.setText("现场微信支付,现场确认人ID号：" + json.getJSONObject("data").getJSONObject("item").getString("confirmedby"));

                        } else if ("8".equals(json.getJSONObject("data").getJSONObject("item").getString("paytype"))) {
                            zhifufangshi.setText("在线手动确认,现场确认人ID号：" + json.getJSONObject("data").getJSONObject("item").getString("confirmedby"));
                        }
                        //订单状态
                        if ("0".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("待付款");
                        } else if ("1".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("已付款");
                        } else if ("2".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("待收货");
                        } else if ("3".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("已完成");
                        } else if ("-1".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("已关闭");
                        } else if ("-2".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("退款中");
                        } else if ("-3".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("换货中");
                        } else if ("-4".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("退货中");
                        } else if ("-5".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("已退货");
                        } else if ("-6".equals(json.getJSONObject("data").getJSONObject("item").getString("status"))) {
                            dingdanzhuantai.setText("已退款");
                        }
                        String dispatchchoice = json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice");

                        if ("1".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            xiadanzhuantai.setText("外卖订单");
                        } else if ("2".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            xiadanzhuantai.setText("预约就餐订单");
                        } else if ("3".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            xiadanzhuantai.setText("现场订单");
                        }
                        ///--------------------
                        JSONArray jsonArray = json.getJSONObject("data").getJSONObject("item").getJSONArray("goods");
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                OrderInfo.DataBean.ItemBean.GoodsBean goods = new OrderInfo.DataBean.ItemBean.GoodsBean();
                                goods.setId(jsonArray.getJSONObject(i).getString("id"));
                                goods.setCommission(jsonArray.getJSONObject(i).getString("commission"));
                                goods.setGoodssn(jsonArray.getJSONObject(i).getString("goodssn"));
                                goods.setMarketprice(jsonArray.getJSONObject(i).getString("marketprice"));
                                goods.setOid(jsonArray.getJSONObject(i).getString("oid"));
                                goods.setOptionid(jsonArray.getJSONObject(i).getString("optionid"));
                                goods.setOptionname(jsonArray.getJSONObject(i).getString("optionname"));
                                goods.setPrice(jsonArray.getJSONObject(i).getString("price"));
                                goods.setProductsn(jsonArray.getJSONObject(i).getString("productsn"));
                                goods.setThumb(jsonArray.getJSONObject(i).getString("thumb"));
                                goods.setTitle(jsonArray.getJSONObject(i).getString("title"));
                                goods.setTotal(jsonArray.getJSONObject(i).getString("total"));
                                goods.setType(jsonArray.getJSONObject(i).getString("type"));
                                goods.setUnit(jsonArray.getJSONObject(i).getString("unit"));
                                listBeen.add(goods);
                            }
                            listAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case LOADINGDATA_JIEZHANG:
                    String jiezhangJson = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(jiezhangJson);
                        String status = json.getString("status");
                        String data = (String) json.getString("data");
                        if ("1".equals(status)) {
                            Toast.makeText(NewOrderDetailsYFK.this, data, Toast.LENGTH_SHORT).show();
                            //发送广播
                            EventBus.getDefault().post(new ShuaXinShuJu(1, "待付款"));
                            NewOrderDetailsYFK.this.finish();
                        } else {
                            Toast.makeText(NewOrderDetailsYFK.this, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mCache.remove("paystaus");
                    break;

                case LOADINGDATA_CONFIRMPAY:
                    String tuikuanJson = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(tuikuanJson);
                        int staus = jsonObject.getInt("status");
                        String data = jsonObject.getString("data");
                        if (staus == 1) {
                            //退款成功
                            //发布广播 刷新数据
                            Toast.makeText(NewOrderDetailsYFK.this, data, Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new ShuaXinShuJu(2, "已付款"));
                            NewOrderDetailsYFK.this.finish();
                        } else {
                            //退款失败
                            Toast.makeText(NewOrderDetailsYFK.this, data, Toast.LENGTH_SHORT).show();
                            NewOrderDetailsYFK.this.finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 200001:
                    String printjson = (String) msg.obj;
                    try {
                        JSONObject jsp = new JSONObject(printjson);
                        if (jsp.getInt("status") == 1) {
                            Toast.makeText(NewOrderDetailsYFK.this, jsp.getString("data"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NewOrderDetailsYFK.this, jsp.getString("data"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN) //
    public void ShuaXinShuJu(ShuaXinShuJu shuaXinShuJu) {
        int pag = shuaXinShuJu.getPage();
        //关闭当前页面  有问题
        if (pag == 10) {
//          Log.i("URL", "ShuaXinShuJu: "+"收到信息了哦哦哦哦哦  哦哦哦 ");
            NewOrderDetailsYFK.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }
}
