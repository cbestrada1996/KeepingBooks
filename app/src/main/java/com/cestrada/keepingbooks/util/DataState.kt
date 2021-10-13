package com.cestrada.keepingbooks.util

import java.lang.Exception

sealed class DataState<out R, out L> {
    data class  Success<out L, out T>(val data: L, val type: T): DataState<L, T>()
    data class Error<out T>(val exception: Exception, val type: T): DataState<Nothing, T>()
    object Loading: DataState<Nothing, Nothing>()
}