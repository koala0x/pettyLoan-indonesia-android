package com.myLoan.br;

public class Constant {
    //本地环境 wifi
    public static final String URL = "http://192.168.24.100:9051";
    public static final String WEBURL = "http://192.168.24.100:3008";

    //线上环境
//    public static final String URL = "http://192.168.24.100:9002";
//    public static final String WEBURL = "http://192.168.24.100:3006";

    //预发地址 preproduction
//    public static final String URL = "http://g3api-pre.pandafintech.com.br";
//    public static final String WEBURL = "http://18.231.55.216:9028";

    //刁琪
//      public static final String WEBURL = "http://10.0.52.247:8100";
    //剑康
//    public static final String URL = "http://10.0.52.103:8400";
    //之豪
//    public static final String URL = "http://10.0.52.49:8400";
    //希良
//    public static final String URL = "http://10.0.52.126:8400";


    public static String longitude;
    public static String latitude;
    public static final int successCode = 0;
    public static final String REGEX_PASSWD = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![!#$%^&._*]+$)[\\da-zA-Z!#$%^&._*]{6,18}$";
    public static final String area = "62";
    public static final int PERMISION_REQUEST_LOCATION = 1;
    public static final int PERMISION_REQUEST_PHONSTATE = 2;
    public static boolean startOpenGps = false;

    /**
     * MMKV 标识符
     */
    public static String MMKV_PREFERENCES = "com.myloan.br";

    public static String SP_VERSION_FIRST_START = "version_first_start";

    public static final int VETIFYCODE_TYPE_REGIT = 1;//发送注册类型短信
    public static final int VETIFYCODE_TYPE_RESET_LOGIN = 2;//发送重置登录密码类型短信
    public static final int VETIFYCODE_TYPE_RESET_PAY = 3;//发送重置支付密码类型短信

    public static final int modifyLoginPw = 1;
    public static final int modifyPaypw = 2;

    public static final int vest = 1;//myLoan
    //权限拒绝
    public static final int DENY_PERMISSION = 0;
    //正常返回
    public static final int CALL_BACK_SUESSCODE = 1;
    //请求被取消
    public static final int REQUEST_CANCEL = 2;

    public static class RxBus {
        //登录,退出登录事件流
        public static final int REQUEST_LOGIN = 1;
    }

    public static class Map {
        public static final String REGITED_KEY = "regited";
        public static final int REGITED_VALUE = 1;
    }

    public static class UserBehavior {
        public static final String regitClickPath = "/user/behavior/registerClickTime";
        public static final String loginClickPath = "/user/behavior/loginClickTime";
        public static final String modifyClickPath = "/user/behavior/modifyPasswordClickTime";
    }

    public static class WebPath {
        public static final String ORDER_LIST = WEBURL + "/#/order-list"; //历史订单记录
        public static final String MY_REWARD = WEBURL + "/#/my-reward"; // 我的优惠券
        public static final String SERVICE_CENTER = WEBURL + "/#/service-center"; //帮助中心
        public static final String ABOUT_US = WEBURL + "/#/about-us"; // 关于我们
        public static final String ABOUT_REPAY = WEBURL + "/#/about-repay"; // 关于还款
        public static final String SETTING = WEBURL + "/#/config";
        public static final String REGIT_PROTROL1 = WEBURL + "/#/reg-protocol";
        public static final String REGIT_PROTROL2 = WEBURL + "/#/reg-protocol-two";

        public static final String FEEDBACK = WEBURL + "/#/feedback";


        //首页
        public static final String MAIN_INFORMATION = WEBURL + "/#/msg-center";
        public static final String MAIN_LOAN_PURPOSE = WEBURL + "/#/certification";
        public static final String MAIN_STAGE_REPAY = WEBURL + "/#/order-detail/";//借款详情
        public static final String MAIN_SERVICE_CENTE = WEBURL + "/#/service-center";

        //
        public static final String FORGETPASS = WEBURL + "/#/forget-login/";
    }

}
