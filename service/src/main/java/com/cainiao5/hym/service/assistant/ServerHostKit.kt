package com.cainiao5.hym.service.assistant

import android.content.Context
import com.cainiao5.hym.service.R
import com.didichuxing.doraemonkit.kit.AbstractKit

/**
 * author: huangyaomian
 * created on: 2021/6/18 8:19 上午
 * description:用来配置切换不同的接口host，调试工具
 */
class ServerHostKit : AbstractKit() {
    override val icon: Int = R.drawable.icon_server_host
    override val name: Int = R.string.str_server_host_dokit

    override fun onAppInit(context: Context?) {
        //
    }

    override fun onClick(context: Context?) {
       //
    }
}