package com.cainiao5.hym.common.network

import com.hym.netdemo.config.HeaderInterceptor
import com.hym.netdemo.config.KtHttpLogInterceptor
import com.hym.netdemo.config.LocalCookieJar
import com.hym.netdemo.support.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * author: huangyaomian
 * created on: 2021/6/12 10:48 下午
 * description:封装的retrofit请求类
 */
object KtRestrofit {

    private val mOkClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)//完整请求超时时长，从发起到接收返回数据，默认值0，不限定
        .connectTimeout(10, TimeUnit.SECONDS)//与服务器建立连接的时长，默认10s
        .readTimeout(10,TimeUnit.SECONDS)//读取服务器返回数据的时长
        .writeTimeout(10, TimeUnit.SECONDS)//向服务器写入数据的时长，默认10s
        .retryOnConnectionFailure(true)//重连
        .followRedirects(false)//重定向
        .cookieJar(LocalCookieJar())
        .addNetworkInterceptor(HeaderInterceptor())//公共header的拦截器
        .addNetworkInterceptor(KtHttpLogInterceptor{
            logLevel(KtHttpLogInterceptor.LogLevel.BODY)
        })
        .build()

    private val retrofitBUilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(mOkClient)

    private var retrofit: Retrofit? = null //retrofit 请求client

    /**
     * 初始化配置
     * [baseUrl] 项目接口的基类url，以/结尾
     */
    fun initConfig(baseUrl: String, okClient: OkHttpClient = mOkClient): KtRestrofit {
        retrofit = retrofitBUilder.baseUrl(baseUrl).client(okClient).build()
        return this
    }

    /**
     * 获取retrofit的service对象
     * [serviceClazz] 定义retrofit的service 接口类
     */
    fun <T> getService(serviceClazz: Class<T>): T {
        if (retrofit == null){
            throw UninitializedPropertyAccessException("Retrofit 必须初始化，需要配置baseURL")
        }else{
            return this.retrofit!!.create(serviceClazz)
        }
    }
}