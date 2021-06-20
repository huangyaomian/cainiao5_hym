package com.cainiao5.hym.login.net

import androidx.annotation.Keep

/**
 * author: huangyaomian
 * created on: 2021/6/19 9:02 下午
 * description:登陆请求入参实体类对象loginReqBody
 */
@Keep
data class LoginReqBody(
    val mobi : String,
    val password : String
)