package com.cainiao5.hym.mine

import com.cainiao5.hym.common.network.KtRetrofit
import com.cainiao5.hym.common.utils.getBaseHost
import com.cainiao5.hym.mine.net.MineService
import com.cainiao5.hym.mine.repo.IMineResource
import com.cainiao5.hym.mine.repo.MineRepo
import com.cainiao5.hym.mine.ui.MineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 *
 * koin的 mine module
 *
 * */
val moduleMine = module {

    //service retrofit
    single {
        KtRetrofit.initConfig(getBaseHost()).getService(MineService::class.java)
    }

    single{ MineRepo(get()) }  bind IMineResource::class

    viewModel { MineViewModel(get()) }
}