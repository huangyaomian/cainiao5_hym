package com.cainiao5.hym.app

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cainiao5.hym.app.databinding.ActivityMainBinding
import com.cainiao5.hym.common.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        val navController = findNavController(R.id.fcv_main)
        mBinding.bnvMain.setupWithNavController(navController)
    }

    override fun initConfig() {
        super.initConfig()
    }

    override fun initData() {
        super.initData()
    }
}