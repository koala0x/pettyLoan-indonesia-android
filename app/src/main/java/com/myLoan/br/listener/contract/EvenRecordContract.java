package com.myLoan.br.listener.contract;

public interface EvenRecordContract  {
    interface IEvenRecord{
        /**
         * 登录事件
         */
        void loginEven();

        /**
         * 注册事件
         */
        void regitEven();

        /**
         * 下单成功事件
         */
        void orderSucessEven();
    }
}
