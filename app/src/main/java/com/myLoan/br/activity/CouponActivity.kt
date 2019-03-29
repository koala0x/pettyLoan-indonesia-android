package com.myLoan.br.activity

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.myLoan.br.R
import com.myLoan.br.base.BaseActivity
import com.myLoan.br.base.BasePresenter
import com.myLoan.br.fragment.CouponToBeUsedFragment
import kotlinx.android.synthetic.main.activity_coupon.*
import kotlinx.android.synthetic.main.include_title.*
import java.util.ArrayList

class CouponActivity : BaseActivity<CouponActivity, BasePresenter<CouponActivity>>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_coupon
    }

    override fun initListener() {
    }

    override fun initView() {
        tv_title.setText(R.string.coupon_title)
        tv_right.visibility = View.VISIBLE
        tv_right.setText(R.string.coupon_title)
        setupViewPager(vp_coupon)
        (this as AppCompatActivity).setSupportActionBar(tb_coupon)
        tl_coupon.setupWithViewPager(vp_coupon)
    }

    override fun intData() {

    }

    override fun oncreatePresenter(): Array<out BasePresenter<CouponActivity>> {
        return arrayOf()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = AdapterA(supportFragmentManager)
        adapter.addFragment(CouponToBeUsedFragment(), "MySong")
        //        adapter.addFragment(new ArtistFragment(), this.getString(R.string.artists));
        viewPager.adapter = adapter
    }

    internal class AdapterA(fm: FragmentManager) : FragmentPagerAdapter(fm) {


        private val mFragments = ArrayList<Fragment>()
        private val mFragmentTitles = ArrayList<String>()
        override fun getCount(): Int {
            return mFragments.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragments.add(fragment)
            mFragmentTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitles[position]
        }
    }
}