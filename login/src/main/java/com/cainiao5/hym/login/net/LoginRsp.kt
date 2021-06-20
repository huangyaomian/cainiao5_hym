package com.cainiao5.hym.login.net

import androidx.annotation.Keep

/**
 * author: huangyaomian
 * created on: 2021/6/19 8:58 下午
 * description:登陆模块相关的结果响应类
 */

/**
 * 查询手机号码石佛u注册的接口响应
 */
@Keep
data class RegisterRsp(
    val is_register : Int = FLAG_UN_REGISTERED
){
    companion object{
        const val FLAG_IS_REGISTERED = 1//已经注册的
        const val FLAG_UN_REGISTERED = 0//0 表示未注册
    }
}

/**
 * 手机号和密码登陆，接口响应
 */
@Keep
data class LoginRsp(
    val course_permission : Boolean,
    val token : String?,
    val user : User?
){
    @Keep
    data class User(
        val id : Int,//用户id
        val logo_url : String?,//用户头像
        val reg_time : String?,//用户注册时间
        val username : String?//用户名
    )
}

/**
 * 手机号和密码登录成功  接口响应返回实体类LoginRsp(即为CniaoUserInfo )
 */
// 给 LoginRsp 取别名  CniaoUserInfo,不影响其他地方调用LoginRsp
//typealias LoginRsp = CniaoUserInfo