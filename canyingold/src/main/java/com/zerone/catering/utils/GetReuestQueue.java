package com.zerone.catering.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;

import java.io.File;

/**
 * Created by on 2017/10/19 0019 17 14.
 * Author  LiuXingWen
 *
 */

public class GetReuestQueue {
//    /volley本来就不建议用来缓存太多的数据。遇到这种情况，目前的最好的解决方案貌似只有暴力清理缓存。
    private static RequestQueue queue;
    public  static RequestQueue getRequestQueue(Context mContext){
         if (queue==null){
             queue = Volley.newRequestQueue(mContext);
         }
         File cacheDir = new File(mContext.getCacheDir(), "volley");
         DiskBasedCache cache = new DiskBasedCache(cacheDir);
         queue.start();
         // 清除所以volley缓存
         queue.add(new ClearCacheRequest(cache, null));
         return queue;
     }
}
