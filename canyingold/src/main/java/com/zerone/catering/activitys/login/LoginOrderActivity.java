package com.zerone.catering.activitys.login;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zerone.catering.R;
import com.zerone.catering.base.BaseActvity;
import com.zerone.catering.activitys.branch.BranchActivity;
import com.zerone.catering.constant.IpConfig;
import com.zerone.catering.domain.UserInfo;
import com.zerone.catering.handler.LoginHandler;
import com.zerone.catering.refreshandclose.CloseLoginActvity;
import com.zerone.catering.thread.LoginInitThread;
import com.zerone.catering.utils.ACache;
import com.zerone.catering.utils.Base64Util;
import com.zerone.catering.utils.GetGson;
import com.zerone.catering.utils.NetUtils;
import com.zerone.catering.utils.SharedPreferencesUtils;
import com.zerone.catering.utils.ShowMessage;
import com.zerone.catering.utils.Utils;
import com.zerone.catering.utils.net.HttpRequestDealOutTime;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginOrderActivity extends BaseActvity{
    private Button login_btn;
    private LinearLayout layout;
    private RequestQueue mQueue;
    private  static  final int LOADINGDATA=10;
    private  static  final int LOADINGDATAGET=11;
    private  static  final int RESPONSE_ERROR=1;
    private EditText userName1;
    private EditText passwrod1;
    private CheckBox rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.acticity_login_second);
        //注册广播
        //注册 ，判断是否存在  存在不用注册不存在 在注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
    }
        DisplayMetrics dm = new DisplayMetrics();
        mQueue = Volley.newRequestQueue(this);
        iniView();
        loadNameAndPasswrod();
        showCustomViewDialog();
    }
    //声明一个AlertDialog构造器
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private void showCustomViewDialog(){
        builder=new AlertDialog.Builder(this);
        /**
         * 设置内容区域为自定义View
         */
        RelativeLayout loginDialog= (RelativeLayout ) getLayoutInflater().inflate(R.layout.activity_loginprogressbar,null);
        builder.setView(loginDialog);
        builder.setCancelable(true);
        dialog = builder.create();

    }
    /**
     *
     */
    private void loadNameAndPasswrod() {
        LoginHandler handler = new LoginHandler(this);
        Thread thread = new Thread(new LoginInitThread(this, handler));
        thread.start();
    }

    private void iniView() {
        login_btn = (Button) findViewById(R.id.login_btn);
        userName1 = (EditText)findViewById(R.id.username);
        passwrod1 = (EditText)findViewById(R.id.password);
        rb = (CheckBox)findViewById(R.id.rm_user);

        //登录按钮
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= userName1.getText().toString().trim();
                String pwd= passwrod1.getText().toString();
                if (name!=null && "".equals(name)){
                    Toast.makeText(LoginOrderActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( pwd!=null &&"".equals( pwd)){
                    Toast.makeText(LoginOrderActivity.this, "密码不能为", Toast.LENGTH_SHORT).show();
                    return;
                }
                String pw= null;
                try {
                    pw = Base64Util.encrypt(pwd.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String nameAndPassword = name+"=="+pw;
                if ( rb.isChecked()){
                    SharedPreferencesUtils.saveNameAndPassword(nameAndPassword,LoginOrderActivity.this);
                }
                String url = IpConfig.URL+"act=module&name=bj_qmxk&do=app_api&opp=login&username="+name+"&password="+pwd;

                 //判读是否有网络连接了
                if (Utils.checkNetworkConnection(LoginOrderActivity.this)){
                    dialog.show();
                    //这是对对话框进行参数的设置
                    WindowManager wm = getWindowManager();
                    Display display = wm.getDefaultDisplay();
                    WindowManager.LayoutParams params =dialog.getWindow().getAttributes();
                    params.width=400;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(params);
                        //点击外部不消失
                    dialog.setCanceledOnTouchOutside(false);
//                    NetUtils.netWorkByMethodGet(LoginOrderActivity.this,url,handlerMsg ,LOADINGDATAGET);
                    HttpRequestDealOutTime.HttpGet(url,LoginOrderActivity.this,handlerMsg,LOADINGDATAGET);
                }else {
                    Toast.makeText(LoginOrderActivity.this, "没有连接网络，请检查是否连接wifi或打开数据流量", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

         //清空密码
        findViewById(R.id.clear_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                  message.what=12;
                handlerMsg.sendMessage(message);
            }
        });
        //清空用户名
        findViewById(R.id.clear_username).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=13;
                handlerMsg.sendMessage(message);
            }
        });

        //记住用户名
        findViewById(R.id.changecheckbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what=14;
                handlerMsg.sendMessage(message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除订阅
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    Handler handlerMsg = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case RESPONSE_ERROR:
                    String error = (String) msg.obj;
                    Log.i("TAG",error);
                    break;
                case LOADINGDATA:
                    String jsonString = (String) msg.obj;
                    break;
                case LOADINGDATAGET:
                    String jsonSt = (String) msg.obj;
//                    Log.i("URL","login="+jsonSt);
                    try {
                        JSONObject jsonObject  = new JSONObject(jsonSt);
                        int loginstatus = jsonObject.getInt("loginstatus");
                    if (loginstatus==1){
                        Gson gson= GetGson.getGson();
                        UserInfo user= gson.fromJson(jsonSt, UserInfo.class);
                        ACache mCache = ACache.get(LoginOrderActivity.this);
//                        Log.d("URL",user.getSession());
                        //移除缓存中的seesion
                        String session = mCache.getAsString("session");
                        mCache.remove("session");
//                        if (session!=null){
////                            Log.i("session",session);
//                        }
                        mCache.put("session",user.getSession());
                        String session2 = mCache.getAsString("session");
                        Intent intent =new Intent(LoginOrderActivity.this, BranchActivity.class);
                        startActivity(intent);
//                        finish();
                        //这个由于session会过期  所以登录后只有获取了分店数据后才算登录成功。
//                        ShowMessage.showMessage(LoginOrderActivity.this,user.getRemark());
                        if (dialog!=null){
                            dialog.dismiss();
                        }

                    }else {
                        dialog.dismiss();
                        ShowMessage.showMessage(LoginOrderActivity.this,"登录失败");
                    }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case 12:
                    passwrod1.setText("");
                    break;

                case 13:
                    userName1.setText("");
                    break;

                case 14:
                       //记住密码框的切换
                       if (rb.isChecked()){
                           rb.setChecked(false);
                       }else {
                           rb.setChecked(true);
                       }

                    break;
            }
        }
    };

    /**
     * 接收通知用的方法是eventbus的框架
     * @param cla
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void CloseLoginActvity(CloseLoginActvity cla) {
        int page = cla.getPage();
        //页面等等第一个页面
        if (page==1){
            //关闭页面 接受到通知 关闭页面
            LoginOrderActivity.this.finish();
        }
    }
}
