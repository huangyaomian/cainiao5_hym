package com.cainiao5.hym.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.cainiao5.hym.common.ktx.viewLifeCycleOwner

/**
 * author: huangyaomian
 * created on: 2021/6/16 8:28 上午
 * description:简单封装的基类Acticity
 */
abstract class BaseActivity<ActivityBinding : ViewDataBinding> : AppCompatActivity {

    /**
     * 无参构造函数
     */
    constructor() : super()

    /**
     * 可以填入layout布局的构造函数，使用viewBinding的方便
     */
    constructor(@LayoutRes layout: Int) : super(layout)

    protected lateinit var mBinding: ActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@BaseActivity, getLayoutRes())
        mBinding.also {
            it.lifecycleOwner = this@BaseActivity.viewLifeCycleOwner
        }
        initView()
        initConfig()
        initData()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * view初始化
     */
    open fun initView() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initView")
    }

    /**
     * 配置初始化
     */
    open fun initConfig() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * 数据初始化
     */
    open fun initData() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initData")
    }

    /**
     * 扩展用户livedata便捷写法的函数
     * [block]liveData对象，响应change变化的逻辑块
     *
     * block:(T?) -> Unit 是个高阶函数 代码块
     */
    protected inline fun <T : Any> LiveData<T>.observeKt(crossinline block: (T?) -> Unit) {
//        this@BaseActivity 是lifecycleOwner的实现子类
        this.observe(this@BaseActivity.viewLifeCycleOwner, Observer { data ->
//            block(data) 同理
            block.invoke(data)
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }


}