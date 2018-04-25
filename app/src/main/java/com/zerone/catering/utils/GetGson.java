package com.zerone.catering.utils;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/6/24.
 */

public class GetGson  {

    /**
     * ��ȡGson����
     * @return
     */
   public static Gson getGson(){
       // ʹ��new����
       Gson gson = new Gson();
       return gson ;
   }
}
