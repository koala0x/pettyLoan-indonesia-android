package com.myLoan.br.adapter

import android.content.Context
import android.content.Intent
import androidx.viewpager.widget.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.myLoan.br.Constant.WEBURL
import com.myLoan.br.R
import com.myLoan.br.activity.WebViewActivity
import com.myLoan.br.bean.MainBean
import com.myLoan.br.tools.DebugLog
import java.util.ArrayList

class MainAuditViewPager(private val homePageProductDTOs: ArrayList<MainBean.productDTOS>, private val context: Context, private var auditOrderId: Int, private var orderId: Int) : androidx.viewpager.widget.PagerAdapter() {
    ///#/account-confirm/{type}/{auditOrderId}/{productId}
    //type 产品类型      2 分期
    //auditOrderId 信审订单id
    //productId   产品id
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_main_loan_type, null)
        val tv_loan_tittle = view.findViewById<TextView>(R.id.tv_loan_tittle)
        val tv_one_left = view.findViewById<TextView>(R.id.tv_one_left)
        val tv_one_right = view.findViewById<TextView>(R.id.tv_one_right)
        val tv_two_left = view.findViewById<TextView>(R.id.tv_two_left)
        val tv_two_right = view.findViewById<TextView>(R.id.tv_two_right)
        val productdtos = homePageProductDTOs[position]
        val productId = productdtos.productId
        val productType = productdtos.productType
        tv_loan_tittle.text = context.resources.getText(R.string.main_short_term_loan)
        tv_one_left.text = context.resources.getText(R.string.main_withdraw_amount)
        tv_one_right.text = productdtos.approveAmountMin.toString() + " ～ "+productdtos.approveAmountMax.toString()
        tv_two_left.text = context.resources.getText(R.string.main_withdraw_period_short)
        tv_two_right.text = productdtos.loanTermMix.toString() + " ～ "+productdtos.loanTermMax.toString()
//        when (productdtos.productType) {
//            1 -> {//1 一次性还本付息
//
//            }
//            2 -> {//2 分期
//                tv_loan_tittle.text = context.resources.getText(R.string.main_installment)
//                tv_one_left.text = context.resources.getText(R.string.main_withdraw_amount)
//                tv_one_right.text = productdtos.approveAmountMin.toString() + " ～ "+productdtos.approveAmountMax.toString()
//                tv_two_left.text = context.resources.getText(R.string.main_withdraw_period_long)
//                tv_two_right.text = productdtos.loanTermMix.toString() + " ～ "+productdtos.loanTermMax.toString()
//            }
//        }
        view.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            val url = "$WEBURL/#/account-confirm/$productType/$auditOrderId/$productId/$orderId"
            DebugLog.d("urlurl", url + "")
            intent.putExtra("urlPath", url)
            context.startActivity(intent)
        }
        container.addView(view)
        return view
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return homePageProductDTOs.size
    }

    override fun getPageWidth(position: Int): Float {
        return if (homePageProductDTOs.size == 1) {
            1f
        } else {
            0.9f
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
        container.removeView(`object` as View)
    }
}