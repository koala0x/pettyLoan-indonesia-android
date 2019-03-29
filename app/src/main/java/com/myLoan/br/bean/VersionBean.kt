package com.myLoan.br.bean

class VersionBean {

    var code: Int = 0
    var success: Boolean = false
    var message: String? = null

    var data: VersionBean.DataBean? = null

    public class DataBean {
        var newVersion: String? = null
        var newVersionName: String? = null
        var forceUpdate: Boolean? = false
        var apkUrl: String? = null
        var updateLog: String? = null
    }
}