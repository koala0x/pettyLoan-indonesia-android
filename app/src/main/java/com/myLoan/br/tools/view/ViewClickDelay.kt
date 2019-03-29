package com.myLoan.br.tools.view

import android.view.View
import com.myLoan.br.tools.view.ViewClickDelay.SPACE_TIME
import com.myLoan.br.tools.view.ViewClickDelay.hash
import com.myLoan.br.tools.view.ViewClickDelay.lastClickTime


object ViewClickDelay {
    var hash: Int = 0
    var lastClickTime: Long = 0
    var SPACE_TIME: Long = 3000
}
infix fun View.clickDelay(clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > SPACE_TIME) {
                lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}
class ClickDelay {
    companion object {
         fun View.clickDelay(clickAction: () -> Unit) {
            this.setOnClickListener {
                if (this.hashCode() != hash) {
                    hash = this.hashCode()
                    lastClickTime = System.currentTimeMillis()
                    clickAction()
                } else {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickTime > SPACE_TIME) {
                        lastClickTime = System.currentTimeMillis()
                        clickAction()
                    }
                }
            }
        }
    }
}