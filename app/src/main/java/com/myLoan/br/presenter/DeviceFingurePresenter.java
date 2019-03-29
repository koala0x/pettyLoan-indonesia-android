package com.myLoan.br.presenter;

import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.bean.Response;
import com.myLoan.br.bean.ResponseMy;
import com.myLoan.br.bean.UserDeviceDetailVO;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.listener.contract.DeviceView;
import com.myLoan.br.modle.DeviceFingureModel;
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
public class DeviceFingurePresenter extends BaseActionPresenter {
    DeviceFingureModel deviceFingureModel;

    @Override
    public void initModle() {
        deviceFingureModel = new DeviceFingureModel();
        regitModel(deviceFingureModel);
    }

    public void sendDeviceFingure(String blackbox) {
        deviceFingureModel.sendDeviceFingure(blackbox, new ModelCallBack<ResponseMy>() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(ResponseMy response) {
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
}
