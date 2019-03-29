package com.myLoan.br.presenter

import android.os.Bundle
import com.myLoan.br.base.BaseActionPresenter
import com.myLoan.br.bean.DetectLivenessRequest
import com.myLoan.br.listener.contract.FaceIdContract
import com.myLoan.br.modle.DetectLivenessModle

class DetectLivenessPresenter : BaseActionPresenter() {
    private var detectLivenessModle: DetectLivenessModle? = null

    override fun initModle() {
        detectLivenessModle = DetectLivenessModle()
        regitModel(detectLivenessModle)
    }

    fun update(detectLivenessRequest: DetectLivenessRequest) {
        detectLivenessModle?.upLoadFaceFile(detectLivenessRequest, object : DetectLivenessModle.UpdateVersionGot {
            override fun uploadSucced() {
                (attach as FaceIdContract).faceidSendSuccess()
            }

            override fun uploadFail(s: Int) {
                (attach as FaceIdContract).faceidFail(s)
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
