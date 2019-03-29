package com.myLoan.br.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.myLoan.br.R
import com.myLoan.br.tools.view.ToastUtil
import com.myLoan.br.view.LoadingDialog

abstract class BaseFragment<V, T : BasePresenter<V>> : Fragment(), BaseViewInterface {
    protected var rootView: View? = null
    protected var mContext: Context? = null

    protected var presenterBase: Array<T>? = null
    private var loadingDialog: Dialog? = null

    protected var layoutView: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenterBase = oncreatePresenter() as Array<T>
        if (presenterBase != null) {
            for (t in presenterBase!!)
                t.attachView(this as V)
        }
        setHasOptionsMenu(true)
    }

    abstract fun getLayoutId(): Int


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null)
        }
        mContext = activity
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initSet()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenterBase != null) {
            for (t in presenterBase!!) {
                if (t.isViewAttach) {
                    t.detachView(this as V)
                }
            }
        }
    }

    override fun showProgessDialog() {
        if (!activity!!.isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog.createLoadingDialog(context, resources.getString(R.string.data_loading))
            }
            loadingDialog!!.show()
        }
    }

    override fun hiddenProgessDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }


    override fun showFailture(code: Int, message: String) {
        activity!!.runOnUiThread { ToastUtil.showToast(message) }
    }

    /**
     * 在这个方法里做一些对象实例化的操作
     */
    protected abstract fun init()

    /**
     * 在这个方法里为控件设置监听事件
     */
    protected abstract fun initSet()


    protected abstract fun oncreatePresenter(): Array<BasePresenter<V>>

}
