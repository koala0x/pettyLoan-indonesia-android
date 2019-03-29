package com.myLoan.br.tools.loginstate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Introduction:用户状态接口。主要用于区分操作的时候是否是登录，如果未登录，则跳转到登录页面
 *
 */
public interface UserState {
    void jump(Activity context);

    Intent getIntent(Context context);
}
