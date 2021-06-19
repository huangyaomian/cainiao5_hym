package com.cainiao5.hym.common

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * author: huangyaomian
 * created on: 2021/6/16 7:59 上午
 * description:抽象的公用base application
 */
abstract class BaseApplicaition : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)//log level error 方能保证这句话不会报错，要么就不写这个

            androidContext(this@BaseApplicaition)
        }

        initConfig()
        initData()
        LogUtils.d("BaseApplication onCreate")
    }

    /**
     * 子类实现必要的配置初始化
     */
    open fun initConfig() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * 子类实现必要的数据初始化
     */
    open fun initData() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initData")
    }
}