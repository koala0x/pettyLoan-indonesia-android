package com.myLoan.br.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class GsonUtil {
    public static Gson buildGosn() {
        GsonBuilder gsonBulder = new GsonBuilder();
        gsonBulder.registerTypeAdapter(String.class, new GsonStringConverter());   //所有String类型null替换为字符串“”
        gsonBulder.registerTypeAdapter(Integer.class, new GsonIntConverter());
    //    gsonBulder.registerTypeAdapter(Integer.class, new GsonDoubleConverter());
        return gsonBulder.create();
    }
}
