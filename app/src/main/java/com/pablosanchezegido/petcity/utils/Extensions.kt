package com.pablosanchezegido.petcity.utils

import android.support.design.widget.Snackbar
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import android.widget.Toast

fun View.makeSnackbar(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Snackbar.make(this, msg, length).show()
}

fun View.makeProgressBar(): ProgressBar {
    val progressBar = ProgressBar(context)
    progressBar.isIndeterminate = true
    progressBar.interpolator = AccelerateDecelerateInterpolator()
    return progressBar
}
