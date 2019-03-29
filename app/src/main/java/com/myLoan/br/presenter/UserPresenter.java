package com.myLoan.br.presenter;

import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.bean.LoginBean;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.listener.contract.UserContract;
import com.myLoan.br.tools.loginstate.LoginContext;
import com.myLoan.br.modle.UserModel;

import java.io.File;
import java.util.List;

public class UserPresenter extends BaseActionPresenter implements UserContract.IUser {
    private UserModel userModel;

    @Override
    public void initModle() {
        userModel = new UserModel();
        regitModel(userModel);
    }

    @Override
    public void uploadHead(File file) {
        userModel.postHeadImage(file, new ModelCallBack<String>() {
            @Override
             public void callBackBean() {

            }

            @Override
            public void callBackBean(String url) {
                if(isViewAttach()){
                    ((UserContract.IUserHeadView)getAttach()).uploadHeadSucess(url);
                }
            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {

            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }

    @Override
    public void forgetLogin(String areaCode, String phoneNumber, String verification, String password, String area) {
        userModel.updateLoginPw(areaCode, phoneNumber, verification, password, area, new ModelCallBack<LoginBean.DataBean>() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(LoginBean.DataBean dataBean) {
                LoginContext.getLoginContext().setUser(dataBean);
                if(isViewAttach()){
                    ((UserContract.IForgetLoginPwView)getAttach()).forgetSucess();
                }

            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                    showToastAction(code,message);
            }

            @Override
            public void showDialoging() {
                showLoadDialogAction();
            }

            @Override
            public void hiddenDialoging() {
                disLoadDilogAction();
            }
        });
    }
}
