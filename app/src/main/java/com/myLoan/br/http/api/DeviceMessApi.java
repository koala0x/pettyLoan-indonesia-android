package com.myLoan.br.http.api;

import com.myLoan.br.bean.Response;
import com.myLoan.br.bean.ResponseMy;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author wzx
 * @version 3.0.0 2018/12/10 14:40
 * @update wzx 2018/12/10 14:40
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public interface DeviceMessApi {

    //保存已安装APP名称
    @POST("/user/phone/app")
    Observable<Response> getInstalledApp(@Body RequestBody body);

    //保存手机硬件信息
    @POST("/user/phone/hardware")
    Observable<ResponseMy> hardwareMess(@Body RequestBody body);


    //保存手机通讯录信息
    @POST("/user/phone/contact")
    Observable<ResponseMy> getContact(@Body RequestBody body);


    //手机手机短信信息
    @POST("/user/phone/sms")
    Observable<ResponseMy> getSms(@Body RequestBody body);

    //保存手机通话记录信息
    @POST("/user/phone/call")
    Observable<ResponseMy> getCall(@Body RequestBody body);


    //保存手机通话记录信息
    @POST("/user/device/detail")
    Observable<ResponseMy> getDeviceDetail(@Body RequestBody body);

    //保存手机通话记录信息
    @POST("/user/phone/newCode")
    Observable<ResponseMy> getNewCode(@Body RequestBody body);


    //保存短信条数
    @POST("/user/callrecord/count")
    Observable<ResponseMy> getMessAndCallCount(@Query("messageCount") String messageCount,@Query("callRecordCount") String callRecordCount);

    //保存短信条数
    @POST("/user/device/saveDeviceFingure")
    Observable<ResponseMy> saveDeviceFingure(@Body RequestBody body);

}
