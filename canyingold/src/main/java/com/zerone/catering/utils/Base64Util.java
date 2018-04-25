package com.zerone.catering.utils;

import android.util.Base64;

/**
 * <b>BASE64�ı���ͽ���<b>
 * <p>
 * BASE64���ǽ�3���ֽڵ�����ת��4���ֽڵ����ݡ�<br/>
 * ����3���ֽڹ���24λ(3*8=24),Ҫ���4���ֽڣ���32λ������Ҫ����8��0��<br/>
 * ��ˣ���ÿ��6��λ��ǰ�油00���ɣ�����4��00֮�� �����4���ֽڡ�<br/>
 * ����:<br/>
 * 0xFF 0xFF 0xFF<br/>
 * ��ɶ�����Ϊ:<br/>
 * 1111 1111 1111 1111 1111 1111<br/>
 * ǰ�油��00�����<br/>
 * 0011 1111 0011 1111 0011 1111 0011 1111<br/>
 * ת��ʮ�����ƣ�<br/>
 * 0x3F 0x3F 0x3F 0x3F<br/>
 * ת��ʮ����Ϊ:<br/>
 * 63 63 63 63<br/>
 * ����BASE64��ӳ���(A-Za-z0-9+/=)��ӳ����ַ�����Ϊ////
 * </p>
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
