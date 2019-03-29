package com.myLoan.br.http;

import android.content.Intent;
import android.os.Build;

import com.myLoan.br.Constant;
import com.myLoan.br.MyApplication;
import com.myLoan.br.tools.map.MyLocation;
import com.myLoan.br.tools.loginstate.LogoutState;
import com.igexin.sdk.PushManager;
import com.myLoan.br.R;
import com.myLoan.br.bean.ResponseMy;
import com.myLoan.br.tools.loginstate.LoginContext;
import com.myLoan.br.tools.loginstate.LoginState;
import com.myLoan.br.tools.DebugLog;
import com.myLoan.br.tools.phonestatus.DeviceUtil;
import com.myLoan.br.tools.phonestatus.PackageManagerUtil;
import com.myLoan.br.tools.view.StringUtil;
import com.myLoan.br.tools.view.ToastUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class TokenHeadInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

       /* HashMap<String, String> heads = new HashMap<>();
        heads.put("pm-osversion",String.valueOf(Build.VERSION.SDK_INT));
        heads.put("pm-osdevice", MyApplication.MAC);//设备号
        heads.put("pm-appversion", String.valueOf(MyApplication.APP_VERSIONNAME));
        heads.put("pm-token",""); //token值
        heads.put("pm-uid", "");//用户id*/
        Response response = null;
        Request updateRequest = null;
        try {
            Request request = chain.request();//拿到请求对象
            String userToken = "";
            String userId = "";
            if (LoginContext.getLoginContext().getState() instanceof LoginState
                    && LoginContext.getLoginContext().getUser(MyApplication.mContext) != null) {
                userToken = LoginContext.getLoginContext().getUser(MyApplication.mContext).getToken();
                userId = LoginContext.getLoginContext().getUser(MyApplication.mContext).getId() + "";
                DebugLog.i("wang", "userId:=== " + userId);
            }
            String clientId = PushManager.getInstance().getClientid(MyApplication.mContext);
            DebugLog.i("wang", "============request================");
            updateRequest = request.newBuilder().//用旧的请求对象 生成新的请求对象
                    header("g4-osType", "3").////系统类型，1: 其他，2.iso,3:安卓
                    header("g4-systemVersion", String.valueOf(Build.VERSION.SDK_INT)). //系统版本
                    header("g4-deviceId", DeviceUtil.getUniqueID()).
                    header("g4-clientId", clientId != null ? clientId : "-1").
                    header("g4-uid", userId).
                    header("g4-token", userToken).
//                    header("g4-vest", String.valueOf(Constant.vest)).
        header("g4-deviceType", DeviceUtil.getDeviceName()).
                            header("Accept-Language", StringUtil.getLanguage()).
                            header("g4-longitude", String.valueOf(MyLocation.getInstance().longitude)).
                            header("g4-latitude", String.valueOf(MyLocation.getInstance().latitude)).
                            addHeader("Connection", "close").
                            header("g4-appversion", PackageManagerUtil.getVersionName()).
                            method(request.method(), request.body()).build();
            Connection connection = chain.connection();
            String requestStartMessage = "--> "
                    + request.method()
                    + ' ' + request.url()
                    + (connection != null ? " " + connection.protocol() : "");
            DebugLog.i("wang", "====" + requestStartMessage);
            response = chain.proceed(updateRequest);
            if (response == null) {
                ToastUtil.showToast(MyApplication.getMyApplication().getString(R.string.net_exception));
            }
            ResponseBody responseBody = null;
            if (response != null) {
                DebugLog.i("wang", "============response================");
                responseBody = response.body();
            }
            if (responseBody != null) {
                MediaType mediaType = null;
                if (response.body() != null) {
                    mediaType = response.body().contentType();
                }
                String content = responseBody.string();
                DebugLog.i("wang", "====Interceptor===" + content);
                JSONObject object = new JSONObject(content);
                DebugLog.i("responseBodyy", request.url() + "||" + object.toString());
                int result = object.optInt("status");
                if (result == 401) {// TODO: 2018/11/13  
                    Intent intent = new Intent();
                    intent.setAction("com.panshi.hujin2.loancash.login");
                    if (MyApplication.getMyApplication() != null) {
                        //登出：
                        LoginContext.getLoginContext().setState(new LogoutState());
                        LoginContext.getLoginContext().setUser(null);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //  Constant.LOGIN_OUT = 1;
                        MyApplication.getMyApplication().startActivity(intent);
                    }
                }
                return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (response == null) {
                if (e instanceof SocketTimeoutException) {
                    ResponseMy response1 = new ResponseMy();

                    response1.setCode(318);
                    response1.setMessage("time out exeception");
                    return new Response.Builder().request(updateRequest).protocol(Protocol.HTTP_1_1).code(318).message("time out")
                            .body(MediaTypeUtil.createResponJsonMediaType(GsonUtil.buildGosn().toJson(response1))).build();
                }
            }
        }
        return response;
    }
}
