package com.myLoan.br.tools.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object InputTools{
    public fun showSoftKeyboard(view: View, mContext: Context) {
        if (view.requestFocus()) {
            var inputMethodManger: InputMethodManager = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManger.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}