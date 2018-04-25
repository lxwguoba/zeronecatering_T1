package com.zerone.catering.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zerone.catering.constant.ConfigS;

/**
 * ��
 * Created by Administrator on 2017/6/12.
 *  1. MODE_APPEND:
 *  2. MODE_PRIVATE:
 *  4.MODE_WORLD_WRITEABLE:
 *  5. MODE_MULTI_PROCESS: �
 */

public class SharedPreferencesUtils {
    /**
     * �����û���������"=="����
     * @param nameAndPassword  �û�������
     * @param context ������
     */
    public static void saveNameAndPassword(String nameAndPassword, Context context){
        SharedPreferences preferences = context.getSharedPreferences(ConfigS.NAMEANDPASSWORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ConfigS.NAMEANDPASSWORD, nameAndPassword);
        editor.commit();
    }

    /**
     * ��ȡ�û���������
     * @param context
     * @return
     */
    public static String getNameAndPassword(Context context){
        SharedPreferences preferences = context.getSharedPreferences(ConfigS.NAMEANDPASSWORD, Context.MODE_PRIVATE);
        String nameAndPasswrod=  preferences.getString(ConfigS.NAMEANDPASSWORD,null);
         return  nameAndPasswrod;
    }

}
