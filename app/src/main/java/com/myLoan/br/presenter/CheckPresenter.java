package com.myLoan.br.presenter;

import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.listener.contract.CheckContract;
import com.myLoan.br.modle.CheckModel;

import java.util.List;

/**
 * 校验相关接口
 */
public class CheckPresenter extends BaseActionPresenter implements CheckContract.IcheckPhone {
    private CheckModel mcheckModel;

    @Override
    public void initModle() {
        mcheckModel = new CheckModel();
        regitModel(mcheckModel);
    }

    @Override
    public void checkIsRegit(String area, String phone, String email, String cpf) {
        mcheckModel.checkPhone(area, phone, email, cpf, new ModelCallBack() {

            @Override
            public void showDialoging() {
                showLoadDialogAction();
            }

            @Override
            public void hiddenDialoging() {
                disLoadDilogAction();
            }

            @Override
            public void failture(int code, String message) {
                showToastAction(code, message);
            }

            @Override
            public void callBackBean() {
                if (isViewAttach()) {
                    ((CheckContract.IcheckPhoneView) getAttach()).regited();
                }
            }

            @Override
            public void callBackBean(Object o) {

            }

            @Override
            public void callBackList(List list) {

            }
        });
    }

    @Override
    public void sendVetifyCode(String area, String phone, int type) {
        mcheckModel.sendVetifyCode(area, phone, type, new ModelCallBack() {
            @Override
            public void showDialoging() {
                showLoadDialogAction();
            }

            @Override
            public void hiddenDialoging() {
                disLoadDilogAction();
            }

            @Override
            public void failture(int code, String message) {
                showToastAction(code, message);
            }

              @Override
              public void callBackBean() {
                     if(isViewAttach()){
                         ((CheckContract.IVetifyCodeView)getAttach()).vetifySucee();
                     }
              }

            @Override
            public void callBackBean(Object o) {

            }

            @Override
            public void callBackList(List list) {

            }
        });
    }
}
