package com.pablosanchezegido.petcity.utils

import android.util.Patterns

fun String.validateEmail(): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.validatePassword(minLength: Int): Boolean =
        this.length >= minLength

fun String.validateFullName(): Boolean =
        this.matches("[a-zA-Záéíóú ]+".toRegex())  // Letters, spaces and accents

fun String.validatePhoneNumber(): Boolean =
        this.matches("[0-9]{9}".toRegex())  // 9 numbers without spaces
