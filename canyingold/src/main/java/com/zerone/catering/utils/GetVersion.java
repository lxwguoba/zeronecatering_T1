package com.zerone.catering.utils;

import com.zerone.catering.BuildConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class GetVersion  {

    public static Map<String, Object> getASVersionName(){
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        HashMap<String, Object>  ver= new HashMap<>();
        ver.put("vresionCode",versionCode);
        ver.put(versionName,versionName);
        return ver;
    }
}
