package com.hym.netdemo.support

import androidx.lifecycle.LiveData
import com.hym.netdemo.model.ApiResponse
import com.hym.netdemo.model.UNKNOWN_ERROR_CODE
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * author: huangyaomian
 * created on: 2021/6/13 3:07 下午
 * description:用于将retrofit的call回调数据转化为livedata的adapter
 */
class LiveDataCallAdapter<R>(private val responseType: Type): CallAdapter<R, LiveData<ApiResponse<R>>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object: Callback<R>{
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(
                                ApiResponse.Companion.create(
                                    response
                                )
                            )
                        }

                        override fun onFailure(call: Call<R>, t: Throwable) {
                            postValue(
                                ApiResponse.Companion.create(
                                    UNKNOWN_ERROR_CODE,
                                    t
                                )
                            )
                        }
                    })
                }
            }
        }

    }
}