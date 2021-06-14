package com.hym.netdemo.support

import androidx.lifecycle.LiveData
import com.hym.netdemo.model.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * author: huangyaomian
 * created on: 2021/6/13 3:14 下午
 * description:用户将retrofit的返回数据，转化为livedata的adapter的工厂类
 */
class LiveDataCallAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData :: class.java){
            return null
        }
        val observableType = getParameterUpperBound(0,returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java){
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType){
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodType)
    }
}