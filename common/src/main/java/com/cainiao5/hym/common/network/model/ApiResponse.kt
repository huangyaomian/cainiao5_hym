package com.hym.netdemo.model

import retrofit2.Response

/**
 * author: huangyaomian
 * created on: 2021/6/12 10:08 下午
 * description:密封类形式的，网络数据返回封装类
 */
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                ApiErrorResponse(
                    response.code(),
                    response.errorBody()?.string() ?: response.message()
                )
            }
        }

        fun <T> create(errorCode: Int, error: Throwable): ApiErrorResponse<T>{
            return ApiErrorResponse(
                errorCode,
                error.message ?: "Unknown Error!"
            )
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorcode: Int, val errorMessage: String) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

internal const val UNKNOWN_ERROR_CODE = -1 //未知错误码