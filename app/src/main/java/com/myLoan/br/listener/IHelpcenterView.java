package com.myLoan.br.listener;

import com.myLoan.br.bean.HelpContentBean;

import java.util.List;

/**
 * @author wzx
 * @version 3.0.0 2019/2/12 17:40
 * @update wzx 2019/2/12 17:40
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public interface IHelpcenterView {
    void helpcenterSucess(List<HelpContentBean.DataBean> dataBeans);

    void helpcenterFail(String massage);

    void helpcenterChoseSucess(String usefulEnum);

    void helpcenterChoseFail(String massage);
}
