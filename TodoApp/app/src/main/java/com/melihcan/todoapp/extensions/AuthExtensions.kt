package com.melihcan.todoapp.extensions

import android.util.Patterns

fun String?.isUsernameValid(): Boolean {
    return !isNullOrEmpty() && this.length >= 3
}

fun String?.isPasswordValid(): Boolean {
    return !isNullOrEmpty() && this.length >= 6
}