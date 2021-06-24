package com.cainiao5.hym.mine.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.cainiao5.hym.common.network.serverData
import com.cainiao5.hym.mine.net.MineService
import com.cainiao5.hym.mine.net.UserInfoRsp
import com.cainiao5.hym.service.network.onBizError
import com.cainiao5.hym.service.network.onBizOK
import com.cainiao5.hym.service.network.onFailure
import com.cainiao5.hym.service.network.onSuccess

class MineRepo(private val service: MineService): IMineResource {

    private val _userInfoRsp = MutableLiveData<UserInfoRsp>()
    override val userInfoLiveData: LiveData<UserInfoRsp> =_userInfoRsp

    override suspend fun getUserInfo(token: String?) {
         service.getUserInfo(token)
             .serverData()
             .onSuccess {
                 onBizError{code,message->
                     LogUtils.w("获取用户信息 BizError $code,$message")
                 }
                 onBizOK<UserInfoRsp>{ code, data, message ->
                     LogUtils.i("获取用户信息 BizOK $data")
                     _userInfoRsp.value=data
                     return@onBizOK
                 }
             }
             .onFailure{
                 LogUtils.e("获取用户信息 接口异常 ${it.message}")
             }
    }

}