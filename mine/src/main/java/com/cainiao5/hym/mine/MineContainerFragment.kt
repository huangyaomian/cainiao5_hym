package com.cniao5.mine

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.cainiao5.hym.common.base.BaseFragment
import com.cainiao5.hym.mine.R
import com.cainiao5.hym.mine.databinding.FragmentContainerMineBinding



/**
 * 0428
 * 我的模块主fragment ，内部安装了navigation的容器,盛装 MineFragment
 *
 *
 * */
class MineContainerFragment: BaseFragment() {
    override fun getLayoutRes(): Int {
    return R.layout.fragment_container_mine
    }


    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
     return  FragmentContainerMineBinding.bind(view)
    }




}


















