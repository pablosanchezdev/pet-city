package com.pablosanchezegido.petcity.utils

import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun View.makeSnackbar(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Snackbar.make(this, msg, length).show()
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layout, this, attachToRoot)
