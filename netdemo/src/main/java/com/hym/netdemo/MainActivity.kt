package com.hym.netdemo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.hym.netdemo.model.*
import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvHello = findViewById<TextView>(R.id.tv_hello)

//region retrofit 请求
       val retrofitCall = KtRestrofit.initConfig("https://course.api.cniao5.com/")
           .getService(CniaoService::class.java)
           .userInfo()

        //ktx的livedata
        val liveInfo = retrofitCall.toLivedata()
        liveInfo.observe(this, Observer {
            LogUtils.d("mika retrofit userinfo ${it.toString()}")
        })

        val loginCall = KtRestrofit.initConfig("https://course.api.cniao5.com/",OkHttpApi.getInstance().getClient())
            .getService(CniaoService::class.java)
            .login(LoginReq())

        lifecycleScope.launch {
            //表达式声明，使用when,协程，同步的代码形式，执行异步的操作
            when(val serverRsp = loginCall.serverRsp()){
                is ApiSuccessResponse -> {
                    LogUtils.i("mika apiservice ${serverRsp.body.toString()}")
                }
                is ApiErrorResponse -> {
                    LogUtils.e("mika apiservice ${serverRsp.errorMessage}")
                }
                is ApiEmptyResponse -> {
                    LogUtils.d("mika empty apireoponese")
                }
            }
        }

        KtRestrofit.initConfig("https://course.api.cniao5.com/")
            .getService(CniaoService::class.java)
            .userInfo2().observe(this, Observer {
                LogUtils.d("mika retrofit liveRsp ${it.toString()}")
            })


/*        val loginBody = LoginReq()
        httpApi.post(
            loginBody,
            "https://course.api.cniao5.com/accounts/course/10301/login",
            object : IHttpCallback {
                override fun onSuccess(data: Any?) {
                    LogUtils.d("success result: ${data.toString()}")
                    runOnUiThread {
                        val toString = data.toString()
                        val (code, dataObj, message) = GsonUtils.fromJson<NetResponse>(
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
//        httpApi.cancelRequest(loginBody)*/
    }


    data class LoginReq(
        val mobi: String = "18648957777",
        val password: String = "cn5123456"
    )
}

interface CniaoService {
    @POST("accounts/course/10301/login")
    fun login(@Body body: MainActivity.LoginReq): retrofit2.Call<NetResponse>

    @GET("member/userinfo")
    fun userInfo():retrofit2.Call<NetResponse>

    @GET("member/userinfo")
    fun userInfo2(): LiveData<ApiResponse<NetResponse>>
}