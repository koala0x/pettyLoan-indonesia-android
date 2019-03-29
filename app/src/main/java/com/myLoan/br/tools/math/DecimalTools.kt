package com.myLoan.br.tools.math

import java.text.DecimalFormat

object DecimalTools {
    public fun addComma(long: Long): String {
        val decimal = DecimalFormat(",###")
        return decimal.format(long.toDouble())
    }
    @JvmStatic
    public fun keepTwoDecimal(double: Double): String {
        val decimal = DecimalFormat("#.##")
        return decimal.format(double)
    }

}