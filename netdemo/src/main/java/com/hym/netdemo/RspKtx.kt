package com.hym.netdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.hym.netdemo.model.ApiResponse
import com.hym.netdemo.model.DataResult
import com.hym.netdemo.model.UNKNOWN_ERROR_CODE
import okhttp3.Response
import retrofit2.Call
import retrofit2.await
import retrofit2.awaitResponse
import java.io.IOException

/**
 * author: huangyaomian
 * created on: 2021/6/13 3:24 下午
 * description:okhttp3 的 response的扩展函数，属性
 */

//region okhttp3 相关扩展

/**
 * okHttp的Call执行异步，并转化为liveData可观察结果
 */
inline fun <reified T> okhttp3.Call.toLiveData(): LiveData<T?> {
    val live = MutableLiveData<T?>()
    this.enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            live.postValue(null)
        }

        override fun onResponse(call: okhttp3.Call, response: Response) {
            if (response.isSuccessful) {
                response.toEntity<T>()
            }
        }
    })
    return live
}

/**
 * 将Response的对象，转化为需要的对象类型，也就是将body.string转为entity
 * @return 返回需要的类型对象，可能为null，如果json解析失败的话
 */
inline fun <reified T> Response.toEntity(): T? {
    if (!isSuccessful) return null
    //gson不允许我们将json对象采用String,所以单独处理
    if (T::class.java.isAssignableFrom(String::class.java)) {
        return kotlin.runCatching {
            this.body?.string()
        }.getOrNull() as? T
    }
    return kotlin.runCatching {
        Gson().fromJson(this.body?.string(), T::class.java)
    }.onFailure { e ->
        e.printStackTrace()
    }.getOrNull()
}
//endregion

//region retrofit 相关扩展
/**
 * retrofit 的 call 执行异步，并转化为livedata可观察结果
 */
fun <T: Any> Call<T>.toLivedata(): LiveData<T?> {
    val live = androidx.lifecycle.MutableLiveData<T>()
    this.enqueue(object : retrofit2.Callback<T>{
        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
           val value = if (response.isSuccessful){
               response.body()
           }else{
               null
           }
            live.postValue(value)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            live.postValue(null)
        }
    })
    return live
}

/**
 * 扩展retrofit的返回数据，调用await，并catch超时等异常
 * @return dataResult 返回格式为ApiResponse封装
 */
suspend fun <T : Any> Call<T>.serverData(): DataResult<T>{
    var result: DataResult<T> = DataResult.Loading
    kotlin.runCatching {
        this.await()
    }.onFailure {
        result = DataResult.Error(RuntimeException(it))
    }.onSuccess {
        result = DataResult.Success(it)
    }
    return result
}

/**
 * 扩展retrofit的返回数据，调用await，并catch超时异常
 * @return ApiResponse 返回格式为ApiResponse 封装
 */
suspend fun <T: Any> Call<T>.serverRsp(): ApiResponse<T> {
    var result: ApiResponse<T>
    val response = kotlin.runCatching {
        this.awaitResponse()
    }.onFailure {
        result = ApiResponse.Companion.create(UNKNOWN_ERROR_CODE,it)
    }.getOrThrow()
    result = ApiResponse.Companion.create(response)
    return result
}

//endregion
