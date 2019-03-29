package com.myLoan.br.listener.contract;

public interface CheckContract {
    interface IcheckPhoneView {
          void regited();

    }
    interface IVetifyCodeView{
        void vetifySucee();
    }
    interface IcheckPhone{
        /**
         * 校验手机是否注册
         * @param area  地区
         * @param phone 手机号
         * @param email email
         * @param cpf cpf
         */
        void checkIsRegit(String area, String phone,String email,String cpf);

        /**
         * 发送短信
         * @param area
         * @param phone
         * @param type 类型 1注册、2重置登录、3重置支付
         */
        void sendVetifyCode(String area,String phone,int type);
    }

}
