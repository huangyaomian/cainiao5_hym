package com.cainiao5.hym.mine.repo

import androidx.lifecycle.LiveData
import com.cainiao5.hym.mine.net.UserInfoRsp

/**
 * 日期： 2020年10月19日 12:21
 * Mine模块的数据获取 接口
 */
interface IMineResource {

    /**
     * 用户信息的返回数据类 liveData
     */
    val userInfoLiveData: LiveData<UserInfoRsp>

    /**
     * 获取userInfo的api函数
     */
    suspend fun getUserInfo(token: String?)

}