package com.myLoan.br.tools.loginstate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.myLoan.br.activity.MainActivityKotlin;


/**
 * Introduction:登录状态
 */
public class LoginState implements UserState {

    @Override
    public void jump(Activity context) {
     //   MyApplication.clearAllTaskActivty();
        Intent intent=getIntent(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(getIntent(context));
    }

    @Override
    public Intent getIntent(Context context) {
        return new Intent(context, MainActivityKotlin.class);
    }

}
