package com.myLoan.br.modle

import com.myLoan.br.Constant
import com.myLoan.br.base.BaseModel
import com.myLoan.br.bean.DetectLivenessRequest
import com.myLoan.br.bean.DetectLivenessResponse
import com.myLoan.br.http.CusumeObserver
import com.myLoan.br.http.GonsonUtil
import com.myLoan.br.http.MediaTypeUtil
import com.myLoan.br.http.RetrofitSingleton
import com.myLoan.br.http.api.User
import com.myLoan.br.listener.FailtureInterface
import com.myLoan.br.listener.LoadDialoginterface
import com.myLoan.br.tools.DebugLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers


class DetectLivenessModle : BaseModel() {

    fun upLoadFaceFile(detectLivenessRequest: DetectLivenessRequest, updateVersionGot: UpdateVersionGot) {
        DebugLog.d("upLoadFaceFile", "1111")

        RetrofitSingleton
                .getInstance()
                .retrofit
                .create(User::class.java)
                .detectLiveness(MediaTypeUtil.createJsonMediaType(GonsonUtil.buildGosn().toJson(detectLivenessRequest)))
                .subscribeOn(Schedulers.io())
                .filter(Predicate { response ->
                    if (response.code == Constant.successCode) {
                        return@Predicate true
                    } else {
                        false
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CusumeObserver<DetectLivenessResponse>() {
                    override fun onNext(response: DetectLivenessResponse) {
                        updateVersionGot.uploadSucced()
                        super.onNext(response)
                    }

                    override fun onStart(s: Disposable) {
                        super.onStart(s)
                    }
                })
    }

    interface UpdateVersionGot : FailtureInterface, LoadDialoginterface {
        abstract fun uploadSucced()
        abstract fun uploadFail(s: Int)
    }
}