package com.cainiao5.hym.common

import android.app.Application
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
    }
}