package com.cainiao5.hym.login.repo

import androidx.lifecycle.LiveData
import com.cainiao5.hym.login.net.LoginReqBody
import com.cainiao5.hym.login.net.LoginRsp
import com.cainiao5.hym.login.net.RegisterRsp

/**
 * author: huangyaomian
 * created on: 2021/6/19 9:18 下午
 * description:登陆模块的相关的抽象数据接口
 */
interface ILoginResource {

    //注册的接口返回
    val registerRsp: LiveData<RegisterRsp>

    //登陆的接口返回
    val loginRsp : LiveData<LoginRsp>

    /**
     * 校验手机号是否注册，合法
     */
    suspend fun checkRegister(mobi:String)

    /**
     * 手机号合法的基础上，调用登陆，获取登陆结果的token
     */
    suspend fun requestLogin(body: LoginReqBody)
}