package com.myLoan.br.http;

import android.util.Log;

import com.google.gson.JsonParseException;
import com.myLoan.br.MyApplication;
import com.myLoan.br.R;
import com.myLoan.br.bean.ResponeThrowable;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        Log.i("tag", "e.toString = " + e.toString());
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, ERROR.HTTP_ERROR);
            ex.isShow = true;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    ex.message = MyApplication.mContext.getString(R.string.unauthorised_out);
                    break;
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                    ex.message = MyApplication.mContext.getString(R.string.net_timeout);
                case 318:
                    ex.message = MyApplication.mContext.getString(R.string.net_timeout);
                    break;
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    //ex.code = httpException.code();
                    ex.message = MyApplication.mContext.getString(R.string.service_error);
                    break;
            }
            return ex;
        } /*else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } */ else if (e instanceof JsonParseException
                || e instanceof JSONException
                /*|| e instanceof ParseException*/) {
            ex = new ResponeThrowable(e, ERROR.PARSE_ERROR);
            ex.isShow = true;
            ex.message = MyApplication.mContext.getString(R.string.json_parse);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponeThrowable(e, ERROR.NETWORD_ERROR);
            ex.isShow = true;
            ex.message = MyApplication.mContext.getString(R.string.net_connect_failture);
            return ex;
        } else if(e instanceof java.net.SocketException){
            ex = new ResponeThrowable(e, ERROR.NETWORD_TIME_OUT);
            ex.isShow = true;
            ex.message = MyApplication.mContext.getString(R.string.net_exception);
            return ex;

        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeThrowable(e, ERROR.SSL_ERROR);
            ex.isShow = true;
            ex.message = MyApplication.mContext.getString(R.string.net_ssl_error);
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponeThrowable(e, ERROR.NETWORD_TIME_OUT);
            ex.isShow = true;
            ex.message = MyApplication.mContext.getString(R.string.net_timeout);
            return ex;
        }
        ex = new ResponeThrowable(e, ERROR.UNKNOWN);
        ex.isShow = false;
        ex.message = e.toString();
        return ex;

    }

    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;
        /**
         * 网络错误
         */
        public static final int NETWORD_TIME_OUT = 1004;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

    }
}
