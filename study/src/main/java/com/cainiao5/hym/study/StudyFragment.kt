package com.cainiao5.hym.study

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.cainiao5.hym.common.base.BaseFragment
import com.cainiao5.hym.study.databinding.FragmentStudyBinding

/**
 * author: huangyaomian
 * created on: 2021/6/16 9:42 下午
 * description:学习tab的fragment
 */
class StudyFragment : BaseFragment() {

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentStudyBinding.bind(view)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_study
    }

    override fun initConfig() {
        super.initConfig()
//        tv_title_fragment.text = "我是学习"
    }

}