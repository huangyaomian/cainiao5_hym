package com.hym.netdemo.support

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2021年06月09日 9:34 下午
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * 网络请求的接口回调
 */
interface IHttpCallback {

    /**
     * 网络请求成功的回调
     * [data] 返回回调的数据结果
     */
    fun onSuccess(data:Any?)


    /**
     * 接口回调失败
     * [error]错误信息的数据类
     */
    fun onFailed(error:Any?)
}