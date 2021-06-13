package com.hym.netdemo.model

/**
 * author: huangyaomian
 * created on: 2021/6/12 4:34 下午
 * description:基础的网络返回数据结果
 */
data class NetResponse (
    val code: Int, //响应码
    val data: Any?, //响应数据内容
    val message: String //响应数据的结果描述
)