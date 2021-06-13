package com.hym.netdemo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.hym.netdemo.model.*
import com.hym.netdemo.support.HymUtils
import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvHello = findViewById<TextView>(R.id.tv_hello)
        val tvHello1 = findViewById<TextView>(R.id.tv_hello1)
        val tvHello2 = findViewById<TextView>(R.id.tv_hello2)

//region retrofit 请求
       val retrofitCall = KtRestrofit.initConfig("https://course.api.cniao5.com/")
           .getService(CniaoService::class.java)
           .userInfo()

        //ktx的livedata
        val liveInfo = retrofitCall.toLivedata()
        liveInfo.observe(this, Observer {
            LogUtils.d("mika retrofit userinfo ${it.toString()}")
                tvHello.text = it.toString()
        })

        val loginCall = KtRestrofit.initConfig("https://course.api.cniao5.com/",OkHttpApi.getInstance().getClient())
            .getService(CniaoService::class.java)
            .login(LoginReq())

        lifecycleScope.launch {
            //表达式声明，使用when,协程，同步的代码形式，执行异步的操作
            when(val serverRsp = loginCall.serverRsp()){
                is ApiSuccessResponse -> {
                    LogUtils.i("mika apiservice ${serverRsp.body.toString()}")
                    tvHello1.text = HymUtils.decodeData(serverRsp.body.data.toString())
                }
                is ApiErrorResponse -> {
                    LogUtils.e("mika apiservice ${serverRsp.errorMessage}")
                    tvHello1.text = serverRsp.errorMessage
                }
                is ApiEmptyResponse -> {
                    LogUtils.d("mika empty apireoponese")
                    tvHello1.text = "empty apireoponese"
                }
            }
        }

        KtRestrofit.initConfig("https://course.api.cniao5.com/")
            .getService(CniaoService::class.java)
            .userInfo2().observe(this, Observer {
                LogUtils.d("mika retrofit liveRsp ${it.toString()}")
            })
        //endregion


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