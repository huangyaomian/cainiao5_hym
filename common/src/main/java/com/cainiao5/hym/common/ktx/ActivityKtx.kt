package com.cainiao5.hym.common.ktx

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

/**
 * author: huangyaomian
 * created on: 2021/6/16 8:05 上午
 * description:activity相关的ktx，扩展函数或扩展属性
 */

//region 扩展函数

/**
 * Activity 中使用DataBinding时setContentView的简化
 * [layout]布局文件
 * @return 返回一个binding的对象实例
 */
fun <T : ViewDataBinding> Activity.bindView(@LayoutRes layout: Int): T {
    return DataBindingUtil.setContentView(this, layout)
}

/**
 * Activity 中使用DataBinding时setContentView的简化
 * [view] View对象
 * @return 返回一个binding的对象实例 T 类型的 可null的
 */
fun <T : ViewDataBinding> Activity.bindView(view: View): T? {
    return DataBindingUtil.bind<T>(view)
}

/**
 * 界面Activity的沉浸式状态栏，使得可以在状态栏里面显示部分需要的图片
 * 注意点：需要在setContentView之前调用该函数才生效
 */
fun Activity.immediateStatusBar(){
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

/**
 * 软键盘的隐藏
 * [view] 事件控件view
 */
fun Activity.dismissKeyBoard(view: View){
    val inm =getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    inm?.hideSoftInputFromWindow(view.windowToken,0)
}



//endregion

//region 扩展属性
/**
 * 扩展lifeCycleOwner属性，便于和fragment之间使用lifeCycleOwner一致性
 */
val ComponentActivity.viewLifeCycleOwner: LifecycleOwner
    get() = this

/**
 * Activit 的扩展字段，便于和fragment中使用liveDate之类的时候，参数一致性
 */
val Activity.context: Context
    get() = this

//endregion