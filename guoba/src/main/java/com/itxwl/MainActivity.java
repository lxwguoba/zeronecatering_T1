package com.itxwl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itxwl.application.MyApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        volley_Get();
        volley_Post();
    }

    //post的请求
    private void volley_Post() {
        String url = "http://lkxw.info/xingwen/getuser";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("TAG", "onResponse: "+s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("TAG", "onResponse: "+volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user", "刘亦菲");
                map.put("password", "520520");
                return map;
            }
        };

        MyApplication.getQueues().add(request);
    }

    private void volley_Get() {
        String url = "";
        //01
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                //成功的回调
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                //失败的回调
//            }
//        });
//
//        //给request 设置标签 以便操作
//        request.setTag("main");
//        //把请求加入队列中
//        MyApplication.queues.add(request);
//        02
//int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //这里返回来的是jsonobject对象可以直接获取
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //这个是和activity联动
        MyApplication.getQueues().cancelAll("main");
    }
}
