package com.myLoan.br.listener.contract;

import android.app.Dialog;

public interface RegiterContract {
    interface IRegi {
        /**
         * 注册
         *
         * @param areaCode
         * @param phoneNum
         * @param passwd
         * @param verityCode
         */
        void regit(String areaCode, String phoneNum, String passwd, String verityCode, String area);

        /**
         * 填写邀请码
         *
         * @param invateCode
         */
        void saveInvateCode(String invateCode,  Dialog dialog);

        /**
         * 倒计时
         *
         * @param second
         */
        void downTimer(int second);


        //验证码检验
        void codeCheck(String areaCode, String phoneNumber, String verificationCode, int verificationType);
    }

    interface IRegirView {
        /**
         * 注册成功回调
         */
        void regitSucess();

        /**
         * 倒计时 view 更新
         *
         * @param second
         */
        void downTimker(long second);

        /**
         * 完成倒计时
         */
        void finshDownTinker();

        void codeCheckSuccess();

    }
}
