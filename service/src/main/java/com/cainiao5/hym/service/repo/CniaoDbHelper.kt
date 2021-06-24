package com.cainiao5.hym.service.repo

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * 数据库操作辅助帮助类
* */
object CniaoDbHelper {

    /**
     * 获取room数据表中存储的userInfo
     * return liveData形式
     */

   fun  getLiveUserDataInfo(context: Context): LiveData<CniaoUserInfo> {
        return CniaoDatabase.getInstance(context).userDao.queryLiveDataUser()
    }


    /**
     * 以普通数据对象的形式，获取userInfo
     */


    fun getUserInfo(context: Context): CniaoUserInfo?= CniaoDatabase.getInstance(context).userDao.queryUserInfo()



    /**
     * 删除数据表中的userInfo信息
     */
    fun deleteUserInfo(context: Context){
        GlobalScope.launch(Dispatchers.IO){
            getUserInfo(context)?.let { userInfo->
                CniaoDatabase.getInstance(context).userDao.deleteUser(userInfo)

            }
        }

    }


    /**
     * 新增用户数据到数据表
     */
   fun   insertUserInfo(context: Context, user  : CniaoUserInfo){
        GlobalScope.launch(Dispatchers.IO){
            CniaoDatabase.getInstance(context).userDao.insertUser(user)
        }
    }
























}







































