package com.cainiao5.hym.mine.net

import com.cainiao5.hym.service.network.BaseRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MineService {

    /**
     * 用户详情信息的获取
     */
    @GET("/member/userinfo")
    fun getUserInfo(@Header("token") token: String?): Call<BaseRsp>


}