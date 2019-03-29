package com.myLoan.br.tools.view

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan

import com.myLoan.br.MyApplication

import com.myLoan.br.tools.view.ScreenTools.sp2px


/**
 * Created by 123 on 2017/10/23.
 */

object TextColorNumberTools {

    fun setTextColorfulNumber(all: String, number: String, size: Int, color: Int): SpannableString {
        val spannableString = SpannableString(all)
        spannableString.setSpan(ForegroundColorSpan(MyApplication.mContext.resources.getColor(color)),
                all.indexOf(number),
                all.indexOf(number) + number.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(AbsoluteSizeSpan(sp2px(size)), all.indexOf(number),
                all.indexOf(number) + number.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }
}
