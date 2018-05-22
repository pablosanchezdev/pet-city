package com.pablosanchezegido.petcity.utils

import android.text.SpannableString
import android.text.style.RelativeSizeSpan

fun makeRelativeSizeSpan(string: String, relativeSize: Float, start: Int, end: Int): SpannableString {
    val span = SpannableString(string)
    span.setSpan(RelativeSizeSpan (relativeSize), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    return span
}
