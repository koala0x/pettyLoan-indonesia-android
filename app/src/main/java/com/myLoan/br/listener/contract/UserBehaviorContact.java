package com.myLoan.br.listener.contract;

public interface UserBehaviorContact {
    interface IUserBehavior {
        /**
         * 注册计时
         */
        void regitClick(StringBuilder sbcheck,StringBuilder sbemail,StringBuilder sbphoneNumber,StringBuilder sbregister,StringBuilder sbtopBack,StringBuilder sbBottomback);

        /**
         * 登录计时
         */
        void loginClick(StringBuilder phoneNum,StringBuilder passwd,StringBuilder login,StringBuilder bottomBack);

        /**
         * 修改密码计时
         * page_type 页面类型：1-修改登录密码页面；2-修改支付密码页面；
         * @Param
         */
        void modifyPasswdClick(StringBuilder bottomBack,StringBuilder topBack,StringBuilder firstPassword,StringBuilder secondPassword,StringBuilder nextStep,int type);
    }
}

