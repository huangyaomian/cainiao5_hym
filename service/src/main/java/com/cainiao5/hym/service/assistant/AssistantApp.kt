package com.cainiao5.hym.service.assistant

import android.app.Application
import com.didichuxing.doraemonkit.DoKit

/**
 * author: huangyaomian
 * created on: 2021/6/18 8:27 上午
 * description:配置dokit的工具类
 */
object AssistantApp {
    fun initConfig(application: Application){
        DoKit.Builder(application)
            .productId("65f032e244f2d5b01a0ac6231f9ec5f0")
            .customKits(mutableListOf(ServerHostKit()))
            .build()

//        DoraemonKit.install(application, mutableListOf( ServerHostKit()))
    }
}