package com.zerone.catering.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 */

public class JsonArrayFormatList {

    public static final String START_ARRAY = "[";
    public static final String END_ARRAY = "]";
    @SuppressWarnings("unchecked")
    public static final <T> List<T> json2listT(String jsonStr, Class<T> tC) {
        //json�ַ�������Ϊ��
        if(jsonStr==null) return null;
        //json�ַ�������Ϊ����ڵ�����
        if(!(jsonStr.startsWith(START_ARRAY) && jsonStr.endsWith(END_ARRAY))) return null;
        List<T> listT = null;
        try {
            //�������Ͷ���
            T t =  tC.newInstance();
            //��������ؼ��ط��͵ľ�������
            Class<T> classT = (Class<T>) Class.forName(t.getClass().getName());
            List<Object> listObj = new ArrayList<Object>();
            //������ڵ���json�ַ���ת��Ϊobject����Object��list����
            listObj = new GsonBuilder().create().fromJson(jsonStr, new TypeToken<List<Object>>(){}.getType());
            //ת��δ�ɹ�
            if(listObj == null || listObj.isEmpty()) return null;
            listT = new ArrayList<T>();
            //��Object��list�еĵ�ÿһ��Ԫ���е�json�ַ���ת��Ϊ���ʹ�������ͼ��뷺�ʹ����list���Ϸ���
            for (Object obj : listObj) {
                T perT = new GsonBuilder().create().fromJson(obj.toString(), classT);
                listT.add(perT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listT;
    }
}
