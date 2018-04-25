package com.zerone.catering.activitys.table;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zerone.catering.R;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.activitys.order.OrderControl;
import com.zerone.catering.adapter.table.RoomAdapter;
import com.zerone.catering.adapter.table.TableAdapter;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.dialog.DODialog;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.domain.table.Room;
import com.zerone.catering.domain.table.RoomList;
import com.zerone.catering.domain.table.TableData;
import com.zerone.catering.domain.table.TableList;
import com.zerone.catering.fragment.table.DdFragment;
import com.zerone.catering.fragment.table.DdktFragment;
import com.zerone.catering.fragment.table.DdlistFragment;
import com.zerone.catering.refreshandclose.shuaxin.TableFlush;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.AnimationUtil;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zeo on 2017/8/1.
 * 餐桌管理Activity
 * version 1.0
 */

public class TableCtrlActivity extends BaseActvity implements View.OnClickListener {
    //显示隐藏控件的 int
    private static final int HIDDEN = 10;
    //显示标题控件的 int
    private static final int SHOW = 11;
    private static final int LOADINGDATA_TABLECATE = 1001;
    private static final int LOADINGDATA_TABLE = 1002;
    private RoomList roomlist;
    private TableList tablelist;
    private Branch branch;
    private ACache mCache;
    private Room defaultRoom;
    private RelativeLayout three_control;
    private RelativeLayout order_MainTitle;
    private ImageView imgbutton_two;
    private ImageView imgbutton_one;
    private GridView gridview;
    private GridView gridview_table;
    private FrameLayout ctrl_layout;
    private TextView btn_dd;
//    private TextView btn_yy;
    private String theorderid;
    private String tableidChecked;
    private String tablenameChecked;
    private String tableposition;
    //记录桌子的最低消费
    private String tableMinconsume;
    private TextView titleName;
    private ImageView img_canwei;
    private ImageView img_diancan;
    private ImageView img_dingdan;
    private ImageView out_activity;
    private Intent baseint;
    private AlertDialog dialog;
    private View adview;
    private TextView title;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablectrl);
        //注册 ，判断是否存在  存在不用注册不存在 在注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mCache = ACache.get(this);
        branch = (Branch) mCache.getAsObject("branch");

        //加载进度条---------------------
        adview = LayoutInflater.from(this).inflate(R.layout.acticity_progressbar, null);
        title = (TextView) adview.findViewById(R.id.titile);
        title.setText("加载桌子数据");
        dialog = DODialog.getDialog(TableCtrlActivity.this, adview);
        //加载进度条------------------------
        roomlist = new RoomList();
        tablelist = new TableList();
        tableidChecked = "";
        tablenameChecked = "";
        tableposition = "";
        theorderid = "-99";  // 没传值时等于-99便于判断使用
        //测试功能默认带上一个传递过来的订单ID
        //theorderid ="271";
        /*实际使用用这个获取点菜页面传递过来的theorderid*/
        baseint = getIntent();
        theorderid = baseint.getStringExtra("theorderid");
        if (theorderid == null || "".equals(theorderid)) {
            theorderid = "-99";
        }
        initNetRoomList();
//        initNetTableList("0");
        initView();

    }

    /*****
     * 薛志豪
     * 页面基本逻辑功能
     */
    public void initView() {
        three_control = (RelativeLayout) findViewById(R.id.three_control);
        order_MainTitle = (RelativeLayout) findViewById(R.id.order_MainTitle);
        imgbutton_one = (ImageView) findViewById(R.id.imgbutton_one);
        imgbutton_two = (ImageView) findViewById(R.id.imgbutton_two);
        titleName = (TextView) findViewById(R.id.titleName);

        img_canwei = (ImageView) findViewById(R.id.img_canwei);
        img_canwei.setOnClickListener(this);
        img_diancan = (ImageView) findViewById(R.id.img_diancan);
        img_diancan.setOnClickListener(this);
        img_dingdan = (ImageView) findViewById(R.id.img_dingdan);
        img_dingdan.setOnClickListener(this);
        out_activity = (ImageView) findViewById(R.id.out_activity);
        out_activity.setOnClickListener(this);

        btn_dd = (TextView) findViewById(R.id.btn_dd);
//        btn_yy = (TextView) findViewById(R.id.btn_yy);
        ctrl_layout = (FrameLayout) findViewById(R.id.ctrl_layout);

        imgbutton_two.setOnClickListener(mylistener1);
        imgbutton_one.setOnClickListener(mylistener2);

        btn_dd.setOnClickListener(zcctrl);
//        btn_yy.setOnClickListener(zcctrl);

        if ("-99".equals(theorderid)) {
            titleName.setText("餐桌管理");
        } else {
            titleName.setText("餐桌管理(选桌中)");
        }
    }

    //菜单切换
    View.OnClickListener mylistener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            three_control.setVisibility(View.VISIBLE);
