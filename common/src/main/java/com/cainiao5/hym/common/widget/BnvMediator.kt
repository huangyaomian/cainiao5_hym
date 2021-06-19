package com.cainiao5.hym.common.widget

import android.view.MenuItem
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * author: huangyaomian
 * created on: 2021/6/19 11:25 上午
 * description:BottomNavigationView和ViewPager2关联的中介者
 */
class BnvMediator(
    private val bnv : BottomNavigationView,
    private val vp2 : ViewPager2,
    private val  config : ((bnv : BottomNavigationView, vp2 : ViewPager2) -> Unit)? = null
) {
    //存储bottomNavigationView的menu的item和其自身position的对应关系
    private val map = mutableMapOf<MenuItem, Int>()

    init {
        //初始化bnv的item和index对应关系
        bnv.menu.forEachIndexed { index, item ->
            map[item] = index
        }
    }

    fun attach(){
        config?.invoke(bnv, vp2)

        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bnv.selectedItemId = bnv.menu[position].itemId
            }
        })

        bnv.setOnNavigationItemSelectedListener { item ->
            vp2.currentItem = map[item] ?: error("没有对应 ${item.title} 的viewPager2的元素")
            true
        }
    }

}