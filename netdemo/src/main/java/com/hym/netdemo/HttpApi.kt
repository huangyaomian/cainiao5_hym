package com.hym.netdemo

import com.hym.netdemo.support.IHttpCallback

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2021年06月09日 9:14 下午
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 网络请求的统一接口类
 */
interface HttpApi {

    /**
     *抽象的http的get请求封装，异步
     */
    fun get(params:Map<String,Any>,path:String,callback: IHttpCallback)

    /**
     *抽象的http的get请求封装，同步
     */
    fun getSync(params:Map<String,Any>,path:String):Any?


    /**
     *抽象的http的post请求封装，异步
     */
    fun post(body:Any,path:String,callback: IHttpCallback)

    /**
     *抽象的http的post请求封装，同步
     */
    fun postSync(body:Any,path:String):Any?

    /**
     * 取消某个请求
     */
    fun cancelRequest(tag:Any)

    /**
     * 取消所有请求
     */
    fun cancelAllRequest()






}