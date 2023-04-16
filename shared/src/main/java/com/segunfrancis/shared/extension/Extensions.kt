package com.segunfrancis.shared.extension

import android.util.Patterns
import android.view.View
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

fun Throwable.handleThrowable(): String {
    return when {
        this is UnknownHostException -> "We've detected a network problem. Please check your internet connection and try again"
        this is HttpException && this.code() == 400 -> "Something is wrong with your request parameters."
        this is HttpException && this.code() == 401 -> "Unauthorized request. Did you remember your API key?"
        this is HttpException && this.code() == 402 -> "Upgrade required or account is delinquent. Payment is required."
        this is HttpException && this.code() == 403 -> "Insecure request. HTTPS requests only."
        this is HttpException && this.code() == 429 -> "Monthly rate limit exceeded."
        this is HttpException && this.code() in 500..599 -> "Sorry, Something went wrong on our end and we've been notified. Please try again later"
        this is SocketTimeoutException || this is InterruptedException -> "Please check your connectivity and try again"
        else -> this.message
            ?: "Sorry, we are currently unable to complete your request. Please try again later"
    }
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
