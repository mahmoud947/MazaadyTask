package com.example.core.base

data class BaseResponse<T>(
    val code: Int,
    val msg: String,
    val data: T
)