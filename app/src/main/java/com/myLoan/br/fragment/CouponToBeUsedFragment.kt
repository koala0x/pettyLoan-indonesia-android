package com.myLoan.br.fragment

import com.myLoan.br.R
import com.myLoan.br.activity.MainActivityKotlin
import com.myLoan.br.base.BaseFragment
import com.myLoan.br.base.BasePresenter
import com.myLoan.br.presenter.CouponToBeUsedPresenter

class CouponToBeUsedFragment : BaseFragment<CouponToBeUsedFragment, BasePresenter<CouponToBeUsedFragment>>() {
    override fun getLayoutId(): Int {
       return R.layout.fragment_coupon_to_be_used
    }

    private var couponToBeUsedPresenter: CouponToBeUsedPresenter? = null


    override fun init() {
    }

    override fun initSet() {
    }

    override fun oncreatePresenter(): Array<BasePresenter<CouponToBeUsedFragment>> {
        couponToBeUsedPresenter = CouponToBeUsedPresenter()
        return arrayOf(couponToBeUsedPresenter!! as BasePresenter<CouponToBeUsedFragment>)
    }
}