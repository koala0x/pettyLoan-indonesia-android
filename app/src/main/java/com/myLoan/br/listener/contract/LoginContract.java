package com.myLoan.br.listener.contract;

public interface LoginContract {

    interface ILogin {
        /**
         * 登录
         * @param areaCode 区位号
         * @param passwd 密码
         * @param phoneNum 手机号
         */
        void login(String areaCode,String passwd,String phoneNum);

        void loginOut();
    }

    interface ILoginView {
       void loginSucess();
       void logoutSucess();
    }

}
