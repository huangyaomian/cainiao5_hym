package com.cainiao5.hym.mine.ui

import com.cainiao5.hym.common.base.BaseViewModel
import com.cainiao5.hym.mine.repo.IMineResource


/**
 * Mine模块的viewModel
 * */
class MineViewModel(private val repo: IMineResource): BaseViewModel() {


  // val  liveUserLiveData:MutableLiveData<CniaoUserInfo> = MutableLiveData<CniaoUserInfo>()

  //通过网络获取用户信息UserInfoRsp
  val  userInfoLiveData  = repo.userInfoLiveData


  /**
   * 获取用户信息数据
   */
  fun getUserInfo(token: String?) = serverAwait {
    token?.let {
      repo.getUserInfo(token)
    }
  }


}