package com.yetk.yetkschedule.data.remote.model

sealed class Response<out T> {
    data object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Failure(
        val e: Exception?
    ): Response<Nothing>()

    fun <T>isSuccess(vararg values: Response<T>): Boolean{
        values.forEach {
            if(it !is Success) {
                return false
            }
        }
        return true
    }

    fun <T>isFailure(vararg values: Response<T>): Boolean{
        values.forEach {
            if(it !is Failure) {
                return false
            }
        }
        return true
    }

    fun <T>isLoading(vararg values: Response<T>): Boolean{
        values.forEach {
            if(it !is Loading) {
                return false
            }
        }
        return true
    }
}