package com.myLoan.br.listener.contract;

import java.io.File;

public interface UserContract {
    /**
     * 用户接口
     */
    interface IUser {
        /**
         * 上传用户图像
         * @param file
         */
        void uploadHead(File file);

        /**
         * 忘记登录密码
         * @param areaCode
         * @param phoneNumber
         * @param verification
         * @param password
         * @param area
         */
        void forgetLogin(String areaCode,String phoneNumber,String verification,String password,String area);
    }
    interface IUserHeadView{
        /**
         * 上传图像
         */
        void uploadHeadSucess(String url);
    }
    interface IForgetLoginPwView{
        /**
         * 忘记密码成功
         */
        void forgetSucess();
    }
}
