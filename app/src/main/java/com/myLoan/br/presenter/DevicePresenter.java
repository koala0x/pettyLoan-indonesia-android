package com.myLoan.br.presenter;

import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.bean.Response;
import com.myLoan.br.bean.UserDeviceDetailVO;
import com.myLoan.br.bean.UserPhoneAddCodeBO;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.listener.contract.DeviceView;
import com.myLoan.br.listener.contract.UserContract;
import com.myLoan.br.modle.DeviceModel;

import java.util.List;

/**
 * @author wzx
 * @version 3.0.0 2018/12/10 17:14
 * @update wzx 2018/12/10 17:14
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class DevicePresenter extends BaseActionPresenter {
    DeviceModel deviceModel;

    @Override
    public void initModle() {
        deviceModel = new DeviceModel();
        regitModel(deviceModel);
    }

    public void getInstalledApkMess(String encodeJson) {
        deviceModel.getInstalledApk(encodeJson, new ModelCallBack<Response>() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Response response) {
                //
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getInatalledApkSuccess();
                }
            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                //
                showToastAction(code, message);
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getInatalledApkFail(code, message);
                }
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }

    public void hardWareMess(String encodeJson) {
        deviceModel.hardwareMess(encodeJson, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getHardWareMessSuccess();
                }
            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getHardWareMessFail(code, message);
                }
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }

    public void getContact(String encodeJson) {
        deviceModel.getContact(encodeJson, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getContactSuccess();
                }
            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getContactFail(code, message);
                }
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }

    public void getSms(String encodeJson) {
        deviceModel.getSms(encodeJson, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getSmsSuccess();
                }
            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getSmsFail(code, message);
                }
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }

    public void getCall(String encodeJson) {
        deviceModel.getCall(encodeJson, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {
                //
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getCallSuccess();
                }
            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getCallFail(code, message);
                }
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }

    public void getDeviceDetail(UserDeviceDetailVO encodeJson) {
        deviceModel.getDeviceDetail(encodeJson, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getDeviceDetailSuccess();
                }
            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getDeviceDetailFail(code, message);
                }
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }

    public void getNewCode(String encodeJson) {
        deviceModel.getNewCode(encodeJson, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getNewCodeSuccess();
                }
            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getNewCodeFail(code, message);
                }
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }

    public void getMessAndCallCount(String messageCount, String callRecordCount) {
        deviceModel.getMessAndCallCount(messageCount, callRecordCount, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getCountSuccess();
                }
            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                if (isViewAttach()) {
                    ((DeviceView) getAttach()).getCountFail(code, message);
                }
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }
}
