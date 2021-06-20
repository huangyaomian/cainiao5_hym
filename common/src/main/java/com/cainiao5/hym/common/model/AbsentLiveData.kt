package com.cainiao5.hym.common.model

import androidx.lifecycle.LiveData

/**
 * author: huangyaomian
 * created on: 2021/6/20 4:54 下午
 * description:创建一个空的liveData的对象类
 */
class AbsentLiveData<T:Any?> private constructor(): LiveData<T>() {
    init {
        postValue(null)
    }

    companion object{
        fun <T:Any?> create():LiveData<T>{
            return AbsentLiveData()
        }

    }
}