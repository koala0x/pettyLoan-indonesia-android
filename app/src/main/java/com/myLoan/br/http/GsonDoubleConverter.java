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

public class GsonDoubleConverter implements JsonSerializer<Double> {
    @Override
    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return new JsonPrimitive(0);
        } else {
        //    BigDecimal bigDecimal=new BigDecimal(String.valueOf(src));
            return new JsonPrimitive(src);
        }
    }

    public Double deserialize(JsonElement json, Type typeOfT,
                               JsonDeserializationContext context)
            throws JsonParseException {
        return json.getAsJsonPrimitive().getAsDouble();
    }
}
