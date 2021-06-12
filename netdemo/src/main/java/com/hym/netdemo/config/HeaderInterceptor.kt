package com.hym.netdemo.config

import com.blankj.utilcode.util.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.CacheControl
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.lang.reflect.Type

/**
 * author: huangyaomian
 * created on: 2021/6/12 10:36 上午
 * description:header里面的验签和一些加密算法
 */
class HeaderInterceptor : Interceptor{

    companion object {
        private val gson: Gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .create()
        private val mapType: Type = object : TypeToken<Map<String, Any>>() {}.type
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()

        //附加的公共headers，封装clientInfo，devicesInfo等。也可以在post请求中，自定义封装headers的字段体内容
        //注意这里，服务器用户校验的字段，只能是以下的字段内容，可以缺失，但是不能额外添加，因为服务端未做处理
        val attachHeaders = mutableListOf<Pair<String,String>>(
            "appid" to NET_CONFIG_APPID,
            "platform" to "android",//如果重复请求，可能会报重复签名错误，vapi平台标记不会
            "timestamp" to System.currentTimeMillis().toString(),

            "brand" to DeviceUtils.getManufacturer(),
            "model" to DeviceUtils.getModel(),
            "uuid" to DeviceUtils.getUniqueDeviceId(),
            "network" to NetworkUtils.getNetworkType().name,
            "system" to DeviceUtils.getSDKVersionName(),

            "version" to AppUtils.getAppVersionName()
        )
        //token仅有值的时候才传递
        val tokenStr = "sss"
        val localToken = SPStaticUtils.getString(SP_KEY_USER_TOKEN,tokenStr)
        if (localToken.isNotEmpty()){
            attachHeaders.add("token" to localToken)
        }
        val signHeaders = mutableListOf<Pair<String, String>>()
        signHeaders.addAll(attachHeaders)
        //get请求，参数
        if (originRequest.method == "GET"){
            originRequest.url.queryParameterNames.forEach{ key ->
                signHeaders.add(key to (originRequest.url.queryParameter(key)?:""))
            }
        }

        //post的请求 formBody形式，或json形式的，都需要将内部的字段，遍历出来，参与sign的计算
        val requestBody = originRequest.body
        if (originRequest.method == "POST"){
            //formBody
            if (requestBody is FormBody){
                for (i in 0 until requestBody.size){
                    signHeaders.add(requestBody.name(i) to requestBody.value(i))
                }
            }
            if (requestBody != null) {
                LogUtils.d("mika test3 requestBody?.contentType()?.type = ${requestBody.contentType()?.type} ---- requestBody.contentType()?.subtype = ${requestBody.contentType()?.subtype}")
            }
            //json 的 body 需要将requestBody反序列化json转为map application/json
            if (requestBody?.contentType()?.type == "application" && requestBody.contentType()?.subtype == "json"){
                kotlin.runCatching {
                    val buffer = Buffer()
                    requestBody.writeTo(buffer)
                    buffer.readByteString().utf8()
                }.onSuccess {
                    val map = gson.fromJson<Map<String,Any>>(it, mapType)
                    map.forEach{entry ->
                        //value 目前json单层级
                        signHeaders.add(entry.key to entry.value.toString())

                    }
                }
            }
        }

        //算法：都必须是非空参数！！！ sign = MD5（ascii排序后的 headers 及params的key = valye拼接&后，最后拼接appkey和valye）//32位的大写
        val signValue = signHeaders
            .sortedBy { it.first }
            .joinToString("&") { "${it.first}=${it.second}" }
            .plus("&appkey=$ENT_CONFIG_APPKEY")

        val newBuilder = originRequest.newBuilder()
            .cacheControl(CacheControl.FORCE_NETWORK)
        attachHeaders.forEach { newBuilder.header(it.first, it.second) }
        newBuilder.header("sign", EncryptUtils.encryptMD5ToString(signValue))

        if (originRequest.method == "POST" && requestBody != null) {
            newBuilder.post(requestBody)
        } else if (originRequest.method == "GET") {
            newBuilder.get()
        }
        return chain.proceed(newBuilder.build())
    }
}