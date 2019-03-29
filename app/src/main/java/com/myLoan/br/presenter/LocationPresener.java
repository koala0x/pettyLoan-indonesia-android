package com.myLoan.br.presenter;

import com.myLoan.br.MyApplication;
import com.myLoan.br.base.BaseActionPresenter;
import com.myLoan.br.tools.phonestatus.LocationUtil;

/**
 * 定位控制器
 */
public class LocationPresener extends BaseActionPresenter {
    @Override
    public void initModle() {

    }

    /**
     * 准备开启GPS
     */
    public void onCreateGPS(){
        LocationUtil.getLngAndLat(MyApplication.mContext,null);
    }
}
