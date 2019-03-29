package com.myLoan.br.http.api;

import com.myLoan.br.bean.DetectLivenessResponse;
import com.myLoan.br.bean.HelpContentBean;
import com.myLoan.br.bean.LoginBean;
import com.myLoan.br.bean.MainBean;
import com.myLoan.br.bean.Response;
import com.myLoan.br.bean.ResponseMy;
import com.myLoan.br.bean.VersionBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface User {
    /**
     * 检查是否注册
     *
     * @param areaCode
     * @param phoneNumber
     * @param email
     * @param cpf
     * @return
     */
    @GET("/user/register/check")
    Observable<Response> checkPhoneRegit(@Query("areaCode") String areaCode, @Query("phoneNumber") String phoneNumber, @Query("email") String email, @Query("cpf") String cpf);

    /**00.
     * 注册
     *
     * @param body
     * @return
     */
    @POST("/user/register")
    Observable<LoginBean> regit(@Body RequestBody body);

    /**
     * 登录
     *
     * @param body
     * @return
     */
    @POST("/user/login")
    Observable<LoginBean> login(@Body RequestBody body);

    @DELETE("/user/logout")
    Observable<Response> logout();

    /**
     * 获取验证码
     *
     * @param areaCode         区位号
     * @param phoneNumber      手机号
     * @param verificationType 类型  1注册、2重置登录、3重置支付
     * @return
     */
    @GET("/verification/code")
    Observable<Response> getVerifyCode(@Query("areaCode") String areaCode, @Query("phoneNumber") String phoneNumber, @Query("verificationType") int verificationType);

    /**
     * 页面计时控制器
     *
     * @param path 路径
     * @param body 请求body
     * @return
     */
    @POST
    Observable<Response> clickTime(@Url String path, @Body RequestBody body);

    /**
     * 邀请码 填写
     *
     * @param vertifCode
     * @return
     */
    @PUT("/user/register/invitationCode")
    Observable<Response> inviteCode(@Query("invitationCode") String vertifCode);

    /**
     * 更新用户头像
     *
     * @param body
     * @return
     */
    @POST("/user/info/head/portrait")
    Observable<ResponseMy> postHeadImage(@Body RequestBody body);

    /**
     * 忘记登录密码
     *
     * @param body
     * @return
     */
    @PATCH("/user/login/password/forget")
    Observable<LoginBean> forgetLoginPw(@Body RequestBody body);

    @GET("/homePage")
    Observable<MainBean> getHomePage();

    @GET("/system/apkVersion")
    Observable<VersionBean> getUpdateVersion();

    @POST("/user/face/saveImg")
    Observable<Response> uploadFaceFile(@Body RequestBody body);

    @GET("/system/help/center")
    Observable<HelpContentBean> getHelpCenter();

    @PATCH("/system/help/center/chose")
    Observable<Response> getHelpCenterChose(@Body RequestBody body);

    @Multipart
    @POST("/system/opinion")
    Observable<ResponseBody> opinion(@Part("title") RequestBody titlebody,
                                     @Part("content") RequestBody contentbody,
                                     @Part("phoneNumber") RequestBody phoneNumberbody,
                                     @Part List<MultipartBody.Part> parts);

    @POST("/user/authentication/detectLiveness")
    Observable<DetectLivenessResponse> detectLiveness(@Body RequestBody body);
//    @Multipart
//    @POST("/system/opinion")
//    Observable<ResponseBody> opinion(@Body RequestBody Body, @Part MultipartBody.Part[] parts);

    @GET("/verification/code/check")
    Observable<ResponseMy> codeCheck(@Query("areaCode") String areaCode,
                                       @Query("phoneNumber") String phoneNumber,
                                       @Query("verificationCode") String verificationCode,
                                       @Query("verificationType") int verificationType);

}
