package com.myLoan.br.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class IntConverter implements JsonSerializer<Integer>{
    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        if(src==null){
            return new JsonPrimitive(0);
        }else{
            return new JsonPrimitive(src);
        }
    }
    public Integer deserialize(JsonElement json, Type typeOfT,
                              JsonDeserializationContext context)
            throws JsonParseException {
        return json.getAsJsonPrimitive().getAsInt();
    }

 /*   @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        if(src==null){
            return new JsonPrimitive("");
        }else{
            return new JsonPrimitive(src.toString());
        }
    }

    public String deserialize(JsonElement json, Type typeOfT,
                              JsonDeserializationContext context)
            throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString();
    }*/
}
