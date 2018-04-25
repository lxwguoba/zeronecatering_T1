package com.zerone.catering.dialog.option_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zerone.catering.BuildConfig;
import com.zerone.catering.R;
import com.zerone.catering.action.OptionsAction;
import com.zerone.catering.activitys.main.OrderMainActivity;
import com.zerone.catering.adapter.ListViewOptionTitle;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.constant.Constant;
import com.zerone.catering.constant.ConstantS;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.dialog.DODialog;
import com.zerone.catering.domain.OptionsBean;
import com.zerone.catering.domain.OptionsBeanItem;
import com.zerone.catering.domain.TestOptionBean;
import com.zerone.catering.setbasedata.TestJson;
import com.zerone.catering.utils.MapUtilsSetParam;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zerone.catering.R.id.money;

/**
 * Created by on 2017/10/27 0027 10 52.
 * Author  LiuXingWen
 */

public class OptionsActivity extends BaseActvity {
    //确定按钮
    private Button options_btn;
    private ListView listTitle;
    //这个是规格的详情页面集合
    private List<OptionsBeanItem> optionBeenList;
    //这个是规格的titlte
    private List<OptionsBean> list;
    //用来获取goodsid
    private Intent intent;
    private String goodsid;
    //规格项的标题适配器
    private ListViewOptionTitle opAdapter;
    //默认选中的规格现拼接的数据
    private String specs = "";
    //这个是菜品的价格
    private TextView price;
    private String memberid;
    // 加载进度条
    private AlertDialog dialog02;
    private TextView title;
    private View adview02;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        intent = getIntent();
        goodsid = intent.getStringExtra("goodsid");
        memberid = intent.getStringExtra("memberid");
        setdata();
        initView();
        action();
    }

    //这个是用来存放点击事件的函数

    private void action() {

        options_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent();
                String jbtJson = Utils.getACache(OptionsActivity.this).getAsString("jbt");
                 Log.d("OptionsActivity","====================" +jbtJson);
                 if (jbtJson==null){
                     return;
                 }
                try {
                    JSONObject jbect = new JSONObject(jbtJson);
                    JSONObject data = jbect.getJSONObject("data");
                    String id = data.getString("id");
                    String title = data.getString("title");
                    String marketprice = data.getString("marketprice");
                    String specss = data.getString("specs");
                    //最低消费的值
                    //返回数据写入到Intent中
                    intent.putExtra("id", id);
                    intent.putExtra("title", title);
                    intent.putExtra("spces", specss);
                    intent.putExtra("price", marketprice);
                    //调用setResult接口
                    setResult(ConstantS.OPTIONSRESULT, intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    //给这个页面设置圆角
    @Override
    public void onAttachedToWindow() {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = (dm.widthPixels * 6) / 10;
        lp.height = (dm.heightPixels * 8) / 10;
        getWindowManager().updateViewLayout(view, lp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view.setBackgroundResource(R.drawable.options);
    }

    //初始化数据
    private void setdata() {
        //选择规则后的页面
        list = new ArrayList<>();
        optionBeenList = new ArrayList<>();
        //获取规格和规格项
        Map<String, String> map = MapUtilsSetParam.getMap(OptionsActivity.this);
        map.put("opp", "getGoodsSpecs");
        map.put("goodsid", goodsid);
        NetUtils.netWorkByMethodPost(this, map, IpConfig.URL, handler, ConstantS.GETOPTIONS);


    }

    /**
     * 初始控件
     */
    private void initView() {
        adview02 = LayoutInflater.from(this).inflate(R.layout.acticity_progressbar, null);
        title = (TextView) adview02.findViewById(R.id.titile);
        title.setText("获取价格");
        dialog02 = DODialog.getDialog(OptionsActivity.this, adview02);
        options_btn = findViewById(R.id.options_btn);
        //用来存放规格项
        listTitle = findViewById(R.id.title);
        opAdapter = new ListViewOptionTitle(this, list, handler);
        listTitle.setAdapter(opAdapter);
        //用来存放规格实体
        listTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String option = list.get(pos).getTitle();
//                Log.d("OptionsActivity", "规格项的点击事件" + option);
            }
        });
        price = findViewById(R.id.price);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //这个是获取规格后返回来的数据
                case 1:
                    String optionsJson = (String) msg.obj;
//                    Log.d("OptionsActivity", "1106::::::::::::::"+optionsJson);
                    try {
                        JSONObject jsonObject = new JSONObject(optionsJson);
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            OptionsBean opb = new OptionsBean();
                            opb.setId(array.getJSONObject(i).getString("id"));
                            opb.setTitle(array.getJSONObject(i).getString("title"));
                            opb.setContent(array.getJSONObject(i).getString("content"));
                            opb.setDescription(array.getJSONObject(i).getString("description"));
                            opb.setDisplayorder(array.getJSONObject(i).getString("displayorder"));
                            opb.setDisplaytype(array.getJSONObject(i).getString("displaytype"));
                            opb.setGoodsid(array.getJSONObject(i).getString("goodsid"));
                            opb.setWeid(array.getJSONObject(i).getString("weid"));
                            JSONArray array1 = array.getJSONObject(i).getJSONArray("items");
                            List<OptionsBeanItem> opblist = new ArrayList<>();
                            for (int j = 0; j < array1.length(); j++) {
                                OptionsBeanItem obi = new OptionsBeanItem();
                                JSONObject ob = array1.getJSONObject(j);
                                //拼规格项
                                if (j == 0) {
                                    if (specs.length() > 0) {
                                        specs = specs + "_" + ob.getString("id");
                                    } else {
                                        specs = ob.getString("id");
                                    }
                                }
                                obi.setWeid(ob.getString("weid"));
                                obi.setDisplayorder(ob.getString("displayorder"));
                                obi.setId(ob.getString("id"));
                                obi.setShow(ob.getString("show"));
                                obi.setSpecid(ob.getString("specid"));
                                obi.setThumb(ob.getString("thumb"));
                                obi.setTitle(ob.getString("title"));
                                opblist.add(obi);
                            }
                            opb.setItems(opblist);
                            list.add(opb);
                        }
                        opAdapter.notifyDataSetChanged();
                        getMoney(specs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                //点击以后返回的数据
                case 2:
                    String title_item = (String) msg.obj;
                    String[] arr = title_item.split("==");
//                    Log.i("URL", arr[0] + "==" + arr[1]);
                    Integer indexFu = Integer.parseInt(arr[0]);
                    Integer indexZi = Integer.parseInt(arr[1]);
                    String title = list.get(indexFu).getItems().get(indexZi).getTitle();
                    String id = list.get(indexFu).getItems().get(indexZi).getId();
                    String[] sarr = specs.split("_");
                    for (int i = 0; i < sarr.length; i++) {
                        if (i == 0) {
                            if (indexFu == 0) {
                                specs = id;
                            } else {
                                specs = sarr[i];
                            }
                        } else {
                            if (indexFu == i) {
                                specs = specs + "_" + id;
                            } else {
                                specs = specs + "_" + sarr[i];
                            }
                        }
                    }
                    getMoney(specs);

                    break;
                //不通的规格获取到不通的价格
                case 100:
                    String moneyJson = (String) msg.obj;
                    try {
                        JSONObject jbt = new JSONObject(moneyJson);
                        int status = jbt.getInt("status");
                        if (status == 1) {
                            JSONObject data = jbt.getJSONObject("data");
                            price.setText("￥" + data.getString("marketprice"));
                            Utils.getACache(OptionsActivity.this).put("jbt", moneyJson);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    DODialog.colseDialog(dialog02);
                    break;
            }
        }
    };

    //获取不通规格的价格
    public void getMoney(String spec) {
        DODialog.showDialog(dialog02, OptionsActivity.this);
//        Log.i("URL", "拼接的规格：：" + spec);
        Map<String, String> ma = MapUtilsSetParam.getMap(OptionsActivity.this);
        ma.put("opp", "getOptionPrice");
        ma.put("goodsid", goodsid);
        if (memberid==null){
            memberid="-1";
        }
        ma.put("memberid", memberid);
        ma.put("specs", spec);
        String url = IpConfig.URL + "act=module&name=bj_qmxk&do=app_api&opp=getOptionPrice" + "&" + "goodsid="
                + goodsid + "&" + "memberid=" + memberid + "&" + "specs=" + spec + "&session=" + Utils.getSeesion(OptionsActivity.this);
        Log.i("URL", "getMoney: " + url);
        NetUtils.netWorkByMethodPost(this, ma, IpConfig.URL, handler, ConstantS.GETOPTIONSPRICE);
    }
}
