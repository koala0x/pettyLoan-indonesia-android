package com.myLoan.br.presenter;

import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.bean.HelpContentBean;
import com.myLoan.br.listener.IHelpcenterView;
import com.myLoan.br.modle.HelpCenterModel;

import java.io.File;
import java.util.List;

/**
 * @author wzx
 * @version 3.0.0 2019/2/12 17:27
 * @update wzx 2019/2/12 17:27
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class HelpCenterPresenter extends BaseActionPresenter {
    private HelpCenterModel helpCenterModel;

    @Override
    public void initModle() {
        helpCenterModel = new HelpCenterModel();
        regitModel(helpCenterModel);
    }

    public void getHelpCenter() {
        helpCenterModel.getHelpCenter(new HelpCenterModel.HelpCenterCallback() {
            @Override
            public void getHelpcenterSuccess(List<HelpContentBean.DataBean> dataBeans) {
                if (isViewAttach()) {
                    ((IHelpcenterView) getAttach()).helpcenterSucess(dataBeans);
                }
            }

            @Override
            public void getHelpcenterFail(String massage) {
                if (isViewAttach()) {
                    ((IHelpcenterView) getAttach()).helpcenterFail(massage);
                }
            }
        });
    }


    public void getHelpCenterChose(int helpId, final String usefulEnum) {
        helpCenterModel.getHelpCenterChose(helpId, usefulEnum, new HelpCenterModel.HelpCenterChoseCallback() {
            @Override
            public void getHelpcenterChoseSuccess() {
                if (isViewAttach()) {
                    ((IHelpcenterView) getAttach()).helpcenterChoseSucess(usefulEnum);
                }
            }

            @Override
            public void getHelpcenterChoseFail(String massage) {
                if (isViewAttach()) {
                    ((IHelpcenterView) getAttach()).helpcenterChoseFail(massage);
                }
            }
        });
    }

    public void feedback(String title, String content, String phoneNumber, List<File> photoFiles) {
        helpCenterModel.feedback(title, content, phoneNumber, photoFiles);
    }
}
