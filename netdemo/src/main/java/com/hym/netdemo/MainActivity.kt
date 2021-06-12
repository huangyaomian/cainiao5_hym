package com.hym.netdemo

import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.hym.netdemo.model.NetResponse
import com.hym.netdemo.support.HymUtils
import com.hym.netdemo.support.IHttpCallback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvHello = findViewById<TextView>(R.id.tv_hello)


        val httpApi = OkHttpApi()
        /*httpApi.get(emptyMap(),"https://course.api.cniao5.com/member/userinfo",object: IHttpCallback {
            override fun onSuccess(data: Any?) {
                LogUtils.d("success result: ${data.toString()}")
                runOnUiThread {
                    tvHello.text = data.toString()
                }

            }

            override fun onFailed(error: Any?) {
                LogUtils.d("failed msg: ${error.toString()}")
            }
        })*/

        val loginBody = LoginReq()
        httpApi.post(loginBody,"https://course.api.cniao5.com/accounts/course/10301/login",object: IHttpCallback {
            override fun onSuccess(data: Any?) {
                LogUtils.d("success result: ${data.toString()}")
                runOnUiThread {
                    val toString = data.toString()
                    val (code,dataObj,message) = GsonUtils.fromJson<NetResponse>(
                        toString,
                        NetResponse::class.java
                    )
                    tvHello.text = HymUtils.decodeData(data.toString())
                }
            }

            override fun onFailed(error: Any?) {
                LogUtils.d("failed msg: ${error.toString()}")
            }
        })

        SystemClock.sleep(200)
//        httpApi.cancelRequest(loginBody)
    }


    data class LoginReq(
        val mobi :String = "18648957777",
        val password :String = "cn5123456"
    )
}