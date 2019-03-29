package com.myLoan.br.tools.view;

import android.content.Context;

import com.myLoan.br.R;


/**
 * Created by Administrator on 2018/7/13 0013.
 */

public class ForbidDoubleClickTools {
    public static long mLastClickTime = 0;
    public static final int TIME_INTERVAL = 3000;

    public static boolean fastDouble(Context context) {
        if (System.currentTimeMillis() - mLastClickTime >= TIME_INTERVAL) {
            //to do
            mLastClickTime = System.currentTimeMillis();
            return false;
        } else {
            ToastUtil.showToast(context.getString(R.string.double_fast));
            return true;
        }

    }
}
