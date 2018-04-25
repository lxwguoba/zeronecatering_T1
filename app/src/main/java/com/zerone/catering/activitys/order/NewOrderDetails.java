package com.zerone.catering.activitys.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.zerone.catering.action.BackAction;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.activitys.main.OrderMainActivity;
import com.zerone.catering.activitys.table.TableCtrlActivity;
import com.zerone.catering.adapter.ListViewOrderDetailsItemsAdapter;
import com.zerone.catering.constant.Constant;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.OrderInfo;
import com.zerone.catering.refreshandclose.shuaxin.ShuaXinShuJu;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.Utils;
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

public class NewOrderDetails extends BaseActvity implements View.OnClickListener {
    //获取订单详情的
    private static final int LOADINGDATA_ORDERDETAILS = 0;
    //结账
    private static final int LOADINGDATA_JIEZHANG = 1;

    //关闭订单
    private static final int LOADINGDATA_CLOSEORDER = 2;

    //存放goodsbean实体类的集合
    private List<OrderInfo.DataBean.ItemBean.GoodsBean> listBeen;
    //商品列表
    private ListView orderDetailsListView;
    //商品列表的适配器
    private ListViewOrderDetailsItemsAdapter listAdapter;
    //选择支付方式的checkbox的控件
    //微信支付
    private CheckBox checkweixinzhifu;
    //支付宝支付
    private CheckBox checkzhifubao;
    //现金支付
    private CheckBox checkxianjin;
    //刷卡支付
    private CheckBox checkshuaka;
    //缓存类
    private ACache mCache;
    //后台session值 每从后台获取数据的需要 session来判断。
    private String session;
    //分店实体类
    private Branch branch;
    //意图类 用来跳转和获取跳转所得数据
    private Intent intent;
    //文本显示控件 收货人
    private TextView shouhuoren;
    //文本显示控件 桌子名称
    private TextView tableName;
    //文本显示控件 订单编号
    private TextView dingdanbianhao;
    //文本显示控件 收货人电话
    private TextView shouhuorendianhua;
    //文本显示控件 收货地址
    private TextView shouhuodizhi;
    //文本显示控件  支付方式
    private TextView zhifufangshi;
    //文本显示控件 订单状态
    private TextView dingdanzhuantai;
    //文本显示控件 提成人员
    private TextView tichengrenyuan;
    //文本显示控件 下单状态
    private TextView xiadanzhuantai;
    //文本显示控件  下单时间
    private TextView xiadanshijian;
    //文本显示控件   买家留言
    private TextView maijialiuyan;
    //文本显示控件   优惠折扣
    private TextView youhuizhekou;
    //文本显示控件   订单金额
    private TextView dingdanjiner;
    //文本显示控件   应收金额
    private TextView yingshoujiner;

    //订单详情实体类
    private OrderInfo orderInfo;
    //订单类型  如现场订单 外卖  预约
    private String dispatchchoice;
    //获取到的对象 response返回来的
    private JSONObject json;
    //结账按钮
    private Button submit_pay;
    //桌子名称按钮  根据不同的id去识别不同的身份。即给button赋予不同的点击事件
    private Button zhuozi;
    //添加菜品按钮
    private Button addfood;

    //返回按钮
    private ImageView imgbutton_two;
    //餐位费的显示控件
    private TextView seat_fee;

