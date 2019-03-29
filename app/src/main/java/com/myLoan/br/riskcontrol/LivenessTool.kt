package com.myLoan.br.riskcontrol

import android.content.Context
import android.util.Log
import cn.fraudmetrix.octopus.livenesssdk.OctopusLivenessManager

fun liveness(context: Context) {
    OctopusLivenessManager.getInstance().detectLiveness(context) { i, info ->
        if (i == 0) {
            Log.d("verificationPackage", info.verificationPackage)
            Log.d("verificationPackageFull", info.verificationPackageFull)
            Log.d("verificatidddd", info.verificationPackageWithFanpaiFull)
        }
    }
}