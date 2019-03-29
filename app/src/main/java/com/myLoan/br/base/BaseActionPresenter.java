package com.myLoan.br.base;

/**
 * 基层视图展示动作处理
 */
public abstract class BaseActionPresenter extends BasePresenter {
    /**
     * 展示 dialog
     */
    public void showLoadDialogAction() {
        if (isViewAttach()) {
            ((BaseViewInterface) getAttach()).showProgessDialog();
        }
    }

    /**
     * dis dialog
     */
    public void disLoadDilogAction() {
        if (isViewAttach()) {
            ((BaseViewInterface) getAttach()).hiddenProgessDialog();
        }
    }

    public void showToastAction(int code,String message){
        if(isViewAttach()){
            ((BaseViewInterface)getAttach()).showFailture(code,message);
        }
    }

}
