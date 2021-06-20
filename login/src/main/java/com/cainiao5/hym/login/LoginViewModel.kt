package com.cainiao5.hym.login

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.cainiao5.hym.common.base.BaseViewModel
import com.cainiao5.hym.login.net.LoginReqBody
import com.cainiao5.hym.login.repo.ILoginResource

/**
 * author: huangyaomian
 * created on: 2021/6/19 4:55 下午
 * description:登录界面逻辑的viewModel
 */
class LoginViewModel(private val resource : ILoginResource) : BaseViewModel() {

    //账号，密码的observable对象
    val obMobile = ObservableField<String>()
    val obPassword = ObservableField<String>()

    // 注册的响应结果 liveRegisterRsp
    val liveRegisterRsp  = resource.registerRsp

    // 登录的响应结果 liveLoginRsp
    val liveLoginRsp=resource.loginRsp


    /**
     * 调用登陆，两步，
     * 1。判断手机号是否已经注册
     * 2。已经注册的，才去调用登陆接口
     */
    fun goLogin(){
        val account = obMobile.get()?:return
        //检测手机号是否账号
        checkRegister(account)
    }



    /**
     * 检查是否注册的账号
     */
    private fun checkRegister(account: String) {
        //{resource.checkRegister(mobi)} 是个lamuda 高阶函数  作为 serverAwait()函数的参数
        serverAwait { resource.checkRegister(account) }
    }

    /**
     * 调用登录接口
     * val mobi: String = "18648957777",
     * val password: String = "cn5123456"
     */
    internal  fun  repoLogin(){
        val account = obMobile.get()?:return
        val password = obPassword.get()?:return
        serverAwait {
            resource.requestLogin(LoginReqBody(account, password))
        }
    }


    fun clickWechat(ctx : Context){
        ToastUtils.showShort("点击了微信登陆")
    }

    fun clickQQ(v : View){
        ToastUtils.showShort("点击了QQ登陆")
    }

    fun clickWeibo(){
        ToastUtils.showShort("点击了微博登陆")
    }

    fun clickForgetPWD(v: View){
        ToastUtils.showShort("静态点击方式")
    }

}