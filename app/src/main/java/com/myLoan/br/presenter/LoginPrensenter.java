package com.myLoan.br.presenter;

import com.myLoan.br.Constant;
import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.bean.LoginBean;
import com.myLoan.br.bean.MsgEvent;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.listener.contract.LoginContract;
import com.myLoan.br.tools.loginstate.LoginContext;
import com.myLoan.br.tools.loginstate.LoginModel;
import com.myLoan.br.tools.loginstate.LoginState;
import com.myLoan.br.tools.loginstate.LogoutState;
import com.myLoan.br.tools.RxBus;

import java.util.List;

/**
 * 登录控制器
 */
public class LoginPrensenter extends BaseActionPresenter implements LoginContract.ILogin {
    private LoginModel loginModel;

    @Override
    public void initModle() {
        loginModel = new LoginModel();
        regitModel(loginModel);
    }


    @Override
    public void login(String areaCode, String passwd, String phoneNum) {
        loginModel.login(areaCode, passwd, phoneNum, new ModelCallBack<LoginBean.DataBean>() {
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

            }

            @Override
            public void callBackBean(LoginBean.DataBean dataBean) {
                LoginContext.getLoginContext().setState(new LoginState());
                LoginContext.getLoginContext().setUser(dataBean);
                MsgEvent msgEvent = new MsgEvent(Constant.RxBus.REQUEST_LOGIN, 0, "");
                RxBus.getDefault().post(msgEvent);
                if (isViewAttach()) {
                    ((LoginContract.ILoginView) getAttach()).loginSucess();
                }
            }

            @Override
            public void callBackList(List<LoginBean.DataBean> list) {

            }
        });
    }

    @Override
    public void loginOut() {
        loginModel.logout(new ModelCallBack() {
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
              showToastAction(code,message);
            }

            @Override
            public void callBackBean() {
//                LoginContext.getLoginContext().setState(new LogoutState());
//                LoginContext.getLoginContext().setUser(null);
//                MsgEvent msgEvent = new MsgEvent(Constant.RxBus.REQUEST_LOGIN, 0, "");
//                RxBus.getDefault().post(msgEvent);
                if(isViewAttach()){
                    ((LoginContract.ILoginView)getAttach()).logoutSucess();
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
