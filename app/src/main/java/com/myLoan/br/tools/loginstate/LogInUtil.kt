package com.myLoan.br.tools.loginstate

import android.app.Activity

fun Activity.doOtherThingsIfLogIN(ifLoginMethod: () -> Unit, ifNoLoginMethod: () -> Unit = {}) {
    if (LoginContext.getLoginContext().isLogin) {
        ifLoginMethod()
    } else {
        LoginContext.getLoginContext().state.jump(this)
        ifNoLoginMethod()
    }
}