package com.myLoan.br.http;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class MediaTypeUtil {
    public static RequestBody createJsonMediaType(String jsonStr ){
        return  RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonStr);
    }
    public static ResponseBody createResponJsonMediaType(String jsonStr ){
        return  ResponseBody.create(MediaType.parse("application/json; charset=utf-8"),jsonStr);
    }
}
