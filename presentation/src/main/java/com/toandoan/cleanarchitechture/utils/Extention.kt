package com.toandoan.cleanarchitechture.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.toast(messsage: String) {
    Toast.makeText(
        this,
        messsage,
        Toast.LENGTH_LONG
    ).show()
}

fun Context.toast(@StringRes messsage: Int) {
    Toast.makeText(
        this,
        messsage,
        Toast.LENGTH_LONG
    ).show()
}