package com.myLoan.br.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.myLoan.br.Constant
import com.myLoan.br.Constant.WEBURL
import com.myLoan.br.Constant.WebPath.MAIN_INFORMATION
import com.myLoan.br.Constant.WebPath.MAIN_STAGE_REPAY
import com.myLoan.br.MyApplication
import com.myLoan.br.R
import com.myLoan.br.adapter.MainAuditViewPager
import com.myLoan.br.base.BaseActivity
import com.myLoan.br.base.BasePresenter
import com.myLoan.br.bean.MainBean
import com.myLoan.br.bean.MsgEvent
import com.myLoan.br.listener.contract.LoginContract
import com.myLoan.br.listener.contract.MainStatusContract
import com.myLoan.br.listener.contract.UserContract
import com.myLoan.br.presenter.*
import com.myLoan.br.tools.DebugLog
import com.myLoan.br.tools.RxBus
import com.myLoan.br.tools.file.SaveAndClipImage
import com.myLoan.br.tools.loginstate.LoginContext
import com.myLoan.br.tools.loginstate.LoginState
import com.myLoan.br.tools.loginstate.LogoutState
import com.myLoan.br.tools.loginstate.doOtherThingsIfLogIN
import com.myLoan.br.tools.math.DateUtil
import com.myLoan.br.tools.math.DecimalTools
import com.myLoan.br.tools.phonestatus.DeviceUtil
import com.myLoan.br.tools.view.*
import com.myLoan.br.view.VerificationCodeView
import com.myLoan.br.view.viewpager.ZoomOutPageTransformer
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivityKotlin : BaseActivity<MainActivityKotlin, BasePresenter<MainActivityKotlin>>(), LoginContract.ILoginView, UserContract.IUserHeadView, MainStatusContract {
    private var mainPresenter: MainStatusPresenter? = null
    private var loginPrensenter: LoginPrensenter? = null
    private var regitPresenter: RegitPresenter? = null
    private var userPresenter: UserPresenter? = null
    private var locationPresener: LocationPresener? = null
    private var updatePresenter: UpdatePresenter? = null

    private var popupWindow: PopupWindow? = null
    private var mrlSelectPic: RelativeLayout? = null
    private var mrlStartCamera: RelativeLayout? = null
    private var mrlCancel: RelativeLayout? = null
    private var disposable: Disposable? = null
    private var exitTime: Long = 0

    private var rxPermissions: RxPermissions? = null
    internal var intent: Intent? = null
    private var auditOrderId: Int? = null

    private var orderId: Int = 0
    private var orderNo: String? = null
    private var type: Int = 0
    private var urlWithDraw = ""

    private var status: Int = 0
    private var saveAndClipImage: SaveAndClipImage? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initListener() {
        mrlSelectPic!! clickDelay {
            popupWindow!!.dismiss()
            rxPermissions!!
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe {
                        if (it!!) {
                            //选择相册图片
                            var photointent: Intent? = null
                            photointent = Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            photointent.type = "image/*"
                            startActivityForResult(photointent, PHOTO_REQUEST_GALLERY)
                        }
                    }

        }
        mrlStartCamera!! clickDelay {
            popupWindow!!.dismiss()
            rxPermissions!!.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe {
                        if (it!!) {
                            saveAndClipImage = SaveAndClipImage()
                            saveAndClipImage?.openCamera(this@MainActivityKotlin)
                        }
                    }
        }
        sl_main.isEnabled = true
        sl_main.setOnRefreshListener {
            this@MainActivityKotlin.doOtherThingsIfLogIN({ mainPresenter!!.getMainStatus() }, { sl_main.isRefreshing = false })
        }
        dl_main.setDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(view: View, v: Float) {

            }

            override fun onDrawerOpened(view: View) {

            }

            override fun onDrawerClosed(view: View) {
                if (intent != null) {
                    startActivity(intent)
                    intent = null
                }
            }

            override fun onDrawerStateChanged(i: Int) {

            }
        })
        iv_information clickDelay {
            this@MainActivityKotlin.doOtherThingsIfLogIN({
                val intent = Intent(this@MainActivityKotlin, WebViewActivity::class.java)
                intent.putExtra("urlPath", MAIN_INFORMATION)
                startActivity(intent)
            })
        }
        tv_main clickDelay {
            this@MainActivityKotlin.doOtherThingsIfLogIN({
                DebugLog.d("doOtherThingsIfLogIN", "$status|||")
                when (status) {
                    0 -> {
                        val intent = Intent(this@MainActivityKotlin, WebViewActivity::class.java)
                        intent.putExtra("urlPath", Constant.WebPath.MAIN_LOAN_PURPOSE)
                        startActivity(intent)
                    }
                    1 -> {
                    }
                    2, 6 -> {
                        val intent1 = Intent(this@MainActivityKotlin, WebViewActivity::class.java)
                        intent1.putExtra("urlPath", urlWithDraw)
                        startActivity(intent1)
                    }
                    3 -> {
                    }
                    13 -> if (fl_audit_success.visibility == View.GONE) {
                        fl_main_no_audit.visibility = View.GONE
                        fl_auditing.visibility = View.GONE
                        fl_audit_failed.visibility = View.GONE
                        fl_audit_success.visibility = View.VISIBLE
                        fl_lending.visibility = View.GONE
                        fl_loan_failure.visibility = View.GONE
                        fl_loan_success.visibility = View.GONE
                        tv_dalay_rate.visibility = View.GONE
                        tv_main.background = getDrawable(R.drawable.shape_main_button_down_2cc0ff)
                        tv_main.setTextColor(resources.getColor(R.color.white))
                        tv_main.text = resources.getString(R.string.main_withdraw_money)
                    } else {
                        val intent2 = Intent(this@MainActivityKotlin, WebViewActivity::class.java)
                        intent2.putExtra("urlPath", urlWithDraw)
                        startActivity(intent2)
                    }
                    4 -> {
                        ///#/stage-repay/{orderId}/{orderNo}/{type}
                        val intentrepay = Intent(this@MainActivityKotlin, WebViewActivity::class.java)
                        intentrepay.putExtra("urlPath", "$MAIN_STAGE_REPAY$orderId/$orderNo/$type")
                        startActivity(intentrepay)
                    }
                    9 -> {
                    }
                }
            })
        }
        updatePresenter!!.update()
    }


    override fun initView() {
        val decorView = window.decorView
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT
        popupWindow = PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 200f))
        val popView = LayoutInflater.from(this).inflate(R.layout.pop_select_picture, null)
        popupWindow!!.contentView = popView
        popupWindow!!.setBackgroundDrawable(BitmapDrawable())
        popupWindow!!.animationStyle = R.style.mypopwindow_anim_style
        popupWindow!!.isFocusable = true
        mrlSelectPic = popView.findViewById(R.id.rl_select_pic)
        mrlStartCamera = popView.findViewById(R.id.paizhao)
        mrlCancel = popView.findViewById(R.id.rl_cancel)
    }

    @SuppressLint("CheckResult")
    override fun intData() {
        rxPermissions = RxPermissions(this)
        if (LoginContext.getLoginContext().getUser(mContext) == null) {
            LoginContext.getLoginContext().state = LogoutState()
            logoutView()
        } else {
            LoginContext.getLoginContext().state = LoginState()
            loginView()
        }
        disposable = RxBus.getDefault().register(MsgEvent::class.java)./*delay(4,TimeUnit.SECONDS).*/observeOn(AndroidSchedulers.mainThread()).subscribe({ msgEvent ->
            DebugLog.i("==msgEven==" + msgEvent.request)
            if (msgEvent.request == Constant.RxBus.REQUEST_LOGIN) {
                if (LoginContext.getLoginContext().isLogin) {
                    loginView()
                    if (msgEvent.type == Constant.Map.REGITED_VALUE) {
                        showInvaiteDialog()
                    }
                } else {
                    logoutView()
                }
            }
        }, { throwable ->
            DebugLog.e("==throw==$throwable")
            throwable.printStackTrace()
        })
        rxPermissions!!.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe {
                    if (it!!) {
                        DeviceUtil.getUniqueID()
                    }
                }
        rxPermissions!!.request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe {
                    if (it!!) {
                        locationPresener!!.onCreateGPS()
                    }
                }
    }

    override fun onResume() {
        super.onResume()
        if (LoginContext.getLoginContext().isLogin) {
            mainPresenter!!.getMainStatus()
        }
    }

    val dialog = null
    private fun showInvaiteDialog() {
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_invation, null)
        val mbtnFinshiSet = view.findViewById<Button>(R.id.btn_finshi_set)
        val mtvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val mingCancel = view.findViewById<ImageView>(R.id.img_cancel)
        val mvcvVetifyCode = view.findViewById<VerificationCodeView>(R.id.verificationcodeview)
        val vetifyCode = arrayOfNulls<String>(1)
        val dialog = BaseDialog.Build(this).setCanceledOnTouchOutside(false).setSzie(DensityUtil.dip2px(this, 290f), DensityUtil.dip2px(this, 250f)).setContentView(view).createDialog()
        mvcvVetifyCode.onCodeFinishListener = VerificationCodeView.OnCodeFinishListener { content -> vetifyCode[0] = content }
        mbtnFinshiSet clickDelay {
            if (!TextUtils.isEmpty(vetifyCode[0])) {
                regitPresenter!!.saveInvateCode(vetifyCode[0], dialog)
            }
        }
        mtvCancel.clickDelay { dialog.dismiss() }
        mingCancel clickDelay { dialog.dismiss() }
        dialog.show()
    }

    private fun loginView() {
        if (!TextUtils.isEmpty(LoginContext.getLoginContext().getUser(this).headPortrait)) {
            val requestOptions = RequestOptions.circleCropTransform()
            Glide.with(this).load(LoginContext.getLoginContext().getUser(this).headPortrait).apply(requestOptions).into(img_head)
        } else {
            img_head.setImageResource(R.mipmap.head)
        }
        tv_login_state.setTextColor(resources.getColor(R.color.theme_color))
        val user = LoginContext.getLoginContext().getUser(this@MainActivityKotlin)
        fl_about_us.visibility = View.VISIBLE
        fl_about_pay.visibility = View.VISIBLE
        fl_login_out.visibility = View.VISIBLE
        v_line_logout.visibility = View.VISIBLE
        if (user != null) {
            tv_login_state.text = user.phoneNumber
            tv_email.visibility = View.VISIBLE
            tv_email.text = user.email
        }
    }

    private fun logoutView() {
        img_head.setImageResource(R.mipmap.default_icon)
        fl_about_us.visibility = View.GONE
        fl_about_pay.visibility = View.GONE
        tv_email.visibility = View.GONE
        fl_login_out.visibility = View.GONE
        v_line_logout.visibility = View.GONE
        tv_login_state.setTextColor(resources.getColor(R.color.FFBEBEBE))
        tv_login_state.text = resources.getString(R.string.no_login_state)

        fl_main_no_audit.visibility = View.VISIBLE
        fl_auditing.visibility = View.GONE
        fl_audit_failed.visibility = View.GONE
        fl_audit_success.visibility = View.GONE
        fl_lending.visibility = View.GONE
        fl_loan_failure.visibility = View.GONE
        fl_loan_success.visibility = View.GONE
        tv_dalay_rate.visibility = View.GONE
        tv_main.background = getDrawable(R.drawable.shape_main_button_down_2cc0ff)
        tv_main.setTextColor(resources.getColor(R.color.white))
        tv_main.text = resources.getString(R.string.main_immediately_apply)
    }

    fun onClickHead(view: View) {
        this@MainActivityKotlin.doOtherThingsIfLogIN({})
    }

    fun onClickCoupon(view: View) {
        hiddenMenuItem()
        fl_coupon.isSelected = true
        closeSlideMenu()

        this@MainActivityKotlin.doOtherThingsIfLogIN({
            intent = Intent(this, CouponActivity::class.java)
            startActivity(intent)
        }, {
            intent = Intent(this, CouponActivity::class.java)
            startActivity(intent)
        })
    }

    fun onClickHistoryOrder(view: View) {
        hiddenMenuItem()
        closeSlideMenu()
        fl_history_order.isSelected = true
        checkLogin(Constant.WebPath.ORDER_LIST)
    }

    fun onClickContractUs(view: View) {
        hiddenMenuItem()
        closeSlideMenu()
        fl_contract_us.isSelected = true
        this@MainActivityKotlin.doOtherThingsIfLogIN({
            val intent = Intent(this@MainActivityKotlin, HelpCenterActivity::class.java)
            startActivity(intent)
        })
    }

    fun onClickInvateFriend(view: View) {
        hiddenMenuItem()
        closeSlideMenu()
        fl_invate_frend.isSelected = true
        ToastUtil.showToast(getString(R.string.no_action_open))
    }

    fun onClickAboutUs(view: View) {
        hiddenMenuItem()
        closeSlideMenu()
        fl_about_us.isSelected = true
        checkLogin(Constant.WebPath.ABOUT_US)

    }

    fun onClickAboutPay(view: View) {
        hiddenMenuItem()
        closeSlideMenu()
        fl_about_pay.isSelected = true
        checkLogin(Constant.WebPath.ABOUT_REPAY)
    }

    fun onClickSet(view: View) {
        hiddenMenuItem()
        closeSlideMenu()
        fl_set.isSelected = true
        checkLogin(Constant.WebPath.SETTING)
    }

    fun onClickLoginOut(view: View) {
        hiddenMenuItem()
        closeSlideMenu()
        fl_login_out.isSelected = true
        loginPrensenter!!.loginOut()
        LoginContext.getLoginContext().state = LogoutState()
        LoginContext.getLoginContext().setUser(null)
        val msgEvent = MsgEvent<Any>(Constant.RxBus.REQUEST_LOGIN, 0, "")
        RxBus.getDefault().post(msgEvent)

    }

    fun onClickImgHead(view: View) {
        hiddenMenuItem()
        this@MainActivityKotlin.doOtherThingsIfLogIN({
            if (popupWindow != null && !popupWindow!!.isShowing) {
                popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0)
            }
        })
    }

    fun onOpenDrawerClick(view: View) {
        if (dl_main.isDrawerOpen(GravityCompat.START)) {
            dl_main.closeDrawer(GravityCompat.START)
        } else {
            dl_main.openDrawer(GravityCompat.START)
        }
    }


    fun fl_center_helpClick(view: View) {
        this@MainActivityKotlin.doOtherThingsIfLogIN({
            val intent = Intent(this@MainActivityKotlin, HelpCenterActivity::class.java)
            startActivity(intent)
        })
    }

    fun fl_loan_guideClick(view: View) {
        this@MainActivityKotlin.doOtherThingsIfLogIN({})
    }

    private fun checkLogin(path: String, bundle: Bundle? = null) {
        this@MainActivityKotlin.doOtherThingsIfLogIN({
            intent = Intent(this, WebViewActivity::class.java)
            if (bundle != null) {
                intent!!.putExtras(bundle)
            }
            intent!!.putExtra("urlPath", path)
        })
    }

    fun closeSlideMenu() {
        dl_main.closeDrawer(GravityCompat.START)
    }

    fun hiddenMenuItem() {
        fl_history_order.isSelected = false
        fl_coupon.isSelected = false
        fl_contract_us.isSelected = false
        fl_invate_frend.isSelected = false
        fl_about_us.isSelected = false
        fl_about_pay.isSelected = false
        fl_set.isSelected = false
    }

    override fun oncreatePresenter(): Array<out BasePresenter<MainActivityKotlin>>? {
        mainPresenter = MainStatusPresenter()
        loginPrensenter = LoginPrensenter()
        regitPresenter = RegitPresenter()
        userPresenter = UserPresenter()
        locationPresener = LocationPresener()
        updatePresenter = UpdatePresenter()
        return arrayOf(mainPresenter!! as BasePresenter<MainActivityKotlin>
                , loginPrensenter!! as BasePresenter<MainActivityKotlin>
                , regitPresenter!! as BasePresenter<MainActivityKotlin>
                , userPresenter!! as BasePresenter<MainActivityKotlin>
                , locationPresener!! as BasePresenter<MainActivityKotlin>
                , updatePresenter!! as BasePresenter<MainActivityKotlin>)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        DebugLog.i("mainart", "===相机==$requestCode||$resultCode")
        when (requestCode) {
            PHOTO_REQUEST_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    saveAndClipImage?.startPhotoZoom(context = this@MainActivityKotlin)
                }
            }
            PHOTO_REQUEST_GALLERY -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        ToastUtil.showToast(resources.getString(R.string.select_file_error))
                        return
                    }
                    val selectedImage = data.data
                    if (selectedImage == null) {
                        ToastUtil.showToast(resources.getString(R.string.select_file_error))
                        return
                    } else {
                        saveAndClipImage?.startPhotoZoom(uri = data.data, context = this@MainActivityKotlin)
                    }
                }
            }
            PHOTO_REQUEST_CLIP -> {
                if (data != null) {
                    saveAndClipImage?.getImageToView(data, this@MainActivityKotlin, userPresenter!!)
                } else {
                    Toast.makeText(this, "裁剪返回出错", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                // Object mHelperUtils;
                Toast.makeText(this, getString(R.string.quit_back_note), Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {// 不弹窗口.直接退出

                // AppCounter.Mark(6);//记录退出登录的在线时长操作
                // 结束activity队列中的所有activity
                MyApplication.clearAllTaskActivty()
                // 清除通知栏
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancelAll()
                System.exit(0)//结束整个进程
            }
            return true
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) {
            if (!disposable!!.isDisposed) {
                disposable!!.dispose()
            }

        }
    }

    override fun loginSucess() {

    }

    override fun logoutSucess() {
        LoginContext.getLoginContext().state.jump(this)
    }

    override fun uploadHeadSucess(url: String) {
        val requestOptions = RequestOptions.circleCropTransform()
        Glide.with(this).load(url).apply(requestOptions).into(img_head)
    }

    override fun mainStatusFinish(mainBean: MainBean) {
        sl_main.isRefreshing = false
        if (mainBean.data != null) {
            val dataBean = mainBean.data
            DebugLog.d("dataBeandataBean", dataBean.status!!.toString() + "")
            if (5 == dataBean.creditStatus && (dataBean.status == 6 || dataBean.status == 8 || dataBean.status == 11 || dataBean.status == 13)) {
                fl_main_no_audit.visibility = View.VISIBLE
                fl_auditing.visibility = View.GONE
                fl_audit_failed.visibility = View.GONE
                fl_audit_success.visibility = View.GONE
                fl_lending.visibility = View.GONE
                fl_loan_failure.visibility = View.GONE
                fl_loan_success.visibility = View.GONE
                tv_dalay_rate.visibility = View.GONE
                tv_main.background = getDrawable(R.drawable.shape_main_button_down_2cc0ff)
                tv_main.setTextColor(resources.getColor(R.color.white))
                tv_main.text = resources.getString(R.string.main_immediately_apply)
            } else {
                status = dataBean.status!!
                when (dataBean.status) {
                    0 -> {
                        fl_main_no_audit.visibility = View.VISIBLE
                        fl_auditing.visibility = View.GONE
                        fl_audit_failed.visibility = View.GONE
                        fl_audit_success.visibility = View.GONE
                        fl_lending.visibility = View.GONE
                        fl_loan_failure.visibility = View.GONE
                        fl_loan_success.visibility = View.GONE
                        tv_dalay_rate.visibility = View.GONE
                        tv_main.background = getDrawable(R.drawable.shape_main_button_down_2cc0ff)
                        tv_main.setTextColor(resources.getColor(R.color.white))
                        tv_main.text = resources.getString(R.string.main_immediately_apply)
                    }
                    1 -> {
                        fl_main_no_audit.visibility = View.GONE
                        fl_auditing.visibility = View.VISIBLE
                        fl_audit_failed.visibility = View.GONE
                        fl_audit_success.visibility = View.GONE
                        fl_lending.visibility = View.GONE
                        fl_loan_failure.visibility = View.GONE
                        fl_loan_success.visibility = View.GONE
                        tv_dalay_rate.visibility = View.GONE
                        tv_main.background = getDrawable(R.drawable.shape_main_button_dfe6ec)
                        tv_main.setTextColor(resources.getColor(R.color.popup_outline))
                        tv_main.text = resources.getString(R.string.main_waiting_evaluating)
                    }
                    2, 6 -> {
                        fl_main_no_audit.visibility = View.GONE
                        fl_auditing.visibility = View.GONE
                        fl_audit_failed.visibility = View.GONE
                        fl_audit_success.visibility = View.VISIBLE
                        fl_lending.visibility = View.GONE
                        fl_loan_failure.visibility = View.GONE
                        fl_loan_success.visibility = View.GONE
                        tv_dalay_rate.visibility = View.GONE
                        tv_main.background = getDrawable(R.drawable.shape_main_button_down_2cc0ff)
                        tv_main.setTextColor(resources.getColor(R.color.white))
                        tv_main.text = resources.getString(R.string.main_withdraw_money)

                        auditOrderId = dataBean.auditOrderId
                        val homePageProductDTOs = dataBean.productDTOS as ArrayList<MainBean.productDTOS>
                        vp_audit_success.adapter = MainAuditViewPager(homePageProductDTOs, this, dataBean.auditOrderId!!, dataBean.orderId!!)
                        vp_audit_success.offscreenPageLimit = 2
                        vp_audit_success.setPageTransformer(true, ZoomOutPageTransformer())
                        urlWithDraw = WEBURL + "/#/account-confirm/" +
                                homePageProductDTOs[0].productType + "/" +
                                dataBean.auditOrderId + "/" +
                                homePageProductDTOs[0].productId + "/" +
                                dataBean.orderId
                        vp_audit_success.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

                            }

                            override fun onPageSelected(i: Int) {
                                val productType = homePageProductDTOs[i].productType!!
                                val auditOrderId = dataBean.auditOrderId!!
                                val productId = homePageProductDTOs[i].productId!!
                                val orderId = dataBean.orderId!!
                                urlWithDraw = "$WEBURL/#/account-confirm/$productType/$auditOrderId/$productId/$orderId"
                            }

                            override fun onPageScrollStateChanged(i: Int) {

                            }
                        })
                    }
                    3 -> {
                        fl_main_no_audit.visibility = View.GONE
                        fl_auditing.visibility = View.GONE
                        fl_audit_failed.visibility = View.GONE
                        fl_audit_success.visibility = View.GONE
                        fl_lending.visibility = View.VISIBLE
                        fl_loan_failure.visibility = View.GONE
                        fl_loan_success.visibility = View.GONE
                        tv_dalay_rate.visibility = View.GONE

                        tv_main.background = getDrawable(R.drawable.shape_main_button_down_2cc0ff)
                        tv_main.setTextColor(resources.getColor(R.color.white))
                        tv_main.text = resources.getString(R.string.main_bank_hanling)

                        tv_loan_account.text = DecimalTools.keepTwoDecimal(dataBean.auditMoney!!)

                        tv_loan_day.text = dataBean.loanDays!!.toString()
                    }
                    13 -> {
                        fl_main_no_audit.visibility = View.GONE
                        fl_auditing.visibility = View.GONE
                        fl_audit_failed.visibility = View.GONE
                        fl_audit_success.visibility = View.GONE
                        fl_lending.visibility = View.GONE
                        fl_loan_failure.visibility = View.VISIBLE
                        fl_loan_success.visibility = View.GONE
                        tv_dalay_rate.visibility = View.GONE

                        tv_main.background = getDrawable(R.drawable.shape_main_button_down_2cc0ff)
                        tv_main.setTextColor(resources.getColor(R.color.white))
                        tv_main.text = resources.getString(R.string.main_loan_failure_bottom)


                        auditOrderId = dataBean.auditOrderId
                        val homePageProductDTOs3 = dataBean.productDTOS as ArrayList<MainBean.productDTOS>
                        vp_audit_success.adapter = MainAuditViewPager(homePageProductDTOs3, this, dataBean.auditOrderId!!, dataBean.orderId!!)
                        vp_audit_success.offscreenPageLimit = 2
                        vp_audit_success.setPageTransformer(true, ZoomOutPageTransformer())
                        urlWithDraw = WEBURL + "/#/account-confirm/" +
                                homePageProductDTOs3[0].productType + "/" +
                                dataBean.auditOrderId + "/" +
                                homePageProductDTOs3[0].productId + "/" +
                                dataBean.orderId
                        vp_audit_success.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

                            }

                            override fun onPageSelected(i: Int) {
                                val productType = homePageProductDTOs3[i].productType!!
                                val auditOrderId = dataBean.auditOrderId!!
                                val productId = homePageProductDTOs3[i].productId!!
                                val orderId = dataBean.orderId!!
                                urlWithDraw = "$WEBURL/#/account-confirm/$productType/$auditOrderId/$productId/$orderId"
                            }

                            override fun onPageScrollStateChanged(i: Int) {

                            }
                        })
                    }
                    4 -> {
                        fl_main_no_audit.visibility = View.GONE
                        fl_auditing.visibility = View.GONE
                        fl_audit_failed.visibility = View.GONE
                        fl_audit_success.visibility = View.GONE
                        fl_lending.visibility = View.GONE
                        fl_loan_failure.visibility = View.GONE
                        fl_loan_success.visibility = View.VISIBLE
                        tv_dalay_rate.visibility = View.VISIBLE
                        val rate = dataBean.dueMonthDelayRate * 100
                        tv_dalay_rate.text = resources.getString(R.string.main_dalay_rate)

                        //type 产品类型  1 一次性还本付息    2 分期
                        val homePageProductDTOs2 = dataBean.productDTOS as ArrayList<MainBean.productDTOS>

                        if (dataBean.hasDue!!) {
                            tv_day_left.text = resources.getString(R.string.main_day_overdue, dataBean.overDueDays!!.toString() + "")
                            tv_day_left.setTextColor(resources.getColor(R.color.main_overdue))
                            tv_payment_amount.text = DecimalTools.keepTwoDecimal(dataBean.repayAmount!!)
                            tv_payment_amount.setTextColor(resources.getColor(R.color.main_overdue))

                            tv_dead_line.text = resources.getString(R.string.main_success_data)
                            tv_dead_line_amount.text = DateUtil.millisecond2dmy(dataBean.repayDate)

                            tv_main.background = getDrawable(R.drawable.shape_main_button_ff8767)
                            tv_main.setTextColor(resources.getColor(R.color.white))
                            tv_main.text = resources.getString(R.string.main_payment_now)
                        } else {
                            tv_day_left.text = resources.getString(R.string.main_day_left, dataBean.loanEndDays!!.toString() + "")
                            tv_day_left.setTextColor(resources.getColor(R.color.main_success_tittle))
                            tv_payment_amount.text = DecimalTools.keepTwoDecimal(dataBean.repayAmount!!)
                            tv_payment_amount.setTextColor(resources.getColor(R.color.main_success_tittle))

                            tv_dead_line.text = resources.getString(R.string.main_success_data)
                            tv_dead_line_amount.text = DateUtil.millisecond2dmy(dataBean.repayDate)

                            tv_main.background = getDrawable(R.drawable.shape_main_button_dcd261)
                            tv_main.setTextColor(resources.getColor(R.color.white))
                            tv_main.text = resources.getString(R.string.main_payment_now)
                        }
                        orderId = dataBean.orderId!!
                        orderNo = dataBean.orderNo
                        type = dataBean.orderType!!
                    }
                    9 -> {//机审拒绝
                        fl_main_no_audit.visibility = View.GONE
                        fl_auditing.visibility = View.GONE
                        fl_audit_failed.visibility = View.VISIBLE
                        fl_audit_success.visibility = View.GONE
                        fl_lending.visibility = View.GONE
                        fl_loan_failure.visibility = View.GONE
                        fl_loan_success.visibility = View.GONE
                        tv_dalay_rate.visibility = View.GONE

                        val forbidDayStr = resources.getString(R.string.main_audit_failed, dataBean.expireRejectDays!!.toString() + "")
                        val spannableString = TextColorNumberTools.setTextColorfulNumber(forbidDayStr, dataBean.expireRejectDays!!.toString() + "", 15, R.color.text_gray_aaaaaa)
                        tv_forbid_day.text = spannableString

                        tv_main.background = getDrawable(R.drawable.shape_main_button_dfe6ec)
                        tv_main.setTextColor(resources.getColor(R.color.popup_outline))
                        tv_main.text = resources.getString(R.string.main_reapply)
                    }
                }
            }
        }
    }

    companion object {
        val PHOTO_REQUEST_CAMERA = 1// 拍照
        private val PHOTO_REQUEST_GALLERY = 2// 从相册中选择
        private val PHOTO_REQUEST_CLIP = 4// 从相册中选择
    }
}
