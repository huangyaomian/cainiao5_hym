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
 * 简单封装的基类Activity
 * */
abstract class BaseActivity<ActivityBinding:ViewDataBinding> : AppCompatActivity  {
    /**
     * 无参构造函数
     */
    constructor() : super()


    /**
     * 可以填入layout布局的构造函数，使用viewBinding的方便
     * [layout] layout布局文件的id
     */
    constructor(@LayoutRes layout: Int) : super(layout)


   // protected  var mBinding: ViewDataBinding?=null
     protected  lateinit var mBinding: ActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= DataBindingUtil.setContentView(this@BaseActivity,getLayoutRes())
        mBinding.also {
            it?.lifecycleOwner=this@BaseActivity.viewLifeCycleOwner
        }

        initView()
        initConfig()
        initData()
     }



    @LayoutRes
    abstract fun getLayoutRes(): Int



    /**
     * 必要的view初始化
     */
    open fun initView() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initView")
    }

    /**
     * 必要的配置初始化
     */
    open fun initConfig() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * 必要的数据初始化
     */
    open fun initData() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initData")
    }


    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }




    /**
     * 扩展用于liveData便捷写法的函数
     * [block]liveData对象，响应change变化的逻辑块
     *
     * block: (T?) -> Unit 是个高阶函数 代码块
     */

    protected  inline fun <T:Any> LiveData<T>.observeKt(crossinline block: (T?) -> Unit){
      //this@BaseActivity 是LifecycleOwner的实现子类
       this.observe(this@BaseActivity.viewLifeCycleOwner, Observer { data->
          //  block(data) 同理
           block.invoke(data)
       })

    }

}






















