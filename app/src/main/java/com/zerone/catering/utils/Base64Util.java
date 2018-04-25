package com.zerone.catering.utils;

import android.util.Base64;

/**
 *
 * Created by Administrator on 2017/6/13.
 */

public class Base64Util{

    /**
     * <b>���ַ�������BASE64��ʽ�ı���<b>
     *
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(String src) throws Exception {
        return Base64.decode(src.getBytes(), Base64.DEFAULT);
    }

    /**
     * <b>��BASE64���ܵ��ַ������н���</b>
     *
     * @return
     * @throws Exception
     */
    public static String encrypt(byte[] src) throws Exception {
        return Base64.encodeToString(src, Base64.DEFAULT);
    }

}
