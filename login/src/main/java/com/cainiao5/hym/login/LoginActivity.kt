package com.cainiao5.hym.login

import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.cainiao5.hym.common.base.BaseActivity
import com.cainiao5.hym.login.databinding.ActivityLoginBinding
import com.cainiao5.hym.login.net.RegisterRsp
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * author: huangyaomian
 * created on: 2021/6/19 3:50 下午
 * description:登陆
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel :LoginViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        super.initView()
        mBinding.apply {
            vm = viewModel
            //点击事件
            mtoolbarLogin.setNavigationOnClickListener{finish()}
            tvRegisterLogin.setOnClickListener{
                ToastUtils.showShort("当前课程为事件注册账号功能")
            }
        }
    }

    override fun initConfig() {
        super.initConfig()

        viewModel.apply {
            liveRegisterRsp.observe(this@LoginActivity, Observer {
                //放回的是否注册的响应结果:it.is_register =1  已经注册的，直接去登录
                if (it.is_register == RegisterRsp.FLAG_IS_REGISTERED){
                    ToastUtils.showShort("已注册：$it")
                    repoLogin()
                }else{
                    ToastUtils.showShort("手机号未注册}")
                }
            })

            liveLoginRsp.observeKt { rsp ->
                ToastUtils.showShort("登陆结果：${rsp.toString()}")
                rsp?.let {
                    //用户信息同步到数据库
                }
                finish()
            }
        }
    }
}