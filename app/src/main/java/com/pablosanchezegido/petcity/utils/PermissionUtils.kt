package com.pablosanchezegido.petcity.utils

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

fun permissionsToRequest(context: Context, permissions: Array<String>): Array<String> {
    val permissionsToRequest = mutableListOf<String>()
    for (permission in permissions) {
        if (!checkPermission(context, permission)) {
            permissionsToRequest.add(permission)
        }
    }

    return permissionsToRequest.toTypedArray()
}

fun checkGrantPermissionResults(grantResults: IntArray): Boolean {
    for (result in grantResults) {
        if (result != PackageManager.PERMISSION_GRANTED) {
            return true
        }
    }

    return false
}

private fun checkPermission(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