    //用来点击此按钮 来改变餐位费
    private LinearLayout update_seatfee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworderdetailssix);
        //注册 ，判断是否存在  存在不用注册不存在 在注册
        intent = getIntent();
        mCache = ACache.get(this);
        branch = (Branch) mCache.getAsObject("branch");
        initData();
        initView();
        LoagingData();
        initListViewAdapter();
        setCheckBox();

    }


    private void LoagingData() {
        /**
         * 该方法是用来获取订单详情的
         */

        //orderid是订单id
        String orderid = intent.getStringExtra("orderid");
        Map<String, String> map = MapUtilsSetParam.getMap(NewOrderDetails.this);
        map.put("opp", "getorderdetail");
        //获取分店id
        map.put("branchid", branch.getId());
        map.put("orderid", orderid);
        NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handler, LOADINGDATA_ORDERDETAILS);
    }


    /**
     * view的初始话
     */
    private void initView() {
        //头部退出

        imgbutton_two = (ImageView) findViewById(R.id.imgbutton_two);
        imgbutton_two.setOnClickListener(new BackAction(this));
        //商品列表
        orderDetailsListView = (ListView) findViewById(R.id.orderDetailsListView);

        //四个checkox
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

        addfood = (Button) findViewById(R.id.addAndcancelFood);
        addfood.setOnClickListener(this);

        seat_fee = (TextView) findViewById(R.id.seat_fee);

        //--------打印按钮--------------------
        update_seatfee = (LinearLayout) findViewById(R.id.update_seatfee);
        //修改餐位费用的按钮
        update_seatfee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * 初始化订单详情界面
     */
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
            //提交结账的按钮
            case R.id.submit_pay:
                try {
                    if (!("3".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice")))) {
                        Map<String, String> mapSure = MapUtilsSetParam.getMap(NewOrderDetails.this);
                        mapSure.put("orderid", json.getJSONObject("data").getJSONObject("item").getString("id"));
                        mapSure.put("paymethod", json.getJSONObject("data").getJSONObject("item").getString("paytype"));
                        mapSure.put("branchid", branch.getId());
                        mapSure.put("opp", "confirmpay");
                        NetUtils.netWorkByMethodPost(NewOrderDetails.this, mapSure, IpConfig.URL, handler, LOADINGDATA_JIEZHANG);
                    } else {
                        String payStaus = mCache.getAsString("paystaus");
                        if (payStaus == null) {
                            Toast.makeText(NewOrderDetails.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Map<String, String> map = MapUtilsSetParam.getMap(NewOrderDetails.this);
                        //结账用：
                        map.put("opp", "jiezhang");
//                        Log.d("NewOrderDetails", "session==="+session);
                        if (TextUtils.isEmpty(json.getJSONObject("data").getJSONObject("tableinfo").getString("tableid"))) {
                            Toast.makeText(this, "请绑定桌子", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        map.put("tableid", json.getJSONObject("data").getJSONObject("tableinfo").getString("tableid"));
                        map.put("orderid", json.getJSONObject("data").getJSONObject("item").getString("id"));
                        map.put("paytype", payStaus);
                        NetUtils.netWorkByMethodPost(NewOrderDetails.this, map, IpConfig.URL, handler, LOADINGDATA_JIEZHANG);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.zhuozi:
                try {
                    String dispatchchoice = json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice");
                    if (!("3".equals(dispatchchoice))) {
                        //外卖或者是预约  关闭订单
                        Map<String, String> map = MapUtilsSetParam.getMap(NewOrderDetails.this);
                        map.put("orderid", json.getJSONObject("data").getJSONObject("item").getString("id"));
                        map.put("tableid", "86");
                        map.put("branchid", branch.getId());
                        map.put("opp", "orderclose");
                        NetUtils.netWorkByMethodPost(NewOrderDetails.this, map, IpConfig.URL, handler, LOADINGDATA_CLOSEORDER);
                    } else {
                        //现场订单
//                    Log.i("URL", "onClick: "+json);
                        String string = json.getJSONObject("data").getString("tableinfo");

                        if (json.getJSONObject("data").getJSONObject("tableinfo").getString("tableid") == null || "false".equals(string)) {
                            Intent intent = new Intent(NewOrderDetails.this, TableCtrlActivity.class);
                            intent.putExtra("theorderid", json.getJSONObject("data").getJSONObject("item").getString("id"));
                            startActivity(intent);
                            NewOrderDetails.this.finish();
                        } else {
                            //换桌子   要通知 contractivity关闭
                            Intent intent = new Intent(NewOrderDetails.this, TableCtrlActivity.class);
                            intent.putExtra("theorderid", json.getJSONObject("data").getJSONObject("item").getString("id"));
                            startActivity(intent);
                            NewOrderDetails.this.finish();
//                        发送广播
                            EventBus.getDefault().post(new ShuaXinShuJu(2, "待付款-closepage"));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.addAndcancelFood:
                //添加菜品减菜
                Intent intent = new Intent(NewOrderDetails.this, OrderMainActivity.class);
                intent.putExtra("actionId", Constant.ACTIONID_PRODUCTINFO);
//                Log.i("TAG", "onClick: "+json);
                try {
                    intent.putExtra("orderid", json.getJSONObject("data").getJSONObject("item").getString("id"));
                    intent.putExtra("memberid", json.getJSONObject("data").getJSONObject("orderownner").getString("id"));
                    intent.putExtra("tablename", json.getJSONObject("data").getJSONObject("tableinfo").getString("tablename"));
                    intent.putExtra("minconsume", json.getJSONObject("data").getJSONObject("tableinfo").getString("minconsume"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                //发送广播
                EventBus.getDefault().post(new ShuaXinShuJu(2, "关闭页面"));
                NewOrderDetails.this.finish();
                break;

        }
    }

    /**
     * 这个是选择支付方式的方法
     * 将checkbox多选按钮修改为单选按钮   并用mCache这个缓存来保存所选的支付方式
     */
    private void setCheckBox() {
        //微信支付
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

        //支付宝支付
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

        //现金支付
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

        //刷卡支付
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
        private String shAddress = "";
        private String shrPhone = "";
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //获取订单详情 后台返回来的数据
                case LOADINGDATA_ORDERDETAILS:
                    //后台返回来的json字符串
                    String orderdetailsJson = (String) msg.obj;
                    Log.i("TAG", "orderjson7777=" + orderdetailsJson);
                    try {
                        json = new JSONObject(orderdetailsJson);

//                         Log.i("TAG",orderdetailsJson);
                        String userstr = json.getJSONObject("data").getJSONObject("item").getString("user");
//                                  Log.d("NewOrderDetails", userstr);
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

                        String datenum = json.getJSONObject("data").getJSONObject("tableinfo").getString("datenum");

                        if (datenum != null) {
                            Utils.getACache(NewOrderDetails.this).put("people", datenum);
                        } else {
                            Utils.getACache(NewOrderDetails.this).put("people", "0");
                        }

                        seat_fee.setText("￥" + json.getJSONObject("data").getJSONObject("item").getString("seat_fee"));

                        if ("1".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            //外卖
                            addfood.setVisibility(View.GONE);
                            submit_pay.setText("确认付款");
                            zhuozi.setText("关闭订单");
                        } else if ("2".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            //2.预约就餐订单
                            addfood.setVisibility(View.GONE);
                            submit_pay.setText("确认付款");
                            zhuozi.setText("关闭订单");
                        } else if ("3".equals(json.getJSONObject("data").getJSONObject("item").getString("dispatchchoice"))) {
                            //2.现场订单
//                                Log.d("NewOrderDetails", json.getJSONObject("data").getJSONObject("tableinfo").getString("tableid"));
                            if (json.getJSONObject("data").getJSONObject("tableinfo").getString("tableid") == null) {
                                zhuozi.setText("绑定桌子");
                            } else {
                                zhuozi.setText("换桌子");
                            }
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

                //结账后台返回来的数据
                case LOADINGDATA_JIEZHANG:
                    String jiezhangJson = (String) msg.obj;
                    try {
                        JSONObject json = new JSONObject(jiezhangJson);
                        String status = json.getString("status");
                        String data = (String) json.getString("data");
                        if ("1".equals(status)) {
                            Toast.makeText(NewOrderDetails.this, data, Toast.LENGTH_SHORT).show();
                            //结账成功后 发送广播 用来刷新订单管理页面的数据
                            EventBus.getDefault().post(new ShuaXinShuJu(1, "待付款"));
                            NewOrderDetails.this.finish();
                        } else {
//                             Log.d("NewOrderDetails", "json:" + json);
                            Toast.makeText(NewOrderDetails.this, data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //清楚所选的支付方式
                    mCache.remove("paystaus");
                    break;
                case LOADINGDATA_CLOSEORDER:
                    //关闭订单返回来的数据
                    String closeJson = (String) msg.obj;
//                     Log.i("URL","close:::="+closeJson);
                    break;

                //打印机打印返回来的数据 现在屏蔽了
                case 200001:
                    String printjson = (String) msg.obj;
                    try {
                        JSONObject jsp = new JSONObject(printjson);
                        if (jsp.getInt("status") == 1) {
                            Toast.makeText(NewOrderDetails.this, jsp.getString("data"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(NewOrderDetails.this, jsp.getString("data"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);//解除订阅
    }

}
