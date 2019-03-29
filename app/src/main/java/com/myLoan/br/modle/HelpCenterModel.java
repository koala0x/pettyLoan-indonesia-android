package com.myLoan.br.modle;

import android.util.Log;

import com.myLoan.br.Constant;
import com.myLoan.br.base.BaseModel;
import com.myLoan.br.bean.HelpChoseDTO;
import com.myLoan.br.bean.HelpContentBean;
import com.myLoan.br.bean.Response;
import com.myLoan.br.http.CusumeObserver;
import com.myLoan.br.http.GsonUtil;
import com.myLoan.br.http.MediaTypeUtil;
import com.myLoan.br.http.RetrofitSingleton;
import com.myLoan.br.http.api.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author wzx
 * @version 3.0.0 2019/2/12 17:26
 * @update wzx 2019/2/12 17:26
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class HelpCenterModel extends BaseModel {
    public void getHelpCenter(final HelpCenterCallback helpCenterCallback) {
        RetrofitSingleton.getInstance()
                .getRetrofit().create(User.class)
                .getHelpCenter()
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<HelpContentBean>() {
                    @Override
                    public boolean test(HelpContentBean response) throws Exception {
                        if (response.getCode() == Constant.successCode) {
                            return true;
                        } else {
                            helpCenterCallback.getHelpcenterFail(response.getMessage());
                            return false;
                        }


                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CusumeObserver<HelpContentBean>() {
                    @Override
                    public void onNext(HelpContentBean response) {
                        helpCenterCallback.getHelpcenterSuccess(response.getData());
                        super.onNext(response);
                    }

                });
    }


    public void getHelpCenterChose(int helpId, String usefulEnum, final HelpCenterChoseCallback helpCenterChoseCallback) {
        HelpChoseDTO helpChoseDTO = new HelpChoseDTO();
        helpChoseDTO.setHelpId(helpId);
        helpChoseDTO.setUsefulEnum(usefulEnum);

        RetrofitSingleton.getInstance().getRetrofit().create(User.class).getHelpCenterChose(MediaTypeUtil.createJsonMediaType(GsonUtil.buildGosn().toJson(helpChoseDTO))).subscribeOn(Schedulers.io()).filter(new Predicate<Response>() {
            @Override
            public boolean test(Response response) throws Exception {
                if (response.getCode() == Constant.successCode) {
                    return true;
                } else {
                    helpCenterChoseCallback.getHelpcenterChoseFail(response.getMessage());
                    return false;
                }

            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<Response>() {
            @Override
            public void onNext(Response response) {
                helpCenterChoseCallback.getHelpcenterChoseSuccess();
                super.onNext(response);
            }

        });
    }

    public void feedback(String title, String content, String phoneNumber, List<File> photoFiles) {
        RequestBody titlerequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody contentrequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), content);
        RequestBody phoneNumberrequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), phoneNumber);
//        RequestBody imagerequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
        List<MultipartBody.Part> parts = new ArrayList<>();
        MultipartBody.Part[] potoparts = new MultipartBody.Part[photoFiles.size()];

        for (int i = 0; i < photoFiles.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), photoFiles.get(i));
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", photoFiles.get(i).getName(), requestBody);
            parts.add(part);
//            potoparts[i] = part;
        }
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("title", title)
                .addFormDataPart("content", content)
                .addFormDataPart("phoneNumber", phoneNumber)
//                .addFormDataPart("image", parts))
                .build();

        RetrofitSingleton.getInstance().getRetrofit().create(User.class).opinion(titlerequestBody, contentrequestBody, phoneNumberrequestBody, parts).subscribeOn(Schedulers.io()).filter(new Predicate<ResponseBody>() {
            @Override
            public boolean test(ResponseBody response) throws Exception {
//                if (response.getCode() == Constant.successCode) {
//                    return true;
//                } else {
//                    return false;
//                }
                return true;

            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new CusumeObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody response) {
                super.onNext(response);
                try {
                    Log.i("wzxfeedback", response.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public interface HelpCenterCallback {
        void getHelpcenterSuccess(List<HelpContentBean.DataBean> dataBeans);

        void getHelpcenterFail(String massage);

    }

    public interface HelpCenterChoseCallback {
        void getHelpcenterChoseSuccess();

        void getHelpcenterChoseFail(String massage);

    }


}
