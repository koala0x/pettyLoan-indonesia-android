package com.myLoan.br.presenter

import android.app.Activity
import com.myLoan.br.base.BaseActionPresenter
import com.myLoan.br.bean.MainBean
import com.myLoan.br.bean.VersionBean
import com.myLoan.br.listener.ModelCallBack
import com.myLoan.br.listener.contract.MainStatusContract
import com.myLoan.br.modle.MainStatusModle
import com.myLoan.br.modle.UpdateModle
import com.myLoan.br.tools.UpdateKotlin

class UpdatePresenter : BaseActionPresenter() {
    private var updateModle: UpdateModle? = null

    override fun initModle() {
        updateModle = UpdateModle()
        regitModel(updateModle)
    }

    fun update() {
        updateModle?.update(object : UpdateModle.Companion.UpdateVersionGot {
            override fun updateVersionGot(response: VersionBean) {
                UpdateKotlin.popUpUpdate(response.data!!, attach as Activity)
            }

            override fun failture(code: Int, message: String?) {

            }

            override fun showDialoging() {
            }

            override fun hiddenDialoging() {
            }
        })
    }
}
