package com.segunfrancis.shared.extension

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

fun Throwable.handleThrowable(): String {
    return when {
        this is UnknownHostException -> "We've detected a network problem. Please check your internet connection and try again"
        this is HttpException && this.code() in 401..403 -> "Not authorized, make sure your API token is correct"
        this is HttpException && this.code() in 500..599 -> "Sorry, we are currently unable to complete your request. Please try again later"
        this is SocketTimeoutException || this is InterruptedException -> "Please check your connectivity and try again"
        else -> this.message
            ?: "Sorry, we are currently unable to complete your request. Please try again later"
    }
}
