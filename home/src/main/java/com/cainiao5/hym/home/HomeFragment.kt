package com.cainiao5.hym.home

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.cainiao5.hym.common.base.BaseFragment
import com.cainiao5.hym.home.databinding.FragmentHomeBinding

/**
 * author: huangyaomian
 * created on: 2021/6/17 7:57 上午
 * description:home fragment
 */
class HomeFragment : BaseFragment() {

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentHomeBinding.bind(view)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_home
}