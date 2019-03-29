package com.myLoan.br.tools

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadSampleListener
import com.liulishuo.filedownloader.FileDownloader
import com.myLoan.br.BuildConfig
import com.myLoan.br.MyApplication
import com.myLoan.br.R
import com.myLoan.br.bean.AutoUpdateInfo
import com.myLoan.br.bean.VersionBean
import com.myLoan.br.view.ProgressBeautifulView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File

object UpdateKotlin {

    //data: VersionBean.DataBean
    @JvmStatic
    fun popUpUpdate(data: VersionBean.DataBean, context: Activity) {
        val popup: View = LayoutInflater.from(context).inflate(R.layout.layout_popup_update, null)

        val tv_popup_tittle: TextView = popup.findViewById(R.id.tv_popup_tittle)
        val tv_popup_content: TextView = popup.findViewById(R.id.tv_popup_content)
        val tv_left: TextView = popup.findViewById(R.id.tv_left)
        val tv_right: TextView = popup.findViewById(R.id.tv_right)
        val progressBeautifulView: ProgressBeautifulView = popup.findViewById(R.id.pv_one)
        val iv_close: ImageView = popup.findViewById(R.id.iv_close)

        tv_popup_content.text = data.updateLog
        (context.window.decorView as FrameLayout).addView(popup)
        tv_popup_tittle.text = context.resources.getText(R.string.new_version).toString() + data.newVersionName
        if (data.forceUpdate!!) {
            tv_left.setOnClickListener {
                context.finish()
            }
            tv_right.setOnClickListener {
                tv_left.visibility = View.GONE
                tv_right.visibility = View.GONE
                progressBeautifulView.visibility = View.VISIBLE
                checkPermission(data, progressBeautifulView, context)
            }
            popup.setOnClickListener {

            }
            iv_close.visibility = View.GONE
        } else {
            tv_left.setOnClickListener {
                (context.window.decorView as FrameLayout).removeView(popup)
            }
            tv_right.setOnClickListener {
                checkPermission(data, progressBeautifulView, context)
                (context.window.decorView as FrameLayout).removeView(popup)
            }
            popup.setOnClickListener {
                (context.window.decorView as FrameLayout).removeView(popup)
            }
            iv_close.visibility = View.VISIBLE
            iv_close.setOnClickListener {
                (context.window.decorView as FrameLayout).removeView(popup)
            }
        }
    }

    fun checkPermission(data: VersionBean.DataBean, progressBeautifulView: ProgressBeautifulView, context: Activity) {
        val rxPermissions = RxPermissions(context)
        val perD = rxPermissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { permission ->
                    if (permission.granted) {
                        startDownloadTask(data.apkUrl!!, data.newVersion!!, progressBeautifulView, context)
                    } else {
                        if (permission.shouldShowRequestPermissionRationale) {
                            if (data.forceUpdate!!) {
                                checkPermission(data, progressBeautifulView, context)
                            }
                        } else {
                            if (data.forceUpdate!!) {
                                context.finish()
                            }
                        }
                    }
                }
    }

    fun startDownloadTask(url: String, newVersion: String, progressBeautifulView: ProgressBeautifulView, context: Activity) {
        FileDownloader.setup(context)
        val address = context.externalCacheDir.absolutePath + "/" + newVersion + ".apk"
        var dis: Disposable = Flowable
                .create(FlowableOnSubscribe<Int> { emitter ->
                    FileDownloader
                            .getImpl()
                            .create(url)
                            .setPath(address)
                            .setListener(object : FileDownloadSampleListener() {
                                override fun error(baseDownloadTask: BaseDownloadTask?, th: Throwable) {
                                    emitter.onError(th)
                                }

                                override fun completed(baseDownloadTask: BaseDownloadTask?) {
                                    emitter.onComplete()
                                }

                                override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                                    val percent = soFarBytes.toFloat() / totalBytes.toFloat() * 100
                                    emitter.onNext(percent.toInt())
                                    super.progress(task, soFarBytes, totalBytes)
                                }
                            }).start()
                }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it > 10) {
                        progressBeautifulView.nowPercent = it / 100f
                        progressBeautifulView.nowNumber = it.toInt().toString() + "%"
                        progressBeautifulView.invalidate()
                    }
                }, {

                }, {
                    progressBeautifulView.nowPercent = 100 / 100f
                    progressBeautifulView.nowNumber = "100%"
                    progressBeautifulView.invalidate()
                    installApk(address, context)
                })
    }

    fun installApk(address: String, context: Activity) {
        val install = Intent(Intent.ACTION_VIEW)
        install.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val apkFile = File(address)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            install.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val contentUri: Uri = FileProvider.getUriForFile(MyApplication.getMyApplication(), BuildConfig.APPLICATION_ID + ".fileProvider", apkFile)
            install.setDataAndType(contentUri, "application/vnd.android.package-archive")
        } else {
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        }
        context.startActivity(install)
    }

}