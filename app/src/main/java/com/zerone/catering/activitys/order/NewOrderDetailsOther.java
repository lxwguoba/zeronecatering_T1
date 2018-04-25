package com.zerone.catering.activitys.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.adapter.ListViewOrderDetailsItemsAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.OrderInfo;
import com.zerone.catering.refreshandclose.shuaxin.ShuaXinShuJu;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.UtilsTime;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */

public class NewOrderDetailsOther extends BaseActvity implements View.OnClickListener {
    //获取订单详情的
    private static final int LOADINGDATA_ORDERDETAILS = 0;
    //结账
    private static final int LOADINGDATA_JIEZHANG = 1;

    //完成订单
    private static final int LOADINGDATA_ORDERFINISH = 2;


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
    private Button addfood;
    private Button cancelfood;
    private String status;

    //返回来的json数据
    private JSONObject json;
    //这个是用来区分订单的类型
    private String dispatchchoice;
    private TextView seat_fee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworderdetailssix);
        intent = getIntent();
        status = intent.getStringExtra("status");
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
        Map<String, String> map = MapUtilsSetParam.getMap(NewOrderDetailsOther.this);
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

        //------这是后面几个页面的订单详情----------
        Button addAndcancelFood = (Button) findViewById(R.id.addAndcancelFood);
        addAndcancelFood.setVisibility(View.GONE);

        zhuozi = (Button) findViewById(R.id.zhuozi);
        zhuozi.setOnClickListener(this);
        submit_pay = (Button) findViewById(R.id.submit_pay);
        submit_pay.setOnClickListener(this);
        if ("2".equals(status)) {
            //待收货
            submit_pay.setText("完成订单");
            zhuozi.setText("取消发货");
            submit_pay.setVisibility(View.VISIBLE);
            zhuozi.setVisibility(View.VISIBLE);
        } else {
            submit_pay.setVisibility(View.GONE);
            zhuozi.setVisibility(View.GONE);
        }
        ImageView imgbutton_two = (ImageView) findViewById(R.id.imgbutton_two);
        imgbutton_two.setOnClickListener(new FinishActvity(this));

        //餐位费
        seat_fee = (TextView) findViewById(R.id.seat_fee);

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
                //完成订单
                try {
                    Map<String, String> mapSure = MapUtilsSetParam.getMap(NewOrderDetailsOther.this);
                    mapSure.put("orderid", json.getJSONObject("data").getJSONObject("item").getString("id"));
                    mapSure.put("opp", "orderfinish");
                    NetUtils.netWorkByMethodPost(NewOrderDetailsOther.this, mapSure, IpConfig.URL, handler, LOADINGDATA_ORDERFINISH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.zhuozi:
                //取消发货
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
                    try {
                        json = new JSONObject(orderdetailsJson);
                        String userstr = json.getJSONObject("data").getJSONObject("item").getString("user");
//                         Log.d("NewOrderDetails", userstr);
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
                            youhuizhekou.setText("未打折");
                            yingshoujiner.setText("￥" + json.getJSONObject("data").getJSONObject("item").getString("price"));
                        } else {
                            double yhzk = Double.parseDouble(json.getJSONObject("data").getJSONObject("item").getString("sale")) * 10;
                            youhuizhekou.setText(yhzk + "折");
                            double ysje = Double.parseDouble(json.getJSONObject("data").getJSONObject("item").getString("price")) * Double.parseDouble(json.getJSONObject("data").getJSONObject("item").getString("sale"));
                            yingshoujiner.setText("￥" + ysje);
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
                        seat_fee.setText("￥" + json.getJSONObject("data").getJSONObject("item").getString("seat_fee"));

                        if ("1".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            //外卖
                        } else if ("2".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            //2.预约就餐订单
                            submit_pay.setText("确认就餐");
                            zhuozi.setText("确认退款");
                        } else if ("3".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            //2.现场订单
                            submit_pay.setText("返回开单");
                            zhuozi.setVisibility(View.GONE);
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
                        dispatchchoice = json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice");
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
                            Toast.makeText(NewOrderDetailsOther.this, data, Toast.LENGTH_SHORT).show();
                            //发送广播
                            EventBus.getDefault().post(new ShuaXinShuJu(1, "待付款"));
                            NewOrderDetailsOther.this.finish();
                        } else {
                            Toast.makeText(NewOrderDetailsOther.this, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mCache.remove("paystaus");
                    break;

                case LOADINGDATA_ORDERFINISH:
                    String tuikuanJson = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(tuikuanJson);
                        int staus = jsonObject.getInt("status");
                        String data = jsonObject.getString("data");
                        if (staus == 1) {
                            //完成订单
                            //发布广播 刷新数据
                            Toast.makeText(NewOrderDetailsOther.this, data, Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new ShuaXinShuJu(3, "待收货"));
                            NewOrderDetailsOther.this.finish();
                        } else {
                            //完成订单失败
                            Toast.makeText(NewOrderDetailsOther.this, data, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(NewOrderDetailsOther.this, jsp.getString("data"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NewOrderDetailsOther.this, jsp.getString("data"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    };
}
