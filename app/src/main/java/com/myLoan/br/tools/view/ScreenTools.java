package com.myLoan.br.tools.view;

import android.content.res.Resources;
import android.util.TypedValue;

import com.myLoan.br.MyApplication;

public class ScreenTools {

    public static int getStatusHeight() {
        int resourceId = MyApplication.getMyApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
        return MyApplication.getMyApplication().getResources().getDimensionPixelSize(resourceId);

    }

    public static int dp2px(float dp) {
        return (int) (dp * MyApplication.getMyApplication().getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }
}