//            order_MainTitle.setVisibility(View.GONE);
            Message message01 = new Message();
            message01.what = HIDDEN;
            handlerMsg.sendMessage(message01);
        }
    };
    View.OnClickListener mylistener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            three_control.setVisibility(View.GONE);
//            order_MainTitle.setVisibility(View.VISIBLE);
            Message message02 = new Message();
            message02.what = SHOW;
            handlerMsg.sendMessage(message02);
        }
    };
    //左侧菜单切换  取消预约
    View.OnClickListener zcctrl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //判断是否选中桌子
            if ("".equals(tableidChecked)) {
                Toast.makeText(TableCtrlActivity.this, "请先选择桌子", Toast.LENGTH_SHORT).show();
                return;
            }
            switch (v.getId()) {
                case (R.id.btn_dd):
                    btn_dd.setBackgroundResource(R.drawable.table_ctrl_bg_left_checked);
                    btn_dd.setTextColor(Color.parseColor("#ffffff"));
//                    这个是预约 的 打开就可以用
//                    btn_yy.setBackgroundResource(R.drawable.table_ctrl_bg_right);
//                    btn_yy.setTextColor(Color.parseColor("#ffffff"));
                    if (theorderid.equals("-99")) {
                        if ("0".equals(tablelist.getUsing(Integer.parseInt(tableposition)))) {
                            init_kt_fragment();
                        } else {
                            init_gdlist_fragment();
                        }
                    } else {
                        init_xz_fragment();
                    }
                    break;
//                这个是预约 的 打开就可以用
//                case (R.id.btn_yy):
//                    btn_dd.setBackgroundResource(R.drawable.table_ctrl_bg_left);
//                    btn_dd.setTextColor(Color.parseColor("#ffffff"));
//                    btn_yy.setBackgroundResource(R.drawable.table_ctrl_bg_right_checked);
//                    btn_yy.setTextColor(Color.parseColor("#ffffff"));
//                    init_yy_fragment();
//                    break;
            }
        }
    };

    /**
     * 薛志豪
     * 碎片加载区域
     */
    //开台
    private void init_kt_fragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DdktFragment fragment4 = new DdktFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("tableid", tableidChecked);
        bundle4.putString("tablename", tablenameChecked);
        bundle4.putString("theorderid", theorderid);
        //1109刘兴文修改 添加最低消费值的记录
        bundle4.putString("minconsume", tableMinconsume);
        fragment4.setArguments(bundle4);
        transaction.replace(R.id.ctrl_layout, fragment4);
        transaction.commit();
    }

    //挂单列表
    private void init_gdlist_fragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DdlistFragment fragment2 = new DdlistFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tableid", tableidChecked);
        bundle2.putString("tablename", tablenameChecked);
        bundle2.putString("theorderid", theorderid);
        fragment2.setArguments(bundle2);
        transaction.replace(R.id.ctrl_layout, fragment2);
        transaction.commit();
        baseint.removeExtra("theorderid");
    }

    //挂单的订单详情
    public void init_gd_fragment(String orderid) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DdFragment fragment1 = new DdFragment();
        //传orderid,桌子id,桌子名称，session
        Bundle bundle1 = new Bundle();
        bundle1.putString("tableid", tableidChecked);
        bundle1.putString("tablename", tablenameChecked);
