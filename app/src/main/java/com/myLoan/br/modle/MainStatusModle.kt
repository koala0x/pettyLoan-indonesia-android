package com.myLoan.br.modle

import com.myLoan.br.Constant
import com.myLoan.br.http.api.User
import com.myLoan.br.base.BaseModel
import com.myLoan.br.bean.MainBean
import com.myLoan.br.http.CusumeObserver
import com.myLoan.br.http.RetrofitSingleton
import com.myLoan.br.listener.ModelCallBack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers


class MainStatusModle : BaseModel() {

    fun getMainStatus(modelCallBack: ModelCallBack<MainBean>) {
        RetrofitSingleton
                .getInstance()
                .retrofit
                .create(User::class.java)
                .homePage
                .subscribeOn(Schedulers.io())
                .filter(Predicate { response ->
                    if (response.code == Constant.successCode) {
                        return@Predicate true
                    } else {
                        modelCallBack.failture(response.code, response.message)
                        false
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CusumeObserver<MainBean>(modelCallBack) {
                    override fun onNext(response: MainBean) {
                        super.onNext(response)
                        modelCallBack.callBackBean(response)
                    }

                    override fun onStart(s: Disposable) {
                        super.onStart(s)
                        addDisposable(s)
                    }
                })
    }
}