package com.project.countryholiday.util

import android.util.Patterns
import android.view.View
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException
import timber.log.Timber

fun Throwable.handleThrowable(): String {
    Timber.e(this)
    val message = when {
        this is UnknownHostException -> "We've detected a network problem. Please check your internet connection and try again"
        this is HttpException && this.code() in 401..403 -> "Not authorized, make sure your API token is correct"
        this is HttpException && this.code() in 500..599 -> "Sorry, we are currently unable to complete your request. Please try again later"
        this is SocketTimeoutException || this is InterruptedException -> "Please check your connectivity and try again"
        else -> this.message ?: "Sorry, we are currently unable to complete your request. Please try again later"
    }
    return message
}

fun View.enabled(value: Boolean) {
    if (value) {
        alpha = 1F
        isEnabled = true
    } else {
        alpha = 0.4F
        isEnabled = false
    }
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}