//        Log.d("TableCtrlActivity", " orderid==" + orderid);
        bundle1.putString("theorderid", orderid);
        bundle1.putString("iskt", "0");
        fragment1.setArguments(bundle1);
        transaction.replace(R.id.ctrl_layout, fragment1);
        transaction.commit();
    }

    //选桌的订单详情
    private void init_xz_fragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DdFragment fragment1 = new DdFragment();
        //传orderid,桌子id,桌子名称，session
        Bundle bundle1 = new Bundle();
        bundle1.putString("tableid", tableidChecked);
        bundle1.putString("tablename", tablenameChecked);
        bundle1.putString("theorderid", theorderid);
        bundle1.putString("iskt", "1");
        fragment1.setArguments(bundle1);
        transaction.replace(R.id.ctrl_layout, fragment1);
        transaction.commit();
    }

//    //预约
//    private void init_yy_fragment() {
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        YyFragment fragment3 = new YyFragment();
//        Bundle bundle = new Bundle();
//        //传桌子ID和桌子名称
//        bundle.putString("tableid", tableidChecked);
//        bundle.putString("tablename", tablenameChecked);
//        //bundle.putString("branchid",branch.getId());
//        fragment3.setArguments(bundle);
//        transaction.replace(R.id.ctrl_layout, fragment3);
//        transaction.commit();
//    }

    /**
     * 薛志豪
     * 远程获取数据功能
     */
    //远程读取桌子分类数据
    private void initNetRoomList() {
        Map<String, String> map = MapUtilsSetParam.getMap(TableCtrlActivity.this);
        map.put("opp", "gettablecatenew");
        map.put("branchid", branch.getId());
//        String url = IpConfig.URL+"act=module&name=bj_qmxk&do=app_api&opp=gettablecatenew&session=" +
//                Utils.getSeesion(TableCtrlActivity.this)+"&branchid="+branch.getId();
//        Log.i("URL", "initNetRoomList: "+url);
        NetUtils.netWorkByMethodPost(TableCtrlActivity.this, map, IpConfig.URL, handlerMsg, LOADINGDATA_TABLECATE);
    }

    //远程读取桌子数据
    private void initNetTableList(String roomid) {
        //展示进度条提示框
        DODialog.showDialog(dialog, TableCtrlActivity.this);
        Map<String, String> map = MapUtilsSetParam.getMap(TableCtrlActivity.this);
        map.put("opp", "gettablenew");
        map.put("branchid", branch.getId());
        map.put("roomid", roomid);




        String url=IpConfig.URL+"act=module&name=bj_qmxk&do=app_api&opp=gettablenew&branchid="+branch.getId()+"&roomid="+roomid+"&session="+Utils.getSeesion(TableCtrlActivity.this);

//        Log.d("TableCtrlActivity","TableCtrlActivity="+ url);

        NetUtils.netWorkByMethodPost(TableCtrlActivity.this, map, IpConfig.URL, handlerMsg, LOADINGDATA_TABLE);
    }

    /**
     * 薛志豪
     * 远程获取的数据格式化
     */
    //默认房间为大厅
    public Room getDefaultRoom() {
        defaultRoom = new Room();
        defaultRoom.setId("0");
        defaultRoom.setRoomname("大厅");

        return defaultRoom;
    }

    //格式化桌子分类数据到roomlist
    private void initRoomlistData(String jsonroom) {
        roomlist.clearSize();
//        roomlist.add(getDefaultRoom());
//        roomlist.addSize();
        //刘兴文 2017-09- 15---------------------
        if (roomlist.getList().size() > 0) {
            //设置获取第一个房间的id号
            mCache.put("position", roomlist.getId(0));
        }
        //刘兴文 2017-09- 15  ---------------------
        try {
            JSONObject jsonObj = new JSONObject(jsonroom);
            String status = jsonObj.getString("status");
            if ("0".equals(status)) {
                String data = jsonObj.getString("data");
                Toast.makeText(TableCtrlActivity.this, data, Toast.LENGTH_SHORT).show();
            } else if ("1".equals(status)) {
                JSONArray dataArray = jsonObj.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = (JSONObject) dataArray.get(i);
                    Room roomData = new Room();
//                    Log.i("id", dataObject.getString("id"));
//                    Log.i("roomname", dataObject.getString("roomname"));
                    roomData.setId(dataObject.getString("id"));
                    roomData.setRoomname(dataObject.getString("roomname"));
                    roomlist.add(roomData);
                    roomlist.addSize();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initRoomDataToPage();
    }

    //格式化桌子数据到tablelist
    private void initTablelistData(String jsonTable) {
        tablelist.clearSize();
        tablelist.clearList(); //由于桌子数据是随房间变化实时的每次格式化数据以前清除之前的数据
        try {
            JSONObject jsonObj = new JSONObject(jsonTable);
            String status = jsonObj.getString("status");
            if ("0".equals(status)) {
                String data = jsonObj.getString("data");
                Toast.makeText(TableCtrlActivity.this, data, Toast.LENGTH_SHORT).show();
            } else {
                JSONArray dataArray = jsonObj.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = (JSONObject) dataArray.get(i);
//                    Log.d("TableCtrlActivity", "dataObject:" + dataObject);
                    TableData tableData = new TableData();
                    tableData.setId(dataObject.getString("id"));
                    tableData.setTablename(dataObject.getString("tablename"));
                    tableData.setMaxseat(dataObject.getString("maxseat"));
                    tableData.setRoomid(dataObject.getString("roomid"));
                    tableData.setUsing(dataObject.getString("using"));
                    tableData.setYdstatus(dataObject.getString("ydstatus"));
                    tableData.setTime_st(dataObject.getString("time_st"));
                    tableData.setMinconsume(dataObject.getString("minconsume"));
                    tablelist.add(tableData);
                    tablelist.addSize();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initTableDataToPage();
    }


    /**
     * 薛志豪
     * 加载数据到页面以及相关事件
     */
    //加载房间数据到页面
    private void initRoomDataToPage() {
        gridview = (GridView) findViewById(R.id.gridview);
        //格式化数据
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < roomlist.getSize(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roomname", roomlist.getRoomname(i));
            map.put("id", roomlist.getId(i));
            data_list.add(map);
        }
        //新建适配器
        RoomAdapter adapter = new RoomAdapter(this, data_list, roomlist.getSize());
        //配置适配器
        gridview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(gridview);
        gridview.setOnItemClickListener(changeRoom);
        //默认第一个被选中  并且给第一个分类赋值
        adapter.setCheckedposition(0);
        initNetTableList(roomlist.getId(0));
    }

    GridView.OnItemClickListener changeRoom = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LinearLayout ll = (LinearLayout) view;
            RoomAdapter adapter = (RoomAdapter) parent.getAdapter();
            adapter.setCheckedposition(position);
            adapter.notifyDataSetChanged();
            //切换房间清空选中的桌子ID和名称
            tablenameChecked = "";
            tableidChecked = "";
            tableposition = "";
            ctrl_layout.removeAllViews();
            btn_dd.setBackgroundResource(R.drawable.table_ctrl_bg_left);
            btn_dd.setTextColor(Color.parseColor("#ffffff"));
//            btn_yy.setBackgroundResource(R.drawable.table_ctrl_bg_right);
//            btn_yy.setTextColor(Color.parseColor("#ffffff"));
            mCache.put("position", roomlist.getId(position));
            initNetTableList(roomlist.getId(position));
        }
    };
    //public void initTableListData(int position){
    //Toast.makeText(TableCtrlActivity.this, roomlist.getRoomname(position), Toast.LENGTH_SHORT).show();
    // initNetTableList(roomlist.getId(position));
    // }

    //加载桌子数据到页面
    public void initTableDataToPage() {
        gridview_table = (GridView) findViewById(R.id.gridview_table);
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < tablelist.getSize(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", tablelist.getId(i));
            map.put("tablename", tablelist.getTablename(i));
            map.put("maxseat", tablelist.getMaxseat(i));
            map.put("roomid", tablelist.getRoomid(i));
            map.put("using", tablelist.getUsing(i));
            map.put("ydstatus", tablelist.getYdstatus(i));
            map.put("time_st", tablelist.getTime_st(i));
            //1109 刘兴文加
            map.put("minconsume", tablelist.getgetMinconsume(i));
            data_list.add(map);
        }
        TableAdapter adapter = new TableAdapter(this, data_list);
        gridview_table.setAdapter(adapter);
        gridview_table.setOnItemClickListener(changeTable);
        //关闭对话框
        DODialog.colseDialog(dialog);
    }

    GridView.OnItemClickListener changeTable = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //theorderid!="" 且 桌子不为空桌时提示 挂单场景
            if (!"-99".equals(theorderid) && !"0".equals(tablelist.getUsing(position))) {
                Toast.makeText(TableCtrlActivity.this, "选桌状态只能选择空桌", Toast.LENGTH_SHORT).show();
            } else {
                TableAdapter adapter = (TableAdapter) parent.getAdapter();
                adapter.setCheckedposition(position);
                //记录选中的桌子Id和名称
                if (!tablelist.getId(position).equals(tableidChecked)) {
                    tablenameChecked = tablelist.getTablename(position);
                    tableidChecked = tablelist.getId(position);
                    tableMinconsume = tablelist.getgetMinconsume(position);
                    tableposition = position + "";
                    ctrl_layout.removeAllViews();
                    btn_dd.setBackgroundResource(R.drawable.table_ctrl_bg_left);
                    btn_dd.setTextColor(Color.parseColor("#ffffff"));
//                    btn_yy.setBackgroundResource(R.drawable.table_ctrl_bg_right);
//                    btn_yy.setTextColor(Color.parseColor("#ffffff"));
                }
                adapter.notifyDataSetChanged();
            }

//Log.i("URL", "400110120113");
            if ("0".equals(tablelist.getYdstatus(position))) {
                if (theorderid.equals("-99")) {
                    if ("0".equals(tablelist.getUsing(Integer.parseInt(tableposition)))) {
                        init_kt_fragment();
                    } else {
                        init_gdlist_fragment();
                    }
                } else {
                    init_xz_fragment();
                }
            } else if ("1".equals(tablelist.getYdstatus(position))) {
//                预约已经关闭打开即可用
//                init_yy_fragment();
            }
        }
    };

    Handler handlerMsg = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADINGDATA_TABLECATE:
                    String jsonroom = (String) msg.obj;
//                    Log.i("TAG", "handleMessage: " + jsonroom);
                    initRoomlistData(jsonroom);
                    break;
                case LOADINGDATA_TABLE:
                    String jsontable = (String) msg.obj;
                    //2017-1113
//                    Log.d("jsontable", jsontable);
                    initTablelistData(jsontable);
                    break;
                case 1:
//                    Log.d("URL", (String) msg.obj);
                    break;

                case HIDDEN:
                    three_control.setAnimation(AnimationUtil.moveToViewBottom(600));
                    img_canwei.setAnimation(AnimationUtil.moveToViewBottom(900));
                    img_diancan.setAnimation(AnimationUtil.moveToViewBottom(1100));
                    img_dingdan.setAnimation(AnimationUtil.moveToViewBottom(1400));
                    out_activity.setAnimation(AnimationUtil.moveToViewBottom(1600));
                    order_MainTitle.setVisibility(View.GONE);
                    three_control.setVisibility(View.VISIBLE);
                    break;
                case SHOW:
                    order_MainTitle.setAnimation(AnimationUtil.moveToViewBottom(600));
                    order_MainTitle.setVisibility(View.VISIBLE);
                    three_control.setVisibility(View.GONE);
                    break;
            }
        }
    };

    /**
     * 设置列表的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 5;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        if (totalHeight > 125) {
            params.height = 125;
        } else {
            params.height = totalHeight;
        }
        // 设置margin
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    //刘兴文修改
    //给餐桌的标题添加动画效果
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_canwei:
                break;
            case R.id.img_diancan:
                TableCtrlActivity.this.finish();
                break;
            case R.id.img_dingdan:
                Intent intent = new Intent(TableCtrlActivity.this, OrderControl.class);
                startActivity(intent);
                TableCtrlActivity.this.finish();
                break;
            case R.id.out_activity:
                TableCtrlActivity.this.finish();
                break;
        }
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
            TableCtrlActivity.this.finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void TableFlush(TableFlush tableFlush) {
        int page = tableFlush.getPage();
        //刷新桌子的数据
        if (page == 1) {
            //关闭页面 接受到通知 关闭页面
            //房间id
            String roomid = mCache.getAsString("position");
            Map<String, String> map = MapUtilsSetParam.getMap(TableCtrlActivity.this);
            map.put("opp", "gettablenew");
            map.put("branchid", branch.getId());
            map.put("roomid", roomid);
            NetUtils.netWorkByMethodPost(TableCtrlActivity.this, map, IpConfig.URL, handlerMsg, LOADINGDATA_TABLE);
        }
    }
}
