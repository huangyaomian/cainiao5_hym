package com.cainiao5.hym.app

import MineFragment
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cainiao5.hym.app.databinding.ActivityMainBinding
import com.cainiao5.hym.common.base.BaseActivity
import com.cainiao5.hym.common.widget.BnvMediator
import com.cainiao5.hym.course.CourseFragment
import com.cainiao5.hym.home.HomeFragment
import com.cainiao5.hym.study.StudyFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object{
        const val INDEX_HOME = 0 //首页home对应的索引位置
        const val INDEX_COURSE = 1 //课程fg对应的索引位置
        const val INDEX_STUDY = 2 //学习fg对应的索引位置
        const val INDEX_MINE = 3 //我的fg对应的索引位置
    }

    private val fragments = mapOf<Int, ReFragment>(
        INDEX_HOME to { HomeFragment() },
        INDEX_COURSE to { CourseFragment() },
        INDEX_STUDY to { StudyFragment() },
        INDEX_MINE to { MineFragment() },

    )

    override fun getLayoutRes()=  R.layout.activity_main


    override fun initView() {
        super.initView()
        mBinding.apply {
            vp2Main.adapter = MainViewPagerAdapter(this@MainActivity,fragments)

            vp2Main.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when(position){
                        INDEX_HOME-> Toast.makeText(this@MainActivity,R.string.str_home,Toast.LENGTH_SHORT).show()
                        INDEX_COURSE-> Toast.makeText(this@MainActivity,R.string.str_course,Toast.LENGTH_SHORT).show()
                        INDEX_STUDY->Toast.makeText(this@MainActivity,R.string.str_study,Toast.LENGTH_SHORT).show()
                        INDEX_MINE->Toast.makeText(this@MainActivity,R.string.str_mine,Toast.LENGTH_SHORT).show()
                        else->error("角标越界")
                    }

                }
            })


            BnvMediator(bnvMain, vp2Main){bnv, vp2 ->
                vp2.isUserInputEnabled = true
            }.attach()
        }
    }

    override fun initConfig() {
        super.initConfig()
    }

    override fun initData() {
        super.initData()
    }
}

/**
 * 首页的viewPager2的适配器
 */

class MainViewPagerAdapter(fragmentActivity: FragmentActivity,
                           private val fragments: Map<Int, ReFragment>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position] ?.invoke()?:  throw IndexOutOfBoundsException("ViewPager接收参数index越界啦!")
    }

}

//类型别名定义
typealias ReFragment = () -> Fragment