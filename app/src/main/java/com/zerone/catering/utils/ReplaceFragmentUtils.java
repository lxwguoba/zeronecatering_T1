package com.zerone.catering.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2017/6/3.
 */

public class ReplaceFragmentUtils {
    /**
     * ��̬���fragment�ķ���
     * @param fragment ��Ҫ��ӵ�framenlayout�ؼ���fragment
     *  @param  ID   ��Ҫ��ӵ��ĸ��ؼ���
     */
    public  static void replaceFragment(Fragment fragment,FragmentManager fragmentManager,int ID){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(ID,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
