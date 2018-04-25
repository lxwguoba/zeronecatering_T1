package com.zerone.catering.activitys.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.zerone.catering.BuildConfig;
import com.zerone.catering.R;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.activitys.order.NewOrderDetails;
import com.zerone.catering.activitys.order.OrderControl;
import com.zerone.catering.activitys.table.TableCtrlActivity;
import com.zerone.catering.adapter.FoodItemsAdapter;
import com.zerone.catering.adapter.GridViewAdapter;
import com.zerone.catering.adapter.ListViewOrderGuanLiAdapter;
import com.zerone.catering.adapter.ListViewOrderInfoAdapter;
import com.zerone.catering.adapter.TableAdapter;
import com.zerone.catering.constant.Constant;
import com.zerone.catering.constant.ConstantS;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.db.impl.BranchDao;
import com.zerone.catering.db.impl.CatgoryGoodsDao;
import com.zerone.catering.db.impl.GoodDetailsDao;
import com.zerone.catering.db.impl.WorkerTabeDao;
import com.zerone.catering.dialog.DODialog;
import com.zerone.catering.dialog.option_dialog.OptionsActivity;
import com.zerone.catering.domain.CategoryGoods;
import com.zerone.catering.domain.FoodItemsBean;
import com.zerone.catering.domain.FoodOrderInfoBean;
import com.zerone.catering.domain.FoodXi;
import com.zerone.catering.domain.GoodsDetails;
import com.zerone.catering.domain.GoodsUp;
import com.zerone.catering.domain.HuiYuanInfoBean;
import com.zerone.catering.domain.Table;
import com.zerone.catering.domain.Worker;
import com.zerone.catering.refreshandclose.shuaxin.ShuaXinShuJu;
import com.zerone.catering.utils.AnimationUtil;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.OutActivityUtils;
import com.zerone.catering.utils.SetGridViewHeight;
import com.zerone.catering.utils.SetRecyclerRow;
import com.zerone.catering.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/7/31.
 */

public class OrderMainActivity extends BaseActvity implements View.OnClickListener {
    //显示隐藏控件的 int
    private static final int HIDDEN = 0;
    //显示标题控件的 int
    private static final int SHOW = 1;
    //展示菜品
    private static final int SHOWFOODDETAILS = 2;
    //展示菜品
    private static final int SHOWFOODDETAILS01 = 201;
    //展示已点菜品信息
    private static final int SHOWFOODINFO = 3;
    //清空列表
    private static final int CLEAR = 4;
    //管理员列表
    private static final int GUANLI = 5;
    //管理员列表
    private static final int SERCHFOODXI = 6;
    //会员处理
    private static final int HUIYUANCHULI = 7;
    //提交订单
    private static final int LOADINGDATA_ORDERSUBMIT = 8;
    //订单详情页面跳转过来后修改了订单在提交时的值
    private static final int LOADINGDATA_ORDERSUBMITUP = 88;
    //获取员工
    private static final int LOADINGDATA_GETWORKER = 9;
    //本店散客
    private static final int LOADINGDATA_SNAKE = 10;
    //提交订单有tableid
    private static final int LOADINGDATA_ORDERSUBMITTABLE = 11;
    //修改数量
    private static final int LOADINGDATA_UPDATACOUNT = 12;
    //获取二为码
    private static final int LOADINGDATA_GETQRCODE = 13;

    //获取订单中的列表数据goods
    private static final int GETORDERLIST = 14;

    //缓存获取菜品
    private static final int SHOWFOODDETAILSCASH = 15;

    //获取菜品库存
    private static final int LOADINGDATA_GETCOUNT = 16;

    //本店散客
    private static final int LOADINGDATA_CHECKGOODS = 17;

    //用来接收点击vip事件回来的操作
    private static final int LOADINGDATA_GETVIPGOODSPRICE = 18;
    //用来接收点击vip事件回来的操作
    private static final int LOADINGDATA_GETVIP = 19;
    //响应码
    private static final int ACTIVITYREQUESTCODE = 10000;
    //有规格时的返回：
    private static final int OPTIONSID = 20;
    private GridView gridView;
    private List<String> list;
    //管理员测试数据
    private List<Worker> guanLiList;
    //集合存放的是菜品详情实体类
    private List<FoodItemsBean.GoodslistBean> foodChildList;
    //用来展示菜品
    private RecyclerView recyclerView;
    //用来展示菜品 的适配器
    private FoodItemsAdapter foodAdapter;
    private ListView listview_orderinfo;
    private ListViewOrderInfoAdapter adapter_orderInfo;
    private List<FoodOrderInfoBean> foodOrderInfo;
    private RelativeLayout relativeOne;
    private RelativeLayout relativeTwo;
    private ImageView imgbutton_two;
    private TextView titleName;
    private ImageView imgbutton_sanjiaoxing;
    private TextView username;
    private ImageView imgbutton_one;
    private ImageView img_canwei;
    private ImageView img_diancan;
    private ImageView img_dingdan;
    private ImageView out_activity;
    private Button clear;
    private TextView money;
    private GoogleApiClient client;
    private GridViewAdapter gridViewAdapter;
    private View guanliView;
    private ListView guanliListView;
    private PopupWindow popupWindowGuanli;
    private TextView huiyuanName;
    private View huiYuanView;
    private ImageView img_return;
    private ImageView close_pop;
    private Button huiyuan;
    private Button sanke;
    private PopupWindow popupWindowHuiYuan;
    //访问网络数据获取菜（dish）
    private List<FoodXi> dishList;
    private Button sceneCustomer;
    private LinearLayout linerLayout;
    private CircleImageView headImg;
    private Button submit_order;
    private ListViewOrderGuanLiAdapter guanLiAdapter;
    private Intent getDataIntent;
    private String tableid;
    private String peopleNum;
    private TextView tableName;
    private String tablename;
    private ImageView img_qrcode;
    private String orderId;
    //动作id 是从哪个页面来的
    private int actionId;
    private Object orderlist;
    private View adview;
    private AlertDialog dialog;
    private TextView title;
    private View adview02;
    private AlertDialog dialog02;

    private BranchDao branchDao;
    private List<Integer> listcount;
    private Map<String, Integer> mapcount;

    private static final String TAG = "OrderMainActivity";
    private String tablename1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        setContentView(R.layout.activity_neworder_page);
        //注册 ，判断是否存在  存在不用注册不存在 在注册   eventbus框架使用
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
       //一进入就把会员id清零 还有接待员清零  还有接待员 无效
        Utils.getACache(OrderMainActivity.this).remove("memberid");
        Utils.getACache(OrderMainActivity.this).remove("workerid");
        getDataIntent = getIntent();
        actionId = getDataIntent.getIntExtra("actionId", Constant.ACTIONID_DEFAULT);
        initData();
        if (actionId == Constant.ACTIONID_DEFAULT) {

        } else if (actionId == Constant.ACTIONID_TABLEGOORDER) {
            //桌子id
            tableid = getDataIntent.getStringExtra("tableid");
            //桌子人数
            peopleNum = getDataIntent.getStringExtra("datenum");
            //桌子名称
            tablename = getDataIntent.getStringExtra("tablename");
//            //桌子的最低消费
        } else if (actionId == Constant.ACTIONID_PRODUCTINFO) {
            orderId = getDataIntent.getStringExtra("orderid");
            String memberid = getDataIntent.getStringExtra("memberid");
            tablename1 = getDataIntent.getStringExtra("tablename");
            //桌子的最低消费这个是订单详情里返回的最低小
            Utils.getACache(OrderMainActivity.this).put("memberid", memberid);

            //获取订单列表
            getorderlist();
        }

        try {
            LoadingData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();
        gridviewaction();
        recyclerViewAction();
        listViewActivity();
        //设置默认为第一个菜系的值
    }

    /**
     * 获取网络数据
     */
    private void LoadingData() throws Exception {

        String foodjson = Utils.getACache(OrderMainActivity.this).getAsString("foodjson");
        if (foodjson != null && foodjson.length() > 0) {
            //  原来的数据
            Message message = new Message();
            message.obj = foodjson;
            message.what = SERCHFOODXI;
            handler.sendMessage(message);
        } else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("act", "module");
            map.put("name", "bj_qmxk");
            map.put("do", "app_api");
            map.put("opp", "getcategory");
            map.put("branchid", Utils.getBranch(OrderMainActivity.this).getId());
            map.put("session", Utils.getSeesion(this));
            NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handler, SERCHFOODXI);
        }
        //获取散客的信息
        Map<String, String> mapSanke = new HashMap<String, String>();
        mapSanke.put("act", "module");
        mapSanke.put("name", "bj_qmxk");
        mapSanke.put("do", "app_api");
        mapSanke.put("opp", "getsanke");
        mapSanke.put("session", Utils.getSeesion(this));
        mapSanke.put("branchid", Utils.getBranch(OrderMainActivity.this).getId());
        NetUtils.netWorkByMethodPost(OrderMainActivity.this, mapSanke, IpConfig.URL, handler, LOADINGDATA_SNAKE);
    }

    /**
     * listview的点击事件
     */
    private void listViewActivity() {
        //这个是管理人员的点击事件
        guanliListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = new Message();
                message.what = GUANLI;
                message.obj = position;
                handler.sendMessage(message);
            }
        });

        //这个是左边的客户列表的点击事件
        listview_orderinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message msg = new Message();
                msg.what = LOADINGDATA_UPDATACOUNT;
                msg.obj = position;
                handler.sendMessage(msg);
            }
        });

    }

    /**
     * recycl的点击事件
     * 这个是菜品的点击事件
     */
    private void recyclerViewAction() {
        //11110
        foodAdapter.setOnItemClickListener(new TableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String hasoption = foodChildList.get(position).getHasoption();
                String memberid = Utils.getACache(OrderMainActivity.this).getAsString("memberid");
                 String sankeid =  Utils.getACache(OrderMainActivity.this).getAsString("sanke");
                if (!(actionId == Constant.ACTIONID_PRODUCTINFO)) {
                    //这个是点餐时 页面不是从详情页跳转过来的 所以 要判读是否有memberid
                    if (tableid == null) {
                        Toast.makeText(OrderMainActivity.this, "请先选择桌子", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (memberid == null&& sankeid==null) {
                        Toast.makeText(OrderMainActivity.this, "请选择会员", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //有规格要选规格无
                if ("1".equals(hasoption)) {
                    //测试打开弹出规格选择页面  1027
                    Intent intent = new Intent(OrderMainActivity.this, OptionsActivity.class);
                    intent.putExtra("memberid", memberid);
                    intent.putExtra("goodsid", foodChildList.get(position).getGoodsid());
                    Utils.getACache(OrderMainActivity.this).put("positionp", position + "");
                    startActivityForResult(intent, ConstantS.OPTIONSRESULT);
                } else {
                    //这个是无规格的
                    Message message = new Message();
                    message.what = SHOWFOODINFO;
                    message.obj = position;
                    handler.sendMessage(message);
                }
            }
        });
    }

    /**
     * gridview 的点击事件处理
     * 这个是菜品分类的点击事件
     */
    private void gridviewaction() {


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // gridView中点击 item为选中状态(背景颜色)
                gridViewAdapter.setSelectPosition(position);
                gridViewAdapter.notifyDataSetChanged();
                String asString = Utils.getACache(OrderMainActivity.this).getAsString(position + "");


                GoodDetailsDao gdd = new GoodDetailsDao(OrderMainActivity.this);
                try {
                    List<FoodItemsBean.GoodslistBean> foodDetails = gdd.getFoodDetailsDB(dishList.get(position).getId());


                    if (foodDetails == null) {
                        Utils.getACache(OrderMainActivity.this).put("pos", position + "");
                        getFood(position);
                        Utils.getACache(OrderMainActivity.this).put("categoryid", dishList.get(position).getId());
                    } else {
                        foodChildList.clear();
                        mapcount.clear();
                        for (int i = 0; i < foodDetails.size(); i++) {
                            foodChildList.add(foodDetails.get(i));
                            //这个是获取库存的
                            Map<String, String> getCountMap = new HashMap<String, String>();
                            getCountMap.put("act", "module");
                            getCountMap.put("name", "bj_qmxk");
                            getCountMap.put("do", "app_api");
                            getCountMap.put("opp", "getGoodsStock");
                            getCountMap.put("session", Utils.getSeesion(OrderMainActivity.this));
                            getCountMap.put("goodsid", foodDetails.get(i).getGoodsid());
                            NetUtils.netWorkerGoodsid(OrderMainActivity.this, getCountMap, IpConfig.URL, handler, LOADINGDATA_GETCOUNT, foodDetails.get(i).getGoodsid());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                foodAdapter.notifyDataSetChanged();

            }
        });
    }

    private void initData() {

        dishList = new ArrayList<>();
        foodChildList = new ArrayList<>();
        listcount = new ArrayList<>();
        mapcount = new HashMap<>();
        foodOrderInfo = new ArrayList<>();
        WorkerTabeDao dao =new WorkerTabeDao(this);
        try {
         guanLiList= dao.getWorker();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        //加载进度条---------------------
        adview02 = LayoutInflater.from(this).inflate(R.layout.acticity_progressbar, null);
        title = (TextView) adview02.findViewById(R.id.titile);
        title.setText("加载菜品中");
        dialog02 = DODialog.getDialog(OrderMainActivity.this, adview02);
        adview = LayoutInflater.from(this).inflate(R.layout.acticity_progressbar, null);
        dialog = DODialog.getDialog(OrderMainActivity.this, adview);
        //管理员
        guanliView = LayoutInflater.from(this).inflate(R.layout.activity_neworder_guanli, null);
        guanliListView = (ListView) guanliView.findViewById(R.id.listview_guanli);
        guanLiAdapter = new ListViewOrderGuanLiAdapter(this, guanLiList);
        guanliListView.setAdapter(guanLiAdapter);
        //会员管理
        huiYuanView = LayoutInflater.from(this).inflate(R.layout.activity_neworder_custompop, null);
        img_qrcode = (ImageView) huiYuanView.findViewById(R.id.img_qrcode);
        img_return = (ImageView) huiYuanView.findViewById(R.id.img_return);
        img_return.setOnClickListener(this);
        close_pop = (ImageView) huiYuanView.findViewById(R.id.close_pop);
        close_pop.setOnClickListener(this);
        huiyuan = (Button) huiYuanView.findViewById(R.id.huiyuan);
        huiyuan.setOnClickListener(this);
        sanke = (Button) huiYuanView.findViewById(R.id.sanke);
        sanke.setOnClickListener(this);
        sceneCustomer = (Button) huiYuanView.findViewById(R.id.sceneCustomer);
        sceneCustomer.setOnClickListener(this);
        //会员管理=======================================================

        //-----------------grdiview 这个是菜品分类
        gridView = (GridView) findViewById(R.id.gridview);
        gridViewAdapter = new GridViewAdapter(dishList, OrderMainActivity.this);
        gridView.setAdapter(gridViewAdapter);
        //第一个设置为默认选中
        gridViewAdapter.setSelectPosition(0);
        //设置gridview的高度  最多不大于三行  165dp  小于三行则展示items的高度
        SetGridViewHeight.setListViewHeightBasedOnChildren(gridView);
        gridView.setSelection(0);
        //-----------------grdiview
        //recycle的使用 这个是菜品详情展示
        recyclerView = (RecyclerView) findViewById(R.id.foodRcyeler);
        GridLayoutManager manager = new GridLayoutManager(OrderMainActivity.this, 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SetRecyclerRow(32, 30));
        foodAdapter = new FoodItemsAdapter(OrderMainActivity.this, foodChildList, mapcount, handler);
        recyclerView.setAdapter(foodAdapter);
        //------------------------------------------
        //这个是客户单的
        listview_orderinfo = (ListView) findViewById(R.id.listview_orderinfo);
        adapter_orderInfo = new ListViewOrderInfoAdapter(OrderMainActivity.this, foodOrderInfo);
        listview_orderinfo.setAdapter(adapter_orderInfo);
        if (adapter_orderInfo.getCount() > 0) {
            listview_orderinfo.setSelection(adapter_orderInfo.getCount() - 1);
        }
        //------------------------------------------
        //两大标题relayout 控件的显示和隐藏
        //这个是三大按钮的父控件
        relativeOne = (RelativeLayout) findViewById(R.id.three_control);
        //这个是按钮所展示的父控件
        relativeTwo = (RelativeLayout) findViewById(R.id.order_MainTitle);
        //点击显示控件的控制按钮

        imgbutton_two = (ImageView) findViewById(R.id.imgbutton_two);
        imgbutton_two.setOnClickListener(this);
        //点击显示控件的控制按钮
        imgbutton_one = (ImageView) findViewById(R.id.imgbutton_one);
        imgbutton_one.setOnClickListener(this);
        //标题的名称
        titleName = (TextView) findViewById(R.id.titleName);
        //三角形按钮
        imgbutton_sanjiaoxing = (ImageView) findViewById(R.id.imgbutton_sanjiaoxing);
        imgbutton_sanjiaoxing.setOnClickListener(this);
        if (actionId == Constant.ACTIONID_PRODUCTINFO) {
            imgbutton_two.setVisibility(View.GONE);
            imgbutton_one.setVisibility(View.GONE);
            titleName.setText("编辑订单");
        }

        //用户名称
        username = (TextView) findViewById(R.id.username);
        img_canwei = (ImageView) findViewById(R.id.img_canwei);
        img_canwei.setOnClickListener(this);
        img_diancan = (ImageView) findViewById(R.id.img_diancan);
        img_dingdan = (ImageView) findViewById(R.id.img_dingdan);
        img_dingdan.setOnClickListener(this);
        out_activity = (ImageView) findViewById(R.id.out_activity);
        out_activity.setOnClickListener(this);
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);
        //菜品的详情列表的总计钱数
        money = (TextView) findViewById(R.id.money);
        //初始化金额为0
        money.setText("0.00");

        //------20----
        headImg = (CircleImageView) findViewById(R.id.huiyuan_headimg);
        headImg.setOnClickListener(this);
//        //打开下拉
        linerLayout = (LinearLayout) findViewById(R.id.linerLayout);
        linerLayout.setOnClickListener(this);

        //提交订单
        submit_order = (Button) findViewById(R.id.submit_order);
        submit_order.setOnClickListener(this);
        if (actionId == Constant.ACTIONID_PRODUCTINFO) {
            submit_order.setText("修改订单");
        }
        //tableName的设置
        tableName = (TextView) findViewById(R.id.tableName);
        if (actionId == Constant.ACTIONID_PRODUCTINFO) {
            //订单详情过来的所以有桌子的1108
                tableName.setText(tablename1);
        } else {
            tableName.setText(tablename);
        }

        //默认一个接待员
        if (guanLiList.size() > 0) {
            username.setText(guanLiList.get(0).getName());
            Utils.getACache(OrderMainActivity.this).put("workerid", guanLiList.get(0).getWorkerid());
        }
    }

    //每一个控件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //标题的显示和隐藏
            case R.id.imgbutton_two:
                Message message01 = new Message();
                message01.what = HIDDEN;
                handler.sendMessage(message01);
                break;
            case R.id.imgbutton_one:
                Message message02 = new Message();
                message02.what = SHOW;
                handler.sendMessage(message02);
                break;
            case R.id.out_activity:
                OutActivityUtils.out(OrderMainActivity.this, "退出收银终端", "您确定要退出吗", Utils.getACache(OrderMainActivity.this),handler);
                break;
            //清空列表
            case R.id.clear:
                Message message03 = new Message();
                message03.what = CLEAR;
                handler.sendMessage(message03);
                break;
            //会员管理的选择
            case R.id.huiyuan_headimg:
                //点了一次以后不能再点了 除非是返回来了
                headImg.setClickable(false);
//                Log.d("OrderMainActivity", "禁止74888了哦");
                if (TextUtils.isEmpty(Utils.getACache(OrderMainActivity.this).getAsString("workerid"))) {
                    Toast.makeText(OrderMainActivity.this, "本地散客不存在或者没有接待员", Toast.LENGTH_SHORT).show();
                    headImg.setClickable(true);
                    return;
                }
                //获取二维码
                Map<String, String> sankeMap = new HashMap<String, String>();
                sankeMap.put("act", "module");
                sankeMap.put("name", "bj_qmxk");
                sankeMap.put("do", "app_api");
                sankeMap.put("opp", "getqrcode");
                sankeMap.put("session", Utils.getSeesion(OrderMainActivity.this));
                sankeMap.put("workerid", Utils.getACache(OrderMainActivity.this).getAsString("workerid"));
//                String url = IpConfig.URL+"act=module&name=bj_qmxk&do=app_api&opp=getqrcode&session=" + Utils.getSeesion(OrderMainActivity.this) + "&workerid=" + Utils.getACache(OrderMainActivity.this).getAsString("workerid");
//                Log.d(TAG,"8888888888888888888888888="+ url);
                NetUtils.netWorkByMethodPost(OrderMainActivity.this, sankeMap, IpConfig.URL, handler, LOADINGDATA_GETQRCODE);

                break;
            //关闭pop
            case R.id.img_return:
                popupWindowHuiYuan.dismiss();
                headImg.setClickable(true);
                break;
            //关闭pop
            case R.id.close_pop:
                headImg.setClickable(true);
                popupWindowHuiYuan.dismiss();
                break;
            //会员的操作
            case R.id.huiyuan:
                huiyuan.setTextColor(Color.rgb(255, 126, 56));
                sanke.setTextColor(Color.rgb(102, 102, 102));
                Intent intent = new Intent(OrderMainActivity.this, HuiYuanInfo.class);
                intent.putExtra("status", 0);
                startActivityForResult(intent, ACTIVITYREQUESTCODE);
                popupWindowHuiYuan.dismiss();
                headImg.setClickable(true);
                break;
            //散客的操作
            case R.id.sanke:
                sanke.setTextColor(Color.rgb(255, 126, 56));
                huiyuan.setTextColor(Color.rgb(102, 102, 102));
                Map<String, String> mapSanke = new HashMap<String, String>();
                mapSanke.put("act", "module");
                mapSanke.put("name", "bj_qmxk");
                mapSanke.put("do", "app_api");
                mapSanke.put("opp", "getsanke");
                mapSanke.put("session", Utils.getSeesion(OrderMainActivity.this));
                mapSanke.put("branchid", Utils.getBranch(OrderMainActivity.this).getId());
                NetUtils.netWorkByMethodPost(OrderMainActivity.this, mapSanke, IpConfig.URL, handler, LOADINGDATA_SNAKE);
                break;
            //现场顾客
            case R.id.sceneCustomer:
                Intent intent02 = new Intent(OrderMainActivity.this, HuiYuanInfo.class);
                intent02.putExtra("status", 1);
                startActivityForResult(intent02, ACTIVITYREQUESTCODE);
                popupWindowHuiYuan.dismiss();
                break;
            //管理员列表展示
            case R.id.linerLayout:
                if (tableid == null) {
                    Toast.makeText(OrderMainActivity.this, "请先开台", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (guanLiList.size() > 0) {
                    showPopGuanLi();
                } else {
                    Toast.makeText(OrderMainActivity.this, "暂无接待员", Toast.LENGTH_SHORT).show();
                }

                break;
            //跳转到订单管理
            case R.id.img_dingdan:
                Intent intent1 = new Intent(OrderMainActivity.this, OrderControl.class);
                startActivity(intent1);
                break;

            case R.id.img_canwei:
                Intent intent2 = new Intent(OrderMainActivity.this, TableCtrlActivity.class);
                startActivity(intent2);
                break;

            case R.id.submit_order:
                //100
                List<GoodsUp> goodsUpList = new ArrayList<>();
                if (foodOrderInfo.size() == 0) {
                    Toast.makeText(OrderMainActivity.this, "请选择菜", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (foodOrderInfo != null) {
                    for (int i = 0; i < foodOrderInfo.size(); i++) {
                        GoodsUp gu = new GoodsUp();
                        gu.setGoodsid(foodOrderInfo.get(i).getFoodId());
                        gu.setOptionid(null);
                        gu.setNumber(foodOrderInfo.get(i).getFoodCount());
                        goodsUpList.add(gu);
                    }
                }
                //把货物封装成jsonarray数组集合
                String listString = JSON.toJSONString(goodsUpList);
                String value = null;
                try {
                    value = new String(listString.getBytes(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String, String> makeOrdermMap = new HashMap<String, String>();
                makeOrdermMap.put("act", "module");
                makeOrdermMap.put("name", "bj_qmxk");
                makeOrdermMap.put("do", "app_api");
                makeOrdermMap.put("opp", "checkSubmitGoodsStock");
                makeOrdermMap.put("goodslist", value);
                makeOrdermMap.put("session", Utils.getSeesion(OrderMainActivity.this));
                if (orderId != null) {
                    makeOrdermMap.put("orderid", orderId);
                }
                NetUtils.netWorkByMethodPost(OrderMainActivity.this, makeOrdermMap, IpConfig.URL, handler, LOADINGDATA_CHECKGOODS);
                break;
        }
    }

    /**
     * 可以提交
     */
    private void submitIsOk() {

        if (actionId == Constant.ACTIONID_PRODUCTINFO) {
            //从订单详情跳转到点餐页面
            //对货物的封装
            //是否达到最低消费
            double selectMoney = Double.parseDouble(money.getText().toString());
            List<GoodsUp> goodsUpList = new ArrayList<>();
            if (foodOrderInfo.size() == 0) {
                Toast.makeText(OrderMainActivity.this, "请选择菜", Toast.LENGTH_SHORT).show();
                return;
            }
            if (foodOrderInfo != null) {
                for (int i = 0; i < foodOrderInfo.size(); i++) {
                    GoodsUp gu = new GoodsUp();
                    gu.setGoodsid(foodOrderInfo.get(i).getFoodId());
                    gu.setOptionid(foodOrderInfo.get(i).getOptionid());
                    gu.setNumber(foodOrderInfo.get(i).getFoodCount());
                    goodsUpList.add(gu);
                }
            }
            //把货物封装成jsonarray数组集合
            String listString = JSON.toJSONString(goodsUpList);
            String value = null;
            try {
                value = new String(listString.getBytes(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }



            Map<String, String> makeOrdermMap = MapUtilsSetParam.getMap(OrderMainActivity.this);
            makeOrdermMap.put("opp", "submiteditgoods");
            makeOrdermMap.put("goodslist", value);
            makeOrdermMap.put("orderid", orderId);
            DODialog.showDialog(dialog, OrderMainActivity.this);
            NetUtils.netWorkByMethodPost(this, makeOrdermMap, IpConfig.URL, handler, LOADINGDATA_ORDERSUBMITUP);
            foodOrderInfo.clear();
            adapter_orderInfo.notifyDataSetChanged();
            headImg.setImageResource(R.mipmap.photo_icon);
        } else {
            Map<String, String> makeOrdermMap = MapUtilsSetParam.getMap(OrderMainActivity.this);
            Table table = (Table) Utils.getACache(OrderMainActivity.this).getAsObject("table");
            String workerid = Utils.getACache(OrderMainActivity.this).getAsString("workerid");
            HuiYuanInfoBean.MemberlistBean huiyuaninfobean = (HuiYuanInfoBean.MemberlistBean) Utils.getACache(OrderMainActivity.this).getAsObject("huiyuaninfo");
            if (workerid == null) {
                Toast.makeText(OrderMainActivity.this, "请选接待员", Toast.LENGTH_SHORT).show();
            } else {
                makeOrdermMap.put("workerid", workerid);
            }
            //对货物的封装
            List<GoodsUp> goodsUpList = new ArrayList<>();

            if (foodOrderInfo.size() == 0) {
                Toast.makeText(OrderMainActivity.this, "请选择菜", Toast.LENGTH_SHORT).show();
                return;
            }
            if (foodOrderInfo != null) {
                for (int i = 0; i < foodOrderInfo.size(); i++) {
                    GoodsUp gu = new GoodsUp();
                    gu.setGoodsid(foodOrderInfo.get(i).getFoodId());
                    gu.setOptionid(foodOrderInfo.get(i).getOptionid());
                    gu.setNumber(foodOrderInfo.get(i).getFoodCount());
                    goodsUpList.add(gu);
                }
            }
            //把货物封装成jsonarray数组集合
            String listString = JSON.toJSONString(goodsUpList);
            String value = null;
            try {
                value = new String(listString.getBytes(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            makeOrdermMap.put("opp", "ordersubmit");
            makeOrdermMap.put("branchid", Utils.getBranch(OrderMainActivity.this).getId());
            if (huiyuaninfobean == null) {
                String id = Utils.getACache(OrderMainActivity.this).getAsString("sanke");
                makeOrdermMap.put("memberid", id);
                Utils.getACache(OrderMainActivity.this).put("memberid", id);
            } else {
                makeOrdermMap.put("memberid", huiyuaninfobean.getId());
            }

            makeOrdermMap.put("goodslist", value);

            if (tableid == null) {
                //数据完整后再启动对话框
                DODialog.showDialog(dialog, OrderMainActivity.this);
                NetUtils.netWorkByMethodPost(this, makeOrdermMap, IpConfig.URL, handler, LOADINGDATA_ORDERSUBMIT);
            } else {

                double selectMoney = Double.parseDouble(money.getText().toString());
                //需要新的接口
                makeOrdermMap.put("tableid", tableid);
                makeOrdermMap.put("datenum", peopleNum);
                //数据完整后再启动对话框
                DODialog.showDialog(dialog, OrderMainActivity.this);
                NetUtils.netWorkByMethodPost(this, makeOrdermMap, IpConfig.URL, handler, LOADINGDATA_ORDERSUBMITTABLE);
            }
            foodOrderInfo.clear();
            adapter_orderInfo.notifyDataSetChanged();
            headImg.setImageResource(R.mipmap.photo_icon);
            tableName.setText("请选桌子");
            Utils.getACache(OrderMainActivity.this).remove("workerid");
        }

    }

    //用来刷新控件和获取网络数据的处理
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //这个是获取单个菜品的价格和会员价格信息
                case LOADINGDATA_GETVIPGOODSPRICE:
                    String goodsId = (String) msg.obj;

                    Map<String, String> map = MapUtilsSetParam.getMap(OrderMainActivity.this);
                    map.put("opp", "getMembersLevelPrice");
                    map.put("goodsid", goodsId);
                    NetUtils.netWorkByMethodPost(OrderMainActivity.this, map, IpConfig.URL, handler, LOADINGDATA_GETVIP);


                    break;
                //这个是获取到了菜品信息返回的response
                case LOADINGDATA_GETVIP:
                    String response = (String) msg.obj;
                    Intent intent01 = new Intent(OrderMainActivity.this, VIPActvity.class);
                    intent01.putExtra("response", response);
                    startActivity(intent01);
                    break;

                case HIDDEN:
                    relativeOne.setAnimation(AnimationUtil.moveToViewBottom(600));
                    img_canwei.setAnimation(AnimationUtil.moveToViewBottom(900));
                    img_diancan.setAnimation(AnimationUtil.moveToViewBottom(1100));
                    img_dingdan.setAnimation(AnimationUtil.moveToViewBottom(1400));
                    out_activity.setAnimation(AnimationUtil.moveToViewBottom(1600));
                    relativeTwo.setVisibility(View.GONE);
                    relativeOne.setVisibility(View.VISIBLE);
                    break;
                case SHOW:
                    relativeTwo.setAnimation(AnimationUtil.moveToViewBottom(600));
                    relativeTwo.setVisibility(View.VISIBLE);
                    relativeOne.setVisibility(View.GONE);
                    break;
                case SHOWFOODDETAILS:
                    //这个是获取分类下的菜品
                    String foodJsons = (String) msg.obj;
                    try {
                        JSONObject jsonObj = new JSONObject(foodJsons);
                        int goodsnum01 = (int) jsonObj.get("goodsnum");
                        if (goodsnum01 > 0) {
                            FoodItemsBean foodItemsBean = JSON.parseObject(foodJsons, FoodItemsBean.class);
                            int goodsnum = foodItemsBean.getGoodsnum();
                            foodChildList.clear();
                            if (goodsnum > 0) {
                                List<FoodItemsBean.GoodslistBean> goodslist = foodItemsBean.getGoodslist();
                                for (int i = 0; i < goodslist.size(); i++) {
                                    foodChildList.add(goodslist.get(i));
                                    Map<String, String> getCountMap = MapUtilsSetParam.getMap(OrderMainActivity.this);
                                    getCountMap.put("opp", "getGoodsStock");
                                    getCountMap.put("goodsid", goodslist.get(i).getGoodsid());
                                    //这个是获取库存
                                    NetUtils.netWorkerGoodsid(OrderMainActivity.this, getCountMap, IpConfig.URL, handler, LOADINGDATA_GETCOUNT, goodslist.get(i).getGoodsid());
                                }
                            }

                        } else {
                            foodChildList.clear();
                            Toast.makeText(OrderMainActivity.this, "没有这个菜系的菜品", Toast.LENGTH_SHORT).show();
                        }

                        foodAdapter.notifyDataSetChanged();
//                        有待修改 1110
                        //这个是将菜品列表放入数据库中  这个地方有问题
                        insterintogoods(foodChildList);
                        String pos = Utils.getACache(OrderMainActivity.this).getAsString("pos");
                        Utils.getACache(OrderMainActivity.this).put(pos + "", jsonObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    DODialog.colseDialog(dialog02);
                    break;

                case SHOWFOODDETAILSCASH:
                    String foodJson01 = (String) msg.obj;
                    try {
                        JSONObject jsonObj = new JSONObject(foodJson01);
                        int goodsnum01 = (int) jsonObj.get("goodsnum");
                        if (goodsnum01 > 0) {
                            FoodItemsBean foodItemsBean = JSON.parseObject(foodJson01, FoodItemsBean.class);
                            int goodsnum = foodItemsBean.getGoodsnum();
                            foodChildList.clear();
                            if (goodsnum > 0) {
                                List<FoodItemsBean.GoodslistBean> goodslist = foodItemsBean.getGoodslist();
                                for (int i = 0; i < goodslist.size(); i++) {
                                    foodChildList.add(goodslist.get(i));
                                }
                            }
                        } else {
                            Toast.makeText(OrderMainActivity.this, "没有这个菜系的菜品", Toast.LENGTH_SHORT).show();
                        }
                        foodAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case SHOWFOODINFO:
                    //这个是无规格的
                    int pos = (int) msg.obj;
                    //原来设定为两个菜不同 若foodsame 为ture则为相同
                    boolean foodsame = false;
                    //用来记录 找到相同的商品时的下标
                    int index = 0;
                    Integer integer = mapcount.get(foodChildList.get(pos).getGoodsid());
                    if (integer == null) {
                        return;
                    }
                    if (integer > 0) {
                        integer--;
                        mapcount.put(foodChildList.get(pos).getGoodsid(), integer);
                    }else if(integer==-1){
                        mapcount.put(foodChildList.get(pos).getGoodsid(), integer);
                    } else {
                        Toast.makeText(OrderMainActivity.this, "库存不足", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (foodOrderInfo.size() > 0) {
                        //2017-11-4
                        //判断菜品是否有规格
                        String option = foodChildList.get(pos).getHasoption();
                        //两个id相同那么是同一个菜品 那就给count++ 否则添加新的商品
                        //recycleview中的商品id
                        String foodid = foodChildList.get(pos).getGoodsid();
                        for (int i = 0; i < foodOrderInfo.size(); i++) {
                            //同一个商品数量加一
                            if (foodid.equals(foodOrderInfo.get(i).getFoodId())) {
                                index = i;
                                foodsame = true;
                                break;
                            }
                        }

                        if (foodsame) {
                            int foodCount = foodOrderInfo.get(index).getFoodCount();
                            FoodOrderInfoBean fob = new FoodOrderInfoBean();
                            fob.setFoodCount(foodCount + 1);
                            fob.setFoodId(foodChildList.get(pos).getGoodsid());
                            fob.setFoodName(foodChildList.get(pos).getGoodsname());
                            fob.setFoodPrice(foodChildList.get(pos).getPrice());
                            fob.setHasoption(0);
                            foodOrderInfo.set(index, fob);
                        } else {
                            FoodOrderInfoBean fob = new FoodOrderInfoBean();
                            fob.setFoodCount(1);
                            fob.setFoodId(foodChildList.get(pos).getGoodsid());
                            fob.setFoodName(foodChildList.get(pos).getGoodsname());
                            fob.setFoodPrice(foodChildList.get(pos).getPrice());
                            fob.setHasoption(0);
                            foodOrderInfo.add(fob);
                        }
                        //10999

                        index = 0;
                        foodsame = false;
                    } else {
                        FoodOrderInfoBean fob = new FoodOrderInfoBean();
                        fob.setFoodCount(1);
                        fob.setFoodId(foodChildList.get(pos).getGoodsid());
                        fob.setFoodName(foodChildList.get(pos).getGoodsname());
                        fob.setFoodPrice(foodChildList.get(pos).getPrice());
                        fob.setHasoption(0);
                        foodOrderInfo.add(fob);
                    }
                    foodAdapter.notifyDataSetChanged();
                    adapter_orderInfo.notifyDataSetChanged();
                    listview_orderinfo.setSelection(adapter_orderInfo.getCount() - 1);
                    double newmoney = 0;
                    String dfmoney = "";
                    for (int i = 0; i < foodOrderInfo.size(); i++) {
                        int count = foodOrderInfo.get(i).getFoodCount();
                        String price = foodOrderInfo.get(i).getFoodPrice();
                        Double pr = Double.parseDouble(price);
                        newmoney += count * pr;
                        DecimalFormat df = new DecimalFormat("#.00");
                        //保留两位小数
                        dfmoney = df.format(newmoney);


                    }
                    Double dmoney = Double.parseDouble(dfmoney);
                    money.setText(dmoney + "");
                    break;

                case CLEAR:
                    foodOrderInfo.clear();
                    adapter_orderInfo.notifyDataSetChanged();
                    money.setText("0.0");
                    break;

                case GUANLI:
                    int po = (int) msg.obj;
                    username.setText(guanLiList.get(po).getName());
                    Utils.getACache(OrderMainActivity.this).put("workerid", guanLiList.get(po).getWorkerid());
                    popupWindowGuanli.dismiss();
                    break;

                case SERCHFOODXI:
                    String foodJson = (String) msg.obj;
                    Utils.getACache(OrderMainActivity.this).remove("foodjson");
                    Utils.getACache(OrderMainActivity.this).put("foodjson", foodJson);
                    List<FoodXi> foodxi = JSON.parseArray(foodJson, FoodXi.class);
                    if (foodxi == null && foodxi.size() == 0) {
                        return;
                    }
                    Utils.getACache(OrderMainActivity.this).put("size", foodxi.size() + "");
                    for (int i = 0; i < foodxi.size(); i++) {
                        FoodXi foodxi2 = foodxi.get(i);
                        dishList.add(foodxi2);
                    }
                    gridViewAdapter.notifyDataSetChanged();
                    //第一个设置为默认选中
                    gridViewAdapter.setSelectPosition(0);

                    getfoodinfo();
                    //设置gridview的高度  最多不大于三行  165dp  小于三行则展示items的高度
                    SetGridViewHeight.setListViewHeightBasedOnChildren(gridView);
                    //把数据存到数据库中
                    try {
                        insterintocategory(dishList);
//                        getFoodAllIntoSql();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case HUIYUANCHULI:
                    //修改会员头像
                    HuiYuanInfoBean.MemberlistBean hmb = (HuiYuanInfoBean.MemberlistBean) Utils.getACache(OrderMainActivity.this).getAsObject("huiyuaninfo");
                    Glide.with(OrderMainActivity.this).load(hmb.getAvatar()).into(headImg);
                    headImg.setFocusable(true);

                    break;
                //获取员工id
                case LOADINGDATA_GETWORKER:
                    String workerJson = (String) msg.obj;
                    Utils.getACache(OrderMainActivity.this).remove("workerjson");
                    Utils.getACache(OrderMainActivity.this).put("workerjson", workerJson);
                    Log.i("URL", ":::" + workerJson);
                    List<Worker> wkData = JSON.parseArray(workerJson, Worker.class);
                    for (int i = 0; i < wkData.size(); i++) {
                        if ("1".equals(wkData.get(i).getWorkerid())) {
                            return;
                        } else {
                            guanLiList.add(wkData.get(i));
                        }
                    }
                    guanLiAdapter.notifyDataSetChanged();
                    break;
                case LOADINGDATA_ORDERSUBMIT:
                    String orderJson = (String) msg.obj;
//                    Log.i("URL", "11061106:::" + orderJson);
                    try {
                        JSONObject jb = new JSONObject(orderJson);
                        if ("0".equals(jb.getString("status"))) {
                            Toast.makeText(OrderMainActivity.this, "下单失败", Toast.LENGTH_SHORT).show();

                            return;
                        } else {
                            Utils.getACache(OrderMainActivity.this).put("orderid", orderJson);
                            Intent intent = new Intent(OrderMainActivity.this, TableCtrlActivity.class);
                            intent.putExtra("theorderid", jb.getString("data"));
                            startActivity(intent);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        Utils.getACache(OrderMainActivity.this).remove("huiyuaninfo");
                        money.setText("0");
                        username.setText("请选择");
                        Utils.getACache(OrderMainActivity.this).remove("memberid");
                    }
                    break;

                case LOADINGDATA_ORDERSUBMITTABLE:
                    String orderTableJson = (String) msg.obj;
                    if ("0".equals(orderTableJson)) {
                        Toast.makeText(OrderMainActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Utils.getACache(OrderMainActivity.this).put("orderid", orderTableJson);
//                      //这个是新接口：
                        Intent intent = new Intent(OrderMainActivity.this, OrderControl.class);
                        startActivity(intent);
                    }
                    Utils.getACache(OrderMainActivity.this).remove("huiyuaninfo");
                    tableid = null;
                    actionId = Constant.ACTIONID_DEFAULT;
                    username.setText("请选择");
                    money.setText("0.00");
                    Utils.getACache(OrderMainActivity.this).remove("memberid");
                    break;

                case LOADINGDATA_SNAKE:
                    String sankeJson = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(sankeJson);
                        String id = jsonObject.getString("id");
                        //需要改动 刘兴文改
                        Utils.getACache(OrderMainActivity.this).put("sanke", id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case LOADINGDATA_UPDATACOUNT:

                    int position = (int) msg.obj;
                    FoodOrderInfoBean foodOrderInfoBean = foodOrderInfo.get(position);
                    int foodCount = foodOrderInfo.get(position).getFoodCount();
                    if (foodCount > 0) {
                        foodCount--;
                        Integer integer1 = mapcount.get(foodOrderInfo.get(position).getFoodId());
                        if (integer1 == null) {
                            return;
                        }
                        integer1++;
                        //109999
                        mapcount.put(foodOrderInfo.get(position).getFoodId(), integer1);
                    } else {
                        return;
                    }
                    foodOrderInfoBean.setFoodPrice(foodOrderInfoBean.getFoodPrice());
                    foodOrderInfoBean.setFoodName(foodOrderInfoBean.getFoodName());
                    foodOrderInfoBean.setFoodId(foodOrderInfoBean.getFoodId());
                    foodOrderInfoBean.setFoodCount(foodCount);
                    //刷新价格
                    String s = money.getText().toString();
                    Double moneydouble = Double.parseDouble(s);
                    Double moneyprice = Double.parseDouble(foodOrderInfoBean.getFoodPrice());
                    double dob = moneydouble - moneyprice;
                    //让double保留两位小数
                    DecimalFormat df = new DecimalFormat("#.00");

                    String formatmoney = df.format(dob);
                    Double fdmoney = Double.parseDouble(formatmoney);

                    money.setText(fdmoney + "");
                    //刷新价格
                    if (foodCount <= 0) {
                        foodOrderInfo.remove(position);
                    } else {
                        foodOrderInfo.set(position, foodOrderInfoBean);
                    }
                    foodAdapter.notifyDataSetChanged();
                    adapter_orderInfo.notifyDataSetChanged();
                    break;

                //获取二维码返回来的数据
                case LOADINGDATA_GETQRCODE:
                    String qrcode = (String) msg.obj;
                    Log.d(TAG, "签入二维码json===" + qrcode);
                    try {
                        JSONObject jsonObject = new JSONObject(qrcode);
                        String status = jsonObject.getString("status");
                        if ("1".equals(status)) {
                            String data = jsonObject.getString("data");
                            Glide.with(OrderMainActivity.this).load(data).error(R.mipmap.ic_launcher).into(img_qrcode);
                        }
                        //展示会员的窗口
                        showPopHuiYuan();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }finally {
                        headImg.setClickable(true);
                    }
                    break;

                case GETORDERLIST:
                    String orderlistgoods = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(orderlistgoods);
                        int status = jsonObject.getInt("status");
                        double listmoney = 0.0;
                        if (status == 1) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject foodbean = data.getJSONObject(i);
                                FoodOrderInfoBean fb = new FoodOrderInfoBean();
                                fb.setFoodCount(foodbean.getInt("total"));
                                fb.setFoodId(foodbean.getString("id"));
                                fb.setFoodName(foodbean.getString("title"));
                                fb.setOptionid(foodbean.getString("optionid"));
                                fb.setFoodPrice(foodbean.getString("price"));
                                fb.setHasoption(foodbean.getInt("hasoption"));
                                //1109
//                                Log.i(TAG, "handleMessage: title " + foodbean.getString("title"));
                                fb.setTitle(foodbean.getString("optionname"));
                                double marketprice = foodbean.getDouble("price");
                                int count = foodbean.getInt("total");
                                listmoney += marketprice * count;
                                foodOrderInfo.add(fb);
                            }
                        }
                        if (adapter_orderInfo != null) {
                            adapter_orderInfo.notifyDataSetChanged();
                            money.setText(listmoney + "");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case LOADINGDATA_ORDERSUBMITUP:
                    String upjson = (String) msg.obj;
                    try {
                        JSONObject jsob = new JSONObject(upjson);
                        int status = jsob.getInt("status");
                        if (status == Constant.SUCCESS) {
                            Intent intent = new Intent(OrderMainActivity.this, NewOrderDetails.class);
                            intent.putExtra("orderid", orderId);
                            startActivity(intent);
                            DODialog.colseDialog(dialog);
                            actionId = Constant.ACTIONID_DEFAULT;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    titleName.setText("点餐");
                    tableName.setText("请选桌子");
                    submit_order.setText("提交订单");
                    imgbutton_two.setVisibility(View.VISIBLE);
                    imgbutton_one.setVisibility(View.VISIBLE);
                    money.setText("0.00");
                    //1106
                    actionId = Constant.ACTIONID_DEFAULT;
                    break;

                case LOADINGDATA_GETCOUNT:
                    //获取库存量
                    String countJson = (String) msg.obj;
                    String[] str = countJson.split("=");
                    String goodsid = str[1];
                    //设置是否相同
                    boolean foodissame = false;
                    //用来存放相同的商品的下标
                    int indexsame = 0;
                    try {
                        JSONObject jsonObject = new JSONObject(str[0]);
                        int status = jsonObject.getInt("status");
                        if (status == 1) {
                            int data = jsonObject.getInt("data");
                            if (foodOrderInfo.size() > 0) {
                                for (int i = 0; i < foodOrderInfo.size(); i++) {
                                    if (goodsid.equals(foodOrderInfo.get(i).getFoodId() + "")) {
                                        indexsame = i;
                                        foodissame = true;
                                    }
                                }
                            }

                            if (actionId == Constant.ACTIONID_DEFAULT) {
                                if (foodissame) {
                                    int count = foodOrderInfo.get(indexsame).getFoodCount();
                                    mapcount.put(goodsid, (data - count));
                                } else {
                                    mapcount.put(goodsid, data);
                                }
                            } else {
                                mapcount.put(goodsid, data);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    indexsame = 0;
                    foodissame = false;
                    foodAdapter.notifyDataSetChanged();

                    break;

                case LOADINGDATA_CHECKGOODS:
                    //1009 检查库存是否充足
                    String check = (String) msg.obj;
                    try {
                        JSONObject checkJson = new JSONObject(check);
                        int status = checkJson.getInt("status");
                        if (status == 1) {
                            submitIsOk();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case OPTIONSID:
                    Intent data = (Intent) msg.obj;
                    //2017-11-09
                    String spces = data.getStringExtra("spces");
                    String optionsid = data.getStringExtra("id");
                    int pos01 = Integer.parseInt(Utils.getACache(OrderMainActivity.this).getAsString("positionp"));
                    //这个是无规格的
                    //原来设定为两个菜不同 若foodsame 为ture则为相同
                    boolean foodsame01 = false;
                    //用来记录 找到相同的商品时的下标
                    int index01 = 0;
                    Integer integer01 = mapcount.get(foodChildList.get(pos01).getGoodsid());
                    if (integer01 == null) {
                        return;
                    }
                    if (integer01 > 0) {
                        integer01--;
                        mapcount.put(foodChildList.get(pos01).getGoodsid(), integer01);
                    } else {
                        Toast.makeText(OrderMainActivity.this, "库存不足", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (foodOrderInfo.size() > 0) {
                        //2017-11-4
                        //判断菜品是有规格 判断规格是否相同  optionsid
                        for (int i = 0; i < foodOrderInfo.size(); i++) {
                            //同一个商品数量加一
                            if (optionsid.equals(foodOrderInfo.get(i).getOptionid())) {
                                index01 = i;
                                foodsame01 = true;
                                break;
                            }
                        }

                        if (foodsame01) {
                            int foodCount01 = foodOrderInfo.get(index01).getFoodCount();
                            FoodOrderInfoBean fob = new FoodOrderInfoBean();
                            fob.setFoodCount(foodCount01 + 1);
                            fob.setFoodId(foodChildList.get(pos01).getGoodsid());
                            fob.setFoodName(foodChildList.get(pos01).getGoodsname());
                            fob.setOptionid(data.getStringExtra("id"));
                            fob.setFoodPrice(data.getStringExtra("price"));
                            fob.setTitle(data.getStringExtra("title"));
                            fob.setHasoption(1);
                            fob.setSpecs(spces);
                            foodOrderInfo.set(index01, fob);
                        } else {
                            FoodOrderInfoBean fob = new FoodOrderInfoBean();
                            fob.setFoodCount(1);
                            fob.setFoodId(foodChildList.get(pos01).getGoodsid());
                            fob.setFoodName(foodChildList.get(pos01).getGoodsname());
                            fob.setFoodPrice(data.getStringExtra("price"));
                            fob.setSpecs(spces);
                            fob.setOptionid(data.getStringExtra("id"));
                            fob.setTitle(data.getStringExtra("title"));
                            fob.setHasoption(1);
                            foodOrderInfo.add(fob);
                        }
                        //10999
                        index = 0;
                        foodsame = false;
                    } else {
                        FoodOrderInfoBean fob = new FoodOrderInfoBean();
                        fob.setFoodCount(1);
                        fob.setFoodId(foodChildList.get(pos01).getGoodsid());
                        fob.setFoodName(foodChildList.get(pos01).getGoodsname());
                        fob.setFoodPrice(data.getStringExtra("price"));
                        fob.setSpecs(data.getStringExtra("spces"));
                        fob.setOptionid(data.getStringExtra("id"));
                        fob.setTitle(data.getStringExtra("title"));
                        fob.setHasoption(1);
                        foodOrderInfo.add(fob);
                    }
                    foodAdapter.notifyDataSetChanged();
                    adapter_orderInfo.notifyDataSetChanged();
                    listview_orderinfo.setSelection(adapter_orderInfo.getCount() - 1);
                    double newmoney01 = 0;
                    String dfmoney01 = "";
                    for (int i = 0; i < foodOrderInfo.size(); i++) {
                        int count = foodOrderInfo.get(i).getFoodCount();
                        String price = foodOrderInfo.get(i).getFoodPrice();
                        Double pr = Double.parseDouble(price);
                        newmoney01 += count * pr;
                        DecimalFormat df01 = new DecimalFormat("#.00");
                        //保留两位小数
                        dfmoney01 = df01.format(newmoney01);
                    }
                    Double dmoney01 = Double.parseDouble(dfmoney01);
                    money.setText(dmoney01 + "");
                    break;

                case 120000:
                    //退出运用
                    removeActivity();
                    break;

            }
        }

    };

    private void showPopGuanLi() {
        popupWindowGuanli = new PopupWindow(guanliView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindowGuanli.setFocusable(true);
        popupWindowGuanli.setBackgroundDrawable(new BitmapDrawable());
        popupWindowGuanli.setOutsideTouchable(true);
        popupWindowGuanli.setTouchable(true);
        popupWindowGuanli.showAsDropDown(imgbutton_sanjiaoxing, -84, 20);
    }

    //展示会员窗口
    private void showPopHuiYuan() {
        if (huiYuanView != null && huiYuanView.getParent() != null) {
            ((ViewGroup) huiYuanView.getParent()).removeAllViews();
        }
        popupWindowHuiYuan = new PopupWindow(huiYuanView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindowHuiYuan.setFocusable(true);
        popupWindowHuiYuan.setBackgroundDrawable(new BitmapDrawable());
        popupWindowHuiYuan.setOutsideTouchable(true);
        popupWindowHuiYuan.setTouchable(true);
        popupWindowHuiYuan.showAtLocation(headImg, Gravity.NO_GRAVITY, 0, 0);

    }


    //获取菜品详情
    public void getFood(int position) {
        DODialog.showDialog(dialog02, OrderMainActivity.this);
        if (BuildConfig.DEBUG) Log.d(TAG, "点击菜品分类获取菜品详情");
        //加载进度条------------------------
        Map<String, String> map = MapUtilsSetParam.getMap(OrderMainActivity.this);
        //2017-1110
        map.put("opp", "getgoods");
        map.put("branchid", Utils.getBranch(OrderMainActivity.this).getId());
        map.put("memberid", "");
        map.put("keyword", "");
        map.put("categoryid", dishList.get(position).getId());
        NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handler, SHOWFOODDETAILS);
    }

    //默认为第一次选择
    public void getfoodinfo() {
        Map<String, String> map = MapUtilsSetParam.getMap(OrderMainActivity.this);
        if (dishList.size() == 0) {
            return;
        }
        Utils.getACache(OrderMainActivity.this).put("pos", "0");
        map.put("opp", "getgoods");
        map.put("branchid", Utils.getBranch(OrderMainActivity.this).getId());
        map.put("memberid", "");
        map.put("keyword", "");
        //获取第一个菜品下的商品
        map.put("categoryid", dishList.get(0).getId());
        Utils.getACache(OrderMainActivity.this).put("categoryid", dishList.get(0).getId());
        NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handler, SHOWFOODDETAILS);
    }


    /**
     * 通过activity返回的数据  是会员选择的数据返回
     *
     * @param requestCode 请求码
     * @param resultCode  响应码
     * @param data        数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITYREQUESTCODE && resultCode == 200) {
            HuiYuanInfoBean.MemberlistBean huiyuaninfobean = (HuiYuanInfoBean.MemberlistBean) data.getSerializableExtra("huiyuaninfo_data");
            //提交订单的remove掉
            Utils.getACache(OrderMainActivity.this).put("huiyuaninfo", huiyuaninfobean);
            Utils.getACache(OrderMainActivity.this).put("memberid", huiyuaninfobean.getId());
            Message message = new Message();
            message.what = HUIYUANCHULI;
            handler.sendMessage(message);
        } else if (requestCode == ConstantS.OPTIONSRESULT) {
            //选择规则后的页面
            if (data == null) {
                return;
            }
            Message message = new Message();
            message.obj = data;
            message.what = OPTIONSID;
            handler.sendMessage(message);
        }

    }


    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("按下了back键   onBackPressed()");
    }


    /**
     * 监听Back键按下事件
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            exit();
//          OutActivityUtils.out(OrderMainActivity.this, "退出收银终端", "您确定要退出吗", Utils.getACache(OrderMainActivity.this),handler);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void getorderlist() {
        Map<String, String> map = MapUtilsSetParam.getMap(OrderMainActivity.this);
        map.put("opp", "geteditordergoods");
//        Log.i("URL", "orderId==" + orderId);
        map.put("orderid", orderId);
//        String url = IpConfig.URL + "act=module&" + "name=bj_qmxk&" + "do=app_api&" + "opp=geteditordergoods&" + "orderid=" + orderId + "&" + "session" + Utils.getSeesion(OrderMainActivity.this);
//        Log.i("URl", "url=" + url);
        NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handler, GETORDERLIST);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        EventBus.getDefault().unregister(this);//解除订阅
//        Glide.with(this).pauseRequests();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dialog.dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void ShuaXinShuJu(ShuaXinShuJu shuaXinShuJu) {
        int page = shuaXinShuJu.getPage();
        //页面等等第一个页面
        if (page == 2) {
            //关闭页面 接受到通知 关闭页面
//            Log.i("URL", "我已关闭了orderControl页面了，请放心！");
//            OrderMainActivity.this.finish();
        }
    }

    /**
     * @param dishList
     */
    private void insterintocategory(List<FoodXi> dishList) throws Exception {
        CatgoryGoodsDao cgd = new CatgoryGoodsDao(this);
        //清空原来的数据
        String foodjson = "0";
        cgd.del();
        for (int i = 0; i < dishList.size(); i++) {
            CategoryGoods cy = new CategoryGoods();
            cy.setParentid(dishList.get(i).getParentid());
            cy.setId(dishList.get(i).getId());
            cy.setName(dishList.get(i).getName());
            cgd.add(cy);
        }
    }

    /**
     * 将菜品数据放入数据库中
     *
     * @param foodChildList
     */
    private void insterintogoods(List<FoodItemsBean.GoodslistBean> foodChildList) throws Exception {

        GoodDetailsDao gdl = new GoodDetailsDao(this);

        String categoryid = Utils.getACache(OrderMainActivity.this).getAsString("categoryid");
        if (foodChildList.size() > 0) {
            for (int i = 0; i < foodChildList.size(); i++) {
                GoodsDetails gd = new GoodsDetails();
                gd.setPrice(foodChildList.get(i).getPrice());
                gd.setGoodsid(foodChildList.get(i).getGoodsid());
                gd.setGoodsname(foodChildList.get(i).getGoodsname());
                gd.setThumb(foodChildList.get(i).getThumb());
                gd.setHasoption(foodChildList.get(i).getHasoption());
                gd.setCategoryid(categoryid);
                gdl.add(gd);
            }
        }
        Utils.getACache(OrderMainActivity.this).remove("categoryid");
    }


    /**
     * activity重新启动
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    //    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    //退出方法
    private long time = 0;
    private void exit() {
            //如果在两秒大于2秒
        if (System.currentTimeMillis() - time > 2000) {
            //获得当前的时间
            time = System.currentTimeMillis();
            showToast("再点击一次退出应用程序");
        } else {
            //点击在两秒以内
            removeALLActivity();//执行移除所以Activity方法
        }
    }
}
