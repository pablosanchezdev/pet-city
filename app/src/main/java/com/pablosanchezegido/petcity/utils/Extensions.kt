package com.pablosanchezegido.petcity.utils

import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun View.makeSnackbar(msg: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, length).show()
}

/* It would be possible to use kotlin default parameter method values,
   but it's not useful for this case since most code is in Java */
fun View.makeSnackbarWithAction(msg: String, length: Int = Snackbar.LENGTH_INDEFINITE, @StringRes actionText: Int, listener: (View) -> Unit) {
    Snackbar.make(this, msg, length)
            .setAction(actionText, listener)
            .show()
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layout, this, attachToRoot)
