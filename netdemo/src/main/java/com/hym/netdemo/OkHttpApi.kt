package com.hym.netdemo


import androidx.collection.SimpleArrayMap
import com.google.gson.Gson
import com.hym.netdemo.config.HeaderInterceptor
import com.hym.netdemo.config.KtHttpLogInterceptor
import com.hym.netdemo.config.LocalCookieJar
import com.hym.netdemo.config.RetryInterceptor
import com.hym.netdemo.support.IHttpCallback
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2021年06月09日 9:41 下午
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 */
class OkHttpApi : HttpApi{
    companion object{
        private const val TAG = "OkHttpApi"//tag
    }

    private val baseUrl = "http://api.qingyunke.com"

    //最大重连次数
    var maxRetry : Int = 0

    //存储请求，用于取消
    private val callMap = SimpleArrayMap<Any,Call>()

    //okHttpClient
    private val mClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回的数据，默认值为0，不限定
        .connectTimeout(10,TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(10,TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(10,TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true )//重连
        .followRedirects(false)//重定向
        .cache(Cache(File("sdcard/cache","okhttp"),1024))
        .cookieJar(LocalCookieJar())
        .addNetworkInterceptor(HeaderInterceptor())//公共的header 拦截器
        .addNetworkInterceptor(KtHttpLogInterceptor{
            logLevel(KtHttpLogInterceptor.LogLevel.BODY)
        })//添加网络拦截器，可以对okHttp的网络请求做拦截处理，不同于应用拦截器，这里能感知所有网络状态，比如重定向
        .addNetworkInterceptor(RetryInterceptor(maxRetry))
        .build()


    /**
     * 异步的get请求
     */
    override fun get(params: Map<String, Any>, path: String, callback: IHttpCallback) {
//        val url = "$baseUrl$path"
        val url = path
        val urlBuilder = url.toHttpUrl().newBuilder()
        for (param in params) {
            urlBuilder.addQueryParameter(param.key,param.value.toString())
        }
        val request = Request.Builder()
            .get()
            .tag(params)
            .url(urlBuilder.build())
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()
        val newCall = mClient.newCall(request)
        callMap.put(request.tag(),newCall)
        newCall.enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.message?.let { callback.onFailed(it) }
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response)
            }
        })
    }

    /**
     * 同步的get请求
     */
    override fun getSync(params: Map<String, Any>, path: String): Any? {
        TODO("Not yet implemented")
    }

    /**
     * 异步的post请求
     */
    override fun post(body: Any, path: String, callback: IHttpCallback) {
        val url = "$baseUrl$path"
        val request = Request.Builder()
            .post(Gson().toJson(body).toRequestBody())
            .url(path)
            .tag(body)
            .build()
        val newCall = mClient.newCall(request)
        callMap.put(request.tag(),newCall)
        newCall.enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(response)
            }
        })

    }

    /**
     * 同步的post请求
     */
    override fun postSync(body: Any, path: String): Any? {
        TODO("Not yet implemented")
    }

    /**
     * 取消单个请求
     */
    override fun cancelRequest(tag: Any) {
        callMap.get(tag)?.cancel()
    }

    /**
     * 取消所有请求
     */
    override fun cancelAllRequest() {
        for (i in 0 until callMap.size()){
            callMap.get(callMap.keyAt(i))?.cancel()
        }
    }
}