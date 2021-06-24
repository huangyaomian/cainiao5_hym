package com.cainiao5.hym.app

import com.alibaba.android.arouter.launcher.ARouter
import com.cainiao5.hym.common.BaseApplicaition
import com.cainiao5.hym.common.ktx.application
import com.cainiao5.hym.login.moduleLogin
import com.cainiao5.hym.mine.moduleMine
import com.cainiao5.hym.service.assistant.AssistantApp
import com.cainiao5.hym.service.moduleService
import okhttp3.internal.immutableListOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module

/**
 * author: huangyaomian
 * created on: 2021/6/18 5:52 下午
 * description:applicaiton 声明类
 */
class MyApplication : BaseApplicaition() {

    private val modules = immutableListOf<Module>(
        moduleService, /*moduleHome,*/ moduleLogin, moduleMine
    )


    override fun initConfig() {
        super.initConfig()

        //添加common 模块之外的其他模块，组件的koin的modules //初始化module
//        loadKoinModules(modules)
        startKoin {
            modules(modules)
        }

        //滴滴的doKit的初始化配置
        AssistantApp.initConfig(application)

        //初始化组件化
        ARouter.init(application)
    }
}