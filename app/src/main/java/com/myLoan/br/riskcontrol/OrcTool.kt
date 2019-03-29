package com.myLoan.br.riskcontrol

import android.content.Context
import android.util.Log
import cn.fraudmetrix.octopus.moboxclippicture.OctopusOcrCallBack
import cn.fraudmetrix.octopus.moboxclippicture.OctopusOcrManager
import cn.fraudmetrix.octopus.moboxclippicture.bean.OctopusOcrCallBackData
import cn.fraudmetrix.octopus.moboxclippicture.bean.OctopusParam

fun getOcr(context: Context, passbackarams: String, identityCode: String, mobile: String) {
    val param = OctopusParam()
    param.passbackarams = passbackarams//透传参数; 
    param.identityCode = identityCode//身份证，作为唯 标识关联多数据源数据;
    param.mobile = mobile//手机号; 	param.realName = "张三"//姓名;
    OctopusOcrManager.getInstance().getPhoto(context, param) { data ->
        val bitmap = data.mIdcardBitmap//拍照返回的图片
        val code = data.code//拍照返回的code码
        val taskid = data.taskId//此次拍照生成的taskid
        Log.d("codecode", "$code||$taskid")
    }
}