package com.myLoan.br.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class GonsonUtil {
    public static Gson buildGosn() {
        GsonBuilder gsonBulder = new GsonBuilder();
        gsonBulder.registerTypeAdapter(String.class, new StringConverter());   //所有String类型null替换为字符串“”
        gsonBulder.registerTypeAdapter(Integer.class, new IntConverter());
        /*gsonBulder.registerTypeAdapter(Integer.class, new IntTypeAdapter());
        gsonBulder.registerTypeAdapter(Long.class, new LongTypeAdapter());
        gsonBulder.registerTypeAdapter(Double.class, new DoubleTypeAdapter());*/
        // gsonBulder.registerTypeAdapter(int.class, INTEGER); //int类型对float做兼容

        //通过反射获取instanceCreators属性
        try {
            Class builder = (Class) gsonBulder.getClass();
            Field f = builder.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBulder);//得到此属性的值
            //注册数组的处理器
            gsonBulder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return gsonBulder.create();
    }
}
