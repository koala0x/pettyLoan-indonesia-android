package com.myLoan.br.tools.view;

import android.text.TextUtils;
import android.widget.Toast;

import com.myLoan.br.MyApplication;


public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String string) {
        if (TextUtils.isEmpty(string)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getMyApplication(), string, Toast.LENGTH_SHORT);
        } else {
           // mToast.cancel();
            mToast.setText(string);
        }
        mToast.show();
    }
}
