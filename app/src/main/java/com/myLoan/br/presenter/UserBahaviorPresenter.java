package com.myLoan.br.presenter;

import com.myLoan.br.Constant;
import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.bean.LoginClick;
import com.myLoan.br.bean.ModifyClick;
import com.myLoan.br.bean.RegitClick;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.listener.contract.UserBehaviorContact;
import com.myLoan.br.modle.UserBehaviorModle;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * 用户行为控制器
 */
public class UserBahaviorPresenter extends BaseActionPresenter implements UserBehaviorContact.IUserBehavior {
    private UserBehaviorModle userBehaviorModle;

    @Override
    public void initModle() {
        userBehaviorModle = new UserBehaviorModle();
        regitModel(userBehaviorModle);
    }

    @Override
    public void regitClick(StringBuilder sbcheck, StringBuilder sbemail, StringBuilder sbphoneNumber, StringBuilder sbregister, StringBuilder sbtopBack, StringBuilder sbBottomClick) {
        RegitClick regitClick = new RegitClick();
        regitClick.setBottomBack(deleteLastIndex(sbBottomClick));
        regitClick.setCheck(deleteLastIndex(sbcheck));
        regitClick.setEmail(deleteLastIndex(sbemail));
        regitClick.setPhoneNumber(deleteLastIndex(sbphoneNumber));
        regitClick.setRegister(deleteLastIndex(sbregister));
        regitClick.setTopBack(deleteLastIndex(sbtopBack));
        userBehaviorModle.clickTime(Constant.UserBehavior.regitClickPath, regitClick, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {

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
    public void loginClick(StringBuilder phoneNum, StringBuilder passwd, StringBuilder login, StringBuilder backBottom) {
        LoginClick loginClick = new LoginClick();
        loginClick.setBottomBack(deleteLastIndex(backBottom));
        loginClick.setPhoneNumber(deleteLastIndex(phoneNum));
        loginClick.setPassword(deleteLastIndex(passwd));
        loginClick.setLogin(deleteLastIndex(login));
        userBehaviorModle.clickTime(Constant.UserBehavior.loginClickPath, loginClick, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {

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
    public void modifyPasswdClick(StringBuilder BottomBack, StringBuilder topBack, StringBuilder firstPassword, StringBuilder secondPassword, StringBuilder nextStep, int type) {
        ModifyClick modifyClick = new ModifyClick();
        modifyClick.setBottomBack(deleteLastIndex(BottomBack));
        modifyClick.setTopBack(deleteLastIndex(topBack));
        modifyClick.setFirstPassword(deleteLastIndex(firstPassword));
        modifyClick.setSecondPassword(deleteLastIndex(secondPassword));
        modifyClick.setNextStep(deleteLastIndex(nextStep));
        modifyClick.setPageType(type);
        userBehaviorModle.clickTime(Constant.UserBehavior.modifyClickPath, modifyClick, new ModelCallBack() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(Object o) {

            }

            @Override
            public void callBackList(List list) {

            }

            @Override
            public void failture(int code, String message) {
                showToastAction(code, message);
            }

            @Override
            public void showDialoging() {

            }

            @Override
            public void hiddenDialoging() {

            }
        });
    }


    public String deleteLastIndex(StringBuilder sb) {
        if (sb.length() > 1) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }
}
