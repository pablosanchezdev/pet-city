package com.pablosanchezegido.petcity.utils

import java.text.DecimalFormatSymbols
import java.util.Locale

fun getLocaleDecimalSeparator(): String {
    val decimalSymbols = DecimalFormatSymbols(Locale.getDefault())
    return decimalSymbols.decimalSeparator.toString()
}
