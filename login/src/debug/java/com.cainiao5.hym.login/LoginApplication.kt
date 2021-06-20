package com.cainiao5.hym.login

import com.cainiao5.hym.common.BaseApplicaition
import com.cainiao5.hym.service.moduleService
import org.koin.core.context.loadKoinModules

/**
 * author: huangyaomian
 * created on: 2021/6/19 9:55 下午
 * description:
 */
class LoginApplication: BaseApplicaition() {

    override fun initConfig() {
        super.initConfig()
        loadKoinModules(moduleService)
        loadKoinModules(moduleLogin)
    }
}