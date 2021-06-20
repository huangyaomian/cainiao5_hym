package com.cainiao5.hym.login.net

import com.cainiao5.hym.service.network.BaseRsp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * author: huangyaomian
 * created on: 2021/6/19 8:33 下午
 * description:登陆模块的接口
 */
interface LoginService {
    @GET("accounts/phone/is/register")
    fun isRegister(@Query("mobi") mobi: String): Call<BaseRsp>

    @POST("accounts/course/10301/login")
    fun login(@Body reqBody: LoginReqBody): Call<BaseRsp>
}