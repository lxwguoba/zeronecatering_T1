package com.zerone.catering.utils;

/**
 * Created by Administrator on 2017/6/13.
 */

public class  LogUtil {

    public static void info(boolean show, String...message){
        if(show){
            info(message);
        }
    }

    /**
     * ��¼��ͨ����־��Ϣ
     *
     * @param message
     */
    public static void info(String... message) {
        StringBuffer info = new StringBuffer();
        for (int i = 0; i < message.length; i++) {
            info.append(message[i]);
        }
        System.out.println(info.toString());
    }

    /**
     * ��¼�������־��Ϣ
     *
     * @param message
     */
    public static void error(String message) {
        System.err.println(message);
    }

    /**
     * ��¼�������־��Ϣ
     *
     * @param message
     * @param e
     */
    public static void error(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
    }

    /**
     * ��¼�������־��Ϣ
     *
     * @param message
     * @param e
     */
    public static void error(String message, Throwable e) {
        System.err.println(message);
        e.printStackTrace();
    }
}
