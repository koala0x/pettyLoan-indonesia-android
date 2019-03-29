package com.myLoan.br.modle

import com.myLoan.br.Constant
import com.myLoan.br.http.api.User
import com.myLoan.br.base.BaseModel
import com.myLoan.br.bean.VersionBean
import com.myLoan.br.http.CusumeObserver
import com.myLoan.br.http.RetrofitSingleton
import com.myLoan.br.listener.FailtureInterface
import com.myLoan.br.listener.LoadDialoginterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers


class UpdateModle : BaseModel() {

    fun update(updateVersionGot: UpdateVersionGot) {
        RetrofitSingleton
                .getInstance()
                .retrofit
                .create(User::class.java)
                .updateVersion
                .subscribeOn(Schedulers.io())
                .filter(Predicate { response ->
                    if (response.code == Constant.successCode) {
                        return@Predicate true
                    } else {
                        updateVersionGot.failture(response.code, response.message)
                        false
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CusumeObserver<VersionBean>() {
                    override fun onNext(response: VersionBean) {
                        super.onNext(response)
                        updateVersionGot.updateVersionGot(response)
                    }

                    override fun onStart(s: Disposable) {
                        super.onStart(s)
                    }
                })
    }

    companion object {
        interface UpdateVersionGot : FailtureInterface, LoadDialoginterface {
            fun updateVersionGot(response: VersionBean)
        }
    }
}