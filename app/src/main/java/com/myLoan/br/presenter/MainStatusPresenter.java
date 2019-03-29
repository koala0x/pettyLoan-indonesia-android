package com.myLoan.br.presenter;

import android.util.Log;

import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.bean.MainBean;
import com.myLoan.br.listener.ModelCallBack;
import com.myLoan.br.listener.contract.MainStatusContract;
import com.myLoan.br.modle.MainStatusModle;

import java.util.List;

public class MainStatusPresenter extends BaseActionPresenter {
    private MainStatusModle mainStatusModle;

    @Override
    public void initModle() {
        mainStatusModle = new MainStatusModle();
        regitModel(mainStatusModle);
    }

    public void getMainStatus() {

        mainStatusModle.getMainStatus(new ModelCallBack<MainBean>() {
            @Override
            public void callBackBean() {

            }

            @Override
            public void callBackBean(MainBean mainBean) {
                if (isViewAttach()) {
                    ((MainStatusContract) getAttach()).mainStatusFinish(mainBean);
                }
            }

            @Override
            public void callBackList(List<MainBean> list) {

            }

            @Override
            public void failture(int code, String message) {
                showToastAction(code, message);
            }

            @Override
            public void showDialoging() {
                showLoadDialogAction();
            }

            @Override
            public void hiddenDialoging() {
                disLoadDilogAction();
            }
        });
    }
}
