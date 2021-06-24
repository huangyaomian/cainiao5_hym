package com.cainiao5.hym.login

import com.cainiao5.hym.common.network.KtRetrofit
import com.cainiao5.hym.login.net.LoginService
import com.cainiao5.hym.login.repo.ILoginResource
import com.cainiao5.hym.login.repo.LoginRepo
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * author: huangyaomian
 * created on: 2021/6/20 10:49 上午
 * description:
 */
val moduleLogin = module {

    //service retrofit
    single {
        KtRetrofit.initConfig("https://course.api.cniao5.com/")
            .getService(LoginService ::class.java)
    }
    //repo LoginResource
    single { LoginRepo(get()) } bind ILoginResource::class
    //viewModel
    viewModel { LoginViewModel(get()) }
}