package com.cainiao5.hym.course

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.cainiao5.hym.common.base.BaseFragment
import com.cainiao5.hym.course.databinding.FragmentCourseBinding

/**
 * author: huangyaomian
 * created on: 2021/6/17 8:08 上午
 * description:course fragment
 */
class CourseFragment : BaseFragment() {
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentCourseBinding.bind(view)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_course
    }

}