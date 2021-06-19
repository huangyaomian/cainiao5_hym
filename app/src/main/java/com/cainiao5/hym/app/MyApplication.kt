package com.cainiao5.hym.app

import com.cainiao5.hym.common.BaseApplicaition
import com.cainiao5.hym.common.ktx.application
import com.cainiao5.hym.service.assistant.AssistantApp

/**
 * author: huangyaomian
 * created on: 2021/6/18 5:52 下午
 * description:applicaiton 声明类
 */
class MyApplication : BaseApplicaition() {
    override fun initConfig() {
        super.initConfig()
        //dokit初始化配置
        AssistantApp.initConfig(application)
    }
}