package com.zerone.catering.activitys.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.zerone.catering.R;
import com.zerone.catering.activitys.branch.BranchActivity;
import com.zerone.catering.db.impl.BranchDao;
import com.zerone.catering.domain.Branch;
import com.zerone.catering.fragment.order.DaiFuKuangFragment;
import com.zerone.catering.fragment.order.DaiShouHuoFragment;
import com.zerone.catering.fragment.order.QuanBuDingDanFragment;
import com.zerone.catering.fragment.order.TuiKuanZhongFragment;
import com.zerone.catering.fragment.order.YiFuKuanFragment;
import com.zerone.catering.fragment.order.YiGuanBiFragment;
import com.zerone.catering.fragment.order.YiWangChengFragment;
import com.zerone.catering.fragment.order.YuYueFragment;
import com.zerone.catering.refreshandclose.shuaxin.ShuaXinShuJu;
import com.zerone.catering.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/4.
 */

public class OrderControl extends AppCompatActivity implements View.OnClickListener {
    private int indexSelected;
    private Fragment[] mFragments;
    private int mIndex;
    private Button daifukuan;
    private Button yifukuan;
    private Button daishouhuo;
    private Button yiwangcheng;
    private Button tuikuanzhong;
    private Button yiguanbi;
    private Button quanbudingdan;
    private ImageView header;
    private String imgUrl;
    private Branch branch;
    private RadioButton yuyue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neworder_dingdanguanli);
        //防止键盘把控件往上推
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //注册 ，判断是否存在  存在不用注册不存在 在注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        branch = Utils.getBranch(this);
        imgUrl=branch.getThumb();
        initFragment();
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        daifukuan = (Button) findViewById(R.id.daifukuang);
        daifukuan.setOnClickListener(this);
        yifukuan = (Button) findViewById(R.id.yifukuang);
        yifukuan.setOnClickListener(this);
//        daishouhuo = (Button) findViewById(R.id.daishouhuo);
//        daishouhuo.setOnClickListener(this);
        yiwangcheng = (Button) findViewById(R.id.yiwangcheng);
        yiwangcheng.setOnClickListener(this);
//        tuikuanzhong = (Button) findViewById(R.id.tuikuangzhong);
//        tuikuanzhong.setOnClickListener(this);
//        yiguanbi = (Button) findViewById(R.id.yiguangbi);
//        yiguanbi.setOnClickListener(this);
        quanbudingdan = (Button) findViewById(R.id.qubudingdan);
        quanbudingdan.setOnClickListener(this);
        header = (ImageView) findViewById(R.id.header);
        Log.i("URL", "initView: "+imgUrl);
        if (imgUrl != null) {
            Glide.with(OrderControl.this).load(imgUrl).into(header);
        }

        //用来展示预约订单列表
        yuyue = (RadioButton) findViewById(R.id.yuyue);
        yuyue.setOnClickListener(this);

    }

    /**
     * 初始化frgment的
     */
    private void initFragment() {
        //代付款
        DaiFuKuangFragment dfkFragment = new DaiFuKuangFragment();
        //已付款
        YiFuKuanFragment yfkFragment = new YiFuKuanFragment();
        //待收货
        DaiShouHuoFragment dshFragment = new DaiShouHuoFragment();
        //已完成
        YiWangChengFragment ywcFragment = new YiWangChengFragment();
        //退款中
        TuiKuanZhongFragment tkzFragment = new TuiKuanZhongFragment();
        //已关闭
        YiGuanBiFragment ygbFrgment = new YiGuanBiFragment();
        //全部订单
        QuanBuDingDanFragment qbddFragment = new QuanBuDingDanFragment();
        //添加到数组

        //预约订单列表
        YuYueFragment  yuYueFragment=new YuYueFragment();


        mFragments = new Fragment[]{dfkFragment, yfkFragment, dshFragment, ywcFragment,
                tkzFragment, ygbFrgment, qbddFragment,yuYueFragment
        };

        //开启事务
        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.content, dfkFragment).commit();

        //默认设置为第0个
        setIndexSelected(0);
    }

    public void setIndexSelected(int indexSelected) {
        if (mIndex == indexSelected) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if (!mFragments[indexSelected].isAdded()) {
            ft.add(R.id.content, mFragments[indexSelected]).show(mFragments[indexSelected]);
        } else {
            ft.show(mFragments[indexSelected]);
        }
        ft.commit();
        //再次赋值
        mIndex = indexSelected;
    }

    //给每个按钮添加点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.daifukuang:
                setIndexSelected(0);
                break;
            case R.id.yifukuang:
                setIndexSelected(1);
                break;

              //用来展示预约列表
            case R.id.yuyue:
                setIndexSelected(7);
                break;
//              case R.id.daishouhuo:
//                  setIndexSelected(2);
//                  break;
            case R.id.yiwangcheng:
                setIndexSelected(3);
                break;
//              case R.id.tuikuangzhong:
//                  setIndexSelected(4);
//                  break;
//              case R.id.yiguangbi:
//                  setIndexSelected(5);
//                  break;
            case R.id.qubudingdan:
                setIndexSelected(6);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
        //Glide.with(this).pauseRequests();
//        if (Util.isOnMainThread()) {
//            Glide.with(OrderControl.this).pauseRequests();
//        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void ShuaXinShuJu(ShuaXinShuJu shuaXinShuJu) {
        int page = shuaXinShuJu.getPage();
        //页面等等第一个页面
        if (page == 2) {
            //关闭页面 接受到通知 关闭页面
//            Log.i("URL","我已关闭了orderControl页面了，请放心！");
        } else if (page == 4) {
            //这个是订单详情点击返回开单后的按钮  通知Control页面关闭 进入到主页面。
            OrderControl.this.finish();
        }
    }



}
