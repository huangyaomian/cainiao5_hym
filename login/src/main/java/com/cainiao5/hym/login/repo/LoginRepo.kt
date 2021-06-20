package com.cainiao5.hym.login.repo

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import com.cainiao5.hym.common.model.SingleLiveData
import com.cainiao5.hym.common.network.serverData
import com.cainiao5.hym.login.net.LoginReqBody
import com.cainiao5.hym.login.net.LoginRsp
import com.cainiao5.hym.login.net.LoginService
import com.cainiao5.hym.login.net.RegisterRsp
import com.cainiao5.hym.service.network.onBizError
import com.cainiao5.hym.service.network.onBizOK
import com.cainiao5.hym.service.network.onFailure
import com.cainiao5.hym.service.network.onSuccess

/**
 * author: huangyaomian
 * created on: 2021/6/19 9:12 下午
 * description:登陆的数据类的实现方式
 */
class LoginRepo(private val service : LoginService) : ILoginResource {

    //私有的  _registerRsp 注册的响应结果
    private val  _registerRsp=SingleLiveData<RegisterRsp>()
    //私有的  _loginRsp登录的响应结果
    private  val  _loginRsp=SingleLiveData<LoginRsp>()


    //对外公开的 可以 setValue postValue   registerRsp= _registerRsp 注册的响应结果
    override val registerRsp: LiveData<RegisterRsp> =_registerRsp
    //对外公开的 可以 setValue postValue   loginRsp= _loginRsp 登录的响应结果
    override val loginRsp: LiveData<LoginRsp> =_loginRsp


    override suspend fun checkRegister(mobi: String) {
        service.isRegister(mobi).serverData()
            .onSuccess{
                onBizOK<RegisterRsp> { code, data, message ->
                    _registerRsp.value=data
                    LogUtils.i("是否注册 BizOK $data")
                    return@onBizOK
                }
                onBizError { code, message ->
                    LogUtils.w("是否注册 BizError $code,$message")
                }

            }
            .onFailure {
                LogUtils.e("是否注册 接口异常 ${it.message}")
            }

    }

    /**
     * 登录请求
     * */
    override suspend fun requestLogin(body: LoginReqBody) {
        service.login(body)
            .serverData()
            .onSuccess {
                onBizOK<LoginRsp>  { code, data, message ->
                    _loginRsp.value=data
                    //登录成功后,用户信息同步到room数据库，
                    LogUtils.i("登录接口成功: BizOK $data")
                }
                onBizError { code, message ->
                    LogUtils.w("登录接口 BizError $code,$message")
                }
            }
            .onFailure {   LogUtils.e("登录接口 异常 ${it.message}") }

    }
}