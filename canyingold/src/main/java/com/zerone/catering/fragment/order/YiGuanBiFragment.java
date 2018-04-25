package com.zerone.catering.fragment.order;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zerone.catering.R;
import com.zerone.catering.action.orderfragment.GoToTableActivityAction;
import com.zerone.catering.activitys.order.NewOrderDetailsOther;
import com.zerone.catering.activitys.main.OrderMainActivity;
import com.zerone.catering.adapter.ListViewOrderGuanLiAdapter;
import com.zerone.catering.adapter.ListViewOrderItemsAdapter;
import com.zerone.catering.adapter.OrderPopWindowAdapter;
import com.zerone.catering.adapter.OrderPopWindowODAdapter;
import com.zerone.catering.adapter.OrderPopWindowPYAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.db.impl.WorkerTabeDao;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.DaiFuKuanOrderBean;
import com.zerone.catering.domain.MyGestureListener;
import com.zerone.catering.domain.OrderType;
import com.zerone.catering.domain.PayType;
import com.zerone.catering.domain.Worker;
import com.zerone.catering.refreshandclose.shuaxin.OrderListFlush;
import com.zerone.catering.refreshandclose.shuaxin.OrderListFlushLeft;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.AnimationUtil;
import com.zerone.catering.utils.ListViewUtils;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.SimpleTimeUtils;
import com.zerone.catering.utils.UtilsTime;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4.
 *
 *  -1
 */

public class YiGuanBiFragment   extends Fragment  implements View.OnClickListener{
    private ACache mCache;
    private Branch branch;
    private View guanliView;
    private RelativeLayout relativeOne;
    private RelativeLayout relativeTwo;
    private ImageView imgbutton_sanjiaoxing;
    private ImageView out_activity;
    private ImageView img_canwei;
    private ImageView img_diancan;
    private ImageView img_dingdan;
    //手势
    private GestureDetector gesture; //手势识别
    //页面
    private  Integer page=1;
    //获取订单
    private  static  final  int LOADINGDATA_GETORDER=0;
    //获取员工
    private  static  final  int LOADINGDATA_GETWORKER=1;

    private  static final  int LOADINGDATA_TIAOJIAN=2;
    //显示隐藏控件的 int
    private static final int HIDDEN = 3;

    //显示标题控件的 int
    private static final int SHOW = 4;

    //管理员列表
    private static final int GUANLI= 5;

    List<DaiFuKuanOrderBean.DataBean.ListBean> getOrderList;
    private ListView getOrderListView;
    private ListViewOrderItemsAdapter getOrderAdapter;
    private TextView showtime;

    private List<Worker> dpryList;
    private List<OrderType> odList;
    private List<PayType> pyList;
    private Button search;
    private TextView select_dianpurenyuan;
    private EditText select_dingdanbiaohao;
    private TextView select_dingdanleixing;
    private TextView select_zhifufangshi;
    private TextView select_kaishishijian;
    private TextView select_jieshushijian;

    private OrderPopWindowAdapter dpryAdapter;
    private OrderPopWindowODAdapter odAdapter;
    private OrderPopWindowPYAdapter pyAdapter;
    private PopupWindow popupWindow;
    private View dianpuView;
    private View dingdanView;
    private View paystateView;
    private ListView dpListView;
    private ListView ddListView;
    private ListView psListView;
    private TextView zongjimoney;
    private TextView benyemoney;
    private PopupWindow popupWindowGuanli;
    private ListView guanliListView;
    private TextView titleName;
    //管理员测试数据
    private List<Worker> guanLiList;
    private ImageView imgbutton_two;
    private ImageView imgbutton_one;
    private TextView username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_neworder_quanbudigndan, container, false);
        mCache = ACache.get(getActivity());
        branch = (Branch) mCache.getAsObject("branch");
        gesture = new GestureDetector(this.getActivity(), new MyGestureListener(6,"已关闭"));
        //注册 ，判断是否存在  存在不用注册不存在 在注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loaingData();
        initStaticData();
        initView(view);
        ListViewAction();
        return  view;
    }
    //listview的点击事件
    private void ListViewAction() {
        dpListView .setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message msg = new Message();
                msg.what=100;
                msg.obj=position;
                handler.sendMessage(msg);
            }
        });
        ddListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message msg = new Message();
                msg.what=101;
                msg.obj=position;
                handler.sendMessage(msg);
            }
        });
        psListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message msg = new Message();
                msg.what=102;
                msg.obj=position;
                handler.sendMessage(msg);
            }
        });
        getOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), NewOrderDetailsOther.class);
                intent.putExtra("orderid",getOrderList.get(position).getId());
                intent.putExtra("status",getOrderList.get(position).getStatus());
                startActivity(intent);

            }
        });
    }

    /**
     * 初始化静态数据
     */
    private void initStaticData() {
        //店铺人员
        //店铺人员
        WorkerTabeDao dao = new WorkerTabeDao(getContext());
        try {
            dpryList =dao.getWorker();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //订单类型
        odList = new ArrayList<>();
        //订单类型的列表
        //订单类型列表
        odList.add(new OrderType(1,"外卖订单"));
        odList.add(new OrderType(2,"预约就餐订单"));
        odList.add(new OrderType(3,"现场订单订单"));
        //支付类型
        pyList = new ArrayList<>();
        //支付方式的列表
        //支付方式
        //1.在线余额支付
        // 2.在线微信支付
        // 3.现场现金支付
        // 4.现场刷卡支付
        // 5.现场支付宝付
        // 6.现场微信支付
        // 7.在线手动确认付款
        pyList.add(new PayType(1,"在线余额支付"));
        pyList.add(new PayType(2,"在线微信支付"));
        pyList.add(new PayType(3,"现场现金支付"));
        pyList.add(new PayType(4,"现场刷卡支付"));
        pyList.add(new PayType(5,"现场支付宝付"));
        pyList.add(new PayType(6,"现场微信支付"));
        pyList.add(new PayType(7,"在线手动确认付款"));
    }

    /**
     * view的初始化
     */
    private void initView(View view) {
        //管理员
        guanliView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_neworder_guanli, null);
        guanliListView = (ListView) guanliView.findViewById(R.id.listview_guanli);
        guanliListView.setAdapter(new ListViewOrderGuanLiAdapter(getActivity(),guanLiList));

        //两大标题relayout 控件的显示和隐藏
        //这个是三大按钮的父控件
        relativeOne = (RelativeLayout)view.findViewById(R.id.three_control);
        //这个是按钮所展示的父控件
        relativeTwo = (RelativeLayout) view.findViewById(R.id.order_MainTitle);
        //标题的名称
        titleName = (TextView) view.findViewById(R.id.titleName);

        username = (TextView) view.findViewById(R.id.username);
        img_canwei = (ImageView) view. findViewById(R.id.img_canwei);
        img_canwei.setOnClickListener(new GoToTableActivityAction(getActivity()));
        img_diancan = (ImageView)  view.findViewById(R.id.img_diancan);
        img_diancan.setOnClickListener(this);
        img_dingdan = (ImageView)  view.findViewById(R.id.img_dingdan);
        img_dingdan.setOnClickListener(this);
        out_activity = (ImageView) view.findViewById(R.id.out_activity);
        out_activity.setOnClickListener(this);
        //点击显示控件的控制按钮
        imgbutton_two = (ImageView) view.findViewById(R.id.imgbutton_two);
        imgbutton_two.setOnClickListener(this);

        //点击显示控件的控制按钮
        imgbutton_one = (ImageView) view.findViewById(R.id.imgbutton_one);
        imgbutton_one.setOnClickListener(this);

        //打开管理员
        //打开下拉
        LinearLayout linerLayout = (LinearLayout) view.findViewById(R.id.linerLayout);
        linerLayout.setOnClickListener(this);
        //三角形按钮
        imgbutton_sanjiaoxing = (ImageView) view.findViewById(R.id.imgbutton_sanjiaoxing);
        getOrderListView = (ListView) view.findViewById(R.id.listview);
        getOrderAdapter = new ListViewOrderItemsAdapter(getActivity(),getOrderList,handler);
        getOrderListView.setAdapter(getOrderAdapter);
        //给listview添加手势识别
        getOrderListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);//返回手势识别触发的事件
            }
        });
        dianpuView = LayoutInflater.from(getContext()).inflate(R.layout.order_dianpurenyuan_pop,null);
        dpListView = (ListView) dianpuView.findViewById(R.id.dianpurenyuan_listview);


        dingdanView = LayoutInflater.from(getContext()).inflate(R.layout.order_orderleixing_pop,null);
        ddListView = (ListView) dingdanView.findViewById(R.id.orderLeiXing_listview);


        paystateView = LayoutInflater.from(getContext()).inflate(R.layout.order_paystate_pop,null);
        psListView = (ListView) paystateView.findViewById(R.id.payState_listview);



        //设置单前时间
        showtime = (TextView) view.findViewById(R.id.showtime);
        showtime.setText(UtilsTime.getNowTime());

        //查询按钮
        search = (Button) view.findViewById(R.id.search);
        search.setOnClickListener(this);

        //店铺人员的选择
        select_dianpurenyuan = (TextView) view.findViewById(R.id.select_dianpurenyuan);
        select_dianpurenyuan.setOnClickListener(this);
        //订单编号的输入
        select_dingdanbiaohao = (EditText) view.findViewById(R.id.select_dingdanbiaohao);
        //订单类型
        select_dingdanleixing = (TextView) view.findViewById(R.id.select_dingdanleixing);
        select_dingdanleixing.setOnClickListener(this);
        //支付方式
        select_zhifufangshi = (TextView) view.findViewById(R.id.select_zhifufangshi);
        select_zhifufangshi.setOnClickListener(this);

        //开始时间
        select_kaishishijian = (TextView) view.findViewById(R.id.select_kaishishijian);
        select_kaishishijian.setOnClickListener(this);
        //结束时间
        select_jieshushijian = (TextView) view.findViewById(R.id.select_jieshushijian);
        select_jieshushijian.setOnClickListener(this);
        //订单类型列表
        odAdapter = new OrderPopWindowODAdapter(getActivity(), odList);
        ddListView.setAdapter(odAdapter);
        //支付方式列表
        pyAdapter = new OrderPopWindowPYAdapter(getActivity(), pyList);
        psListView.setAdapter(pyAdapter);

        //店铺人员的列表
        dpryAdapter = new OrderPopWindowAdapter(getActivity(), dpryList);
        dpListView.setAdapter(dpryAdapter);


        //总计money的显示

        zongjimoney= (TextView) view.findViewById(R.id.zongjimoney);
        zongjimoney.setText("￥0.00");
        benyemoney = (TextView) view.findViewById(R.id.benyemoney);
        benyemoney.setText("￥0.00");
    }

    /**
     * 获取网络数据 订单
     */
    private void loaingData() {
        //这个需要改成worker
        guanLiList=new ArrayList<>();
        getOrderList = new ArrayList<>();
        //获取order的数据
        Map<String, String> mapOrder = MapUtilsSetParam.getMap(getContext());
        mapOrder.put("opp", "orderlist");
        mapOrder.put("branchid", branch.getId());
        mapOrder.put("page","1");
        mapOrder.put("status", "-1");
        NetUtils.netWorkByMethodPost(getActivity(),mapOrder, IpConfig.URL,handler,LOADINGDATA_GETORDER);
//        String url = IpConfig.URL+"act=module&"+"name=bj_qmxk&"+"do=app_api&"+"opp=orderlist&"+
//                "session="+ ""+"&"+"branchid="+branch.getId()+"&page=1&"+"status=0";
//        Log.d("URL",url);

        //获取店铺人员的数据
//        Map<String, String> mapWorker =MapUtilsSetParam.getMap(getContext());
//        mapWorker.put("opp", "getworker");
//        mapWorker.put("branchid", branch.getId());
//        NetUtils.netWorkByMethodPost(getActivity(),  mapWorker, IpConfig.URL,handler,LOADINGDATA_GETWORKER );

    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  LOADINGDATA_GETORDER:
                    String orderJson = (String) msg.obj;
                    DaiFuKuanOrderBean daiFuKuanOrderBean = JSON.parseObject(orderJson, DaiFuKuanOrderBean.class);
                    if (daiFuKuanOrderBean.getStatus()==0){
                        return;
                    }
                    List<DaiFuKuanOrderBean.DataBean.ListBean> list = daiFuKuanOrderBean.getData().getList();
                    if (list.size()>0){
                        getOrderList.clear();
                        for (int i=0;i<list.size();i++){
                            getOrderList.add(list.get(i));
                        }
                        getOrderAdapter.notifyDataSetChanged();
                        zongjimoney.setText("￥"+daiFuKuanOrderBean.getData().getAlltotal());
                        benyemoney.setText("￥"+daiFuKuanOrderBean.getData().getTotalyejin());
                    }else {
                        Toast.makeText(getContext(),"没有更多了！",Toast.LENGTH_SHORT).show();
                        page=1;
                    }
                    break;

                case LOADINGDATA_GETWORKER:
                    String workerJson= (String) msg.obj;
                    mCache.remove("workerjson");
                    mCache.put("workerjson", workerJson);
                    List<Worker> wkData = JSON.parseArray(workerJson, Worker.class);
                    for (int i=0;i<wkData.size();i++){
                        if ("1".equals(wkData.get(i).getWorkerid())){
                            return;
                        }else {
                            dpryList.add(wkData.get(i));
                        }
                    }
                    dpryAdapter.notifyDataSetChanged();
                    ListViewUtils.setListViewHeightBasedOnChildren(dpListView);
                    break;
                case LOADINGDATA_TIAOJIAN:
                    String tiaojianJson= (String) msg.obj;
                    DaiFuKuanOrderBean daiFuKuanOrderBean01 = JSON.parseObject(tiaojianJson, DaiFuKuanOrderBean.class);
                    if (daiFuKuanOrderBean01.getData().getList().size()==0){
                        Toast.makeText(getActivity(),"没有这个订单",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<DaiFuKuanOrderBean.DataBean.ListBean> list02 = daiFuKuanOrderBean01.getData().getList();
                    getOrderList.clear();
                    for (int i=0;i<list02.size();i++){
                        getOrderList.add(list02.get(i));
                    }
                    getOrderAdapter.notifyDataSetChanged();
                    break;

                case 100:
                    int  index= (int) msg.obj;
                    select_dianpurenyuan.setText(dpryList.get(index).getName());
                    mCache.put("workerid",dpryList.get(index).getWorkerid());
                    popupWindow.dismiss();
                    break;
                case 101:
                    int  index01= (int) msg.obj;
                    select_dingdanleixing.setText(odList.get(index01).getTypeName());
                    mCache.put("dispatchchoice",odList.get(index01).getOrderType()+"");
                    popupWindow.dismiss();
                    break;
                case 102:
                    int  index02= (int) msg.obj;
                    select_zhifufangshi.setText(pyList.get(index02).getPayTypeName());
                    mCache.put("orderpaytype",pyList.get(index02).getPayTypeStaus()+"");
                    popupWindow.dismiss();
                    break;
                case 2017:
                    String startTime = (String) msg.obj;
                    select_kaishishijian.setText(startTime);
                    break;

                case 2018:
                    String endTime = (String) msg.obj;
                    select_jieshushijian.setText(endTime);
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
                case GUANLI:
                    int po= (int) msg.obj;
                    username.setText(guanLiList.get(po).getName());
                    popupWindowGuanli.dismiss();
                    break;
                case 20000:

                    String orderid = (String) msg.obj;
//                    Log.i("URL","orderid="+orderid);
                    Map<String, String> map =MapUtilsSetParam.getMap(getContext());
                    map.put("opp", "printorder");
                    map.put("branchid", branch.getId());
                    map.put("orderid",orderid);
                    NetUtils.netWorkByMethodPost(getContext(), map, IpConfig.URL, handler,200001);

                    break;

                case 200001:
                    String printjson= (String) msg.obj;
                    try {
                        JSONObject jsp= new JSONObject(printjson);
                        if (jsp.getInt("status")==1){
                            Toast.makeText(getContext(),jsp.getString("data"),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(),jsp.getString("data"),Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //查询按钮
            case R.id.search:
                Map<String, String> mapOrder =MapUtilsSetParam.getMap(getContext());
                String startTime= select_kaishishijian.getText().toString();
                if (!"请选择".equals(startTime)){
                    mapOrder.put("timestart",startTime);
                }
                String endTime=select_jieshushijian.getText().toString();
                if (!"请选择".equals(endTime)){
                    mapOrder.put("timeend",endTime);
                }
                String orderNumber=select_dingdanbiaohao.getText().toString().trim();
                if (!"".equals(orderNumber)){
                    mapOrder.put("ordersn",orderNumber);
                }
                //员工id
                String workerid= mCache.getAsString("workerid");
                if (workerid!=null){
                    mapOrder.put("workerid",workerid);
                }
                //订单类型
                String dispatchchoice= mCache.getAsString("dispatchchoice");
                if (dispatchchoice!=null){
                    mapOrder.put("dispatchchoice",dispatchchoice);
                }
                //支付类型
                String orderpaytype= mCache.getAsString("orderpaytype");
                if (orderpaytype!=null){
                    mapOrder.put("orderpaytype",orderpaytype);
                }

                mapOrder.put("opp", "orderlist");

                mapOrder.put("branchid", branch.getId());
                mapOrder.put("page","1");
                mapOrder.put("status", "-1");
                NetUtils.netWorkByMethodPost(getActivity(),mapOrder, IpConfig.URL,handler,LOADINGDATA_TIAOJIAN);
                String url = IpConfig.URL+"act=module&"+"name=bj_qmxk&"+"do=app_api&"+"opp=orderlist&"+
                        "session="+"&"+"branchid="+branch.getId()+"&page=1&"+"status=-99&"+"workerid="+workerid;
//                Log.d("URL",url);
                mCache.remove("workerid");
                mCache.remove("dispatchchoice");
                mCache.remove("orderpaytype");
                select_dianpurenyuan.setText("请选择");
                select_zhifufangshi.setText("请选择");
                select_dingdanleixing.setText("请选择");
                select_kaishishijian.setText("请选择");
                select_jieshushijian.setText("请选择");
                select_dianpurenyuan.setText("请选择");
                select_dingdanbiaohao.setHint("请输入订单编号");
                break;
            //店铺人员
            case R.id.select_dianpurenyuan:
                showPop(dianpuView,select_dianpurenyuan);
                break;

            //订单类型
            case R.id.select_dingdanleixing:
                showPop(dingdanView,select_dingdanleixing);
                break;
            //支付方式
            case R.id.select_zhifufangshi:
                showPop(paystateView,select_zhifufangshi);
                break;

            //开始时间
            case  R.id.select_kaishishijian:
                SimpleTimeUtils.selectTime(getActivity(),handler,"start");
                break;

            //结束时间
            case  R.id.select_jieshushijian:
                SimpleTimeUtils.selectTime(getActivity(),handler,"end");
                break;
            //管理员列表展示
            case R.id.linerLayout:
                showPopGuanLi();
                break;
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
            //这个有待改动
            case  R.id.img_diancan:
                Intent intent =new Intent(getActivity(), OrderMainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.out_activity:
                getActivity().finish();
                break;
        }
    }

    private void showPop(View view,TextView tView) {
        popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                getActivity().getWindow().getWindowManager().getDefaultDisplay().getHeight());
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(tView);
    }

    private void showPopGuanLi() {
        popupWindowGuanli = new PopupWindow(guanliView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindowGuanli.setFocusable(true);
        popupWindowGuanli.setBackgroundDrawable(new BitmapDrawable());
        popupWindowGuanli.setOutsideTouchable(true);
        popupWindowGuanli.setTouchable(true);
        popupWindowGuanli.showAsDropDown(imgbutton_sanjiaoxing,-84,20);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
//        Glide.with(this).pauseRequests();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void OrderListFlush(OrderListFlush orderListFlush) {
        int pag = orderListFlush.getPageid();
        if (pag==6){
            //获取order的数据
            Map<String, String> mapOrder = MapUtilsSetParam.getMap(getContext());
            page++;
            mapOrder.put("opp", "orderlist");
            mapOrder.put("branchid", branch.getId());
            mapOrder.put("page",page+"");
            mapOrder.put("status", "-1");
            NetUtils.netWorkByMethodPost(getActivity(),mapOrder, IpConfig.URL,handler,LOADINGDATA_GETORDER);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void OrderListFlushLeft(OrderListFlushLeft orderListFlush) {
        int pag = orderListFlush.getPageid();
        if (pag==6){
            //获取order的数据
            if (page==1){
                Toast.makeText(getContext(),"已经是首页了！",Toast.LENGTH_SHORT).show();
                return;
            }
            page--;
            Map<String, String> mapOrder = MapUtilsSetParam.getMap(getContext());
            mapOrder.put("opp", "orderlist");
            mapOrder.put("branchid", branch.getId());
            mapOrder.put("page",""+page);
            mapOrder.put("status", "-1");
            NetUtils.netWorkByMethodPost(getActivity(),mapOrder, IpConfig.URL,handler,LOADINGDATA_GETORDER);
        }
    }
}