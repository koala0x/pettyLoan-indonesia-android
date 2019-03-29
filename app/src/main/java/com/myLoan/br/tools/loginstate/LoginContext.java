package com.myLoan.br.tools.loginstate;

import android.content.Context;

import com.myLoan.br.Constant;
import com.myLoan.br.bean.LoginBean;
import com.tencent.mmkv.MMKV;

/**
 * @author wzx
 * @version 3.0.0 2018/11/23 14:13
 * @update wzx 2018/11/23 14:13
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class LoginContext {
    private static LoginContext loginContext;

    public static final String MMKV_USER_ID = "userId";
    public static final String MMKV_PHONE_NUMBER = "phoneNumber";
    public static final String MMKV_TOKEN = "token";

    private UserState mState = new LogoutState();
//    private LoginBean.DataBean mUser;

    public static LoginContext getLoginContext() {
        if (loginContext == null) {
            loginContext = new LoginContext();
        }
        return loginContext;
    }

    public void setState(UserState mState) {
        this.mState = mState;
    }

    public UserState getState() {
        return mState;
    }

    /**
     * 是否是登录状态
     *
     * @return
     */
    public boolean isLogin() {
        return mState instanceof LoginState;
    }

    private static LoginBean.DataBean mUser;

    public void setUser(LoginBean.DataBean mUser) {
//        if (mUser == null) {
//            MMKV mMkv = MMKV.mmkvWithID(Constant.MMKV_PREFERENCES, MMKV.SINGLE_PROCESS_MODE);
//            mMkv.remove(MMKV_USER_ID);
//            mMkv.remove(MMKV_PHONE_NUMBER);
//            mMkv.remove(MMKV_TOKEN);
//        } else {
//            MMKV mMkv = MMKV.mmkvWithID(Constant.MMKV_PREFERENCES, MMKV.SINGLE_PROCESS_MODE);
//            mMkv.encode(MMKV_USER_ID, mUser.getId() + "");
//            mMkv.encode(MMKV_PHONE_NUMBER, mUser.getPhoneNumber());
//            mMkv.encode(MMKV_TOKEN, mUser.getToken());
//        }


        this.mUser = mUser;
    }

    public LoginBean.DataBean getUser(Context context) {
//        if (mUser == null) {
//            MMKV mMkv = MMKV.mmkvWithID(Constant.MMKV_PREFERENCES, MMKV.SINGLE_PROCESS_MODE);
//            if (mMkv.decodeString(MMKV_TOKEN, null) != null) {
//                mUser = new LoginBean.DataBean();
//                mUser.setId(Integer.parseInt(mMkv.decodeString(MMKV_USER_ID, null)));
//                mUser.setPhoneNumber(mMkv.decodeString(MMKV_PHONE_NUMBER, null));
//                mUser.setToken(mMkv.decodeString(MMKV_TOKEN, null));
//            }
//
//        }
        return mUser;
    }

}
