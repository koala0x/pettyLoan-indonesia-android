package com.myLoan.br.tools.loginstate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.myLoan.br.activity.LoadLoginActivity;


/**
 * Introduction:注销状态
 */
public class LogoutState implements UserState {

    @Override
    public void jump(Activity context) {
        Intent intent = getIntent(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public Intent getIntent(Context context) {
        return new Intent(context, LoadLoginActivity.class);
    }

}
