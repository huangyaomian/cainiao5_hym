package com.cainiao5.hym.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils


/**
 *Fragment的抽象基类
 * */
abstract  class BaseFragment: Fragment { //Fragment()表示主构造函数


    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

     var mActivity: AppCompatActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }
//ViewDataBinding 就是UI的具体viewDataBinding对象
  private var mBinding: ViewDataBinding?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          mBinding= bindView(view, savedInstanceState)
          mBinding?.lifecycleOwner=viewLifecycleOwner

        initConfig()
        initData()
    }

    abstract fun bindView(view: View, savedInstanceState: Bundle?):ViewDataBinding

    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * view初始化后的必要配置
     */
    open fun initConfig() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * view初始化后的必要数据
     */
    open fun initData() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initData")
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

    /**
     * 扩展liveData的observe函数
     */

    protected   fun <T:Any> LiveData<T>.observerKt(block: (T?) -> Unit){
         this.observe(viewLifecycleOwner, Observer { data->
               block(data)
           //   block.invoke(data)
         })
    }




}