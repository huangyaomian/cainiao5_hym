package com.hym.netdemo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvHello = findViewById<TextView>(R.id.tv_hello)
        val map = mapOf(
            "key" to "free",
            "appid" to "0",
            "msg" to "你好，我想和你做朋友，可以吗，哈哈哈"

        )

        val httpApi = OkHttpApi()
        httpApi.get(map,"/api.php",object:IHttpCallback{
            override fun onSuccess(data: Any?) {
                LogUtils.d("success result: ${data.toString()}")
                runOnUiThread {
                    tvHello.text = data.toString()
                }

            }

            override fun onFailed(error: Any?) {
                LogUtils.d("failed msg: ${error.toString()}")
            }
        })

        httpApi.post(LoginReq(),"",object:IHttpCallback{
            override fun onSuccess(data: Any?) {
                LogUtils.d("success result: ${data.toString()}")
                runOnUiThread {
                    tvHello.text = data.toString()
                }
            }

            override fun onFailed(error: Any?) {
                LogUtils.d("failed msg: ${error.toString()}")
            }
        })
    }


    data class LoginReq(
        val mobi :String = "18648957777",
        val password :String = "cn5123456"
    )
}