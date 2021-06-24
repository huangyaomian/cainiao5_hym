package com.cainiao5.hym.mine.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ObservableField
import com.cainiao5.hym.mine.R
import com.cainiao5.hym.mine.databinding.VItemSettingsBinding

class ItemSettingsView @JvmOverloads
 constructor(context: Context,
             attrs: AttributeSet?=null,
           defStyleAttr: Int = 0) : ConstraintLayout(context,attrs,defStyleAttr) {

 //初始化 ItemSettingsBean
 private var itemBean = ItemSettingsBean()
 private val  obItemInfo = ObservableField<ItemSettingsBean> (itemBean)



     init {
         //1、 dabinding 绑定组合控件的布局v_item_settings
         VItemSettingsBinding.inflate(LayoutInflater.from(context), this, true).apply {
               info  = obItemInfo
         }

         setBackgroundColor(Color.LTGRAY)


          //配置控件的各个资源属性
         val attributes =  context.obtainStyledAttributes(attrs,R.styleable.ItemSettingsView).apply {
             //icon资源设置:图片
              val iconRes= getResourceId(R.styleable.ItemSettingsView_icon,R.drawable.ic_gift_card)
             itemBean.iconRes = iconRes
             //icon资源设置:颜色
             val iconRGB=getColor(R.styleable.ItemSettingsView_iconColor,0)
             itemBean.iconColor =iconRGB

             //title设置:标题
             val title = getString(R.styleable.ItemSettingsView_title)?:   ""
             itemBean.title = title
             //title设置:颜色
            val titleRGB=getColor(R.styleable.ItemSettingsView_titleColor,resources.getColor(R.color.colorPrimaryText))
             itemBean.titleColor = titleRGB

             //desc设置:标题

             val desc = getString(R.styleable.ItemSettingsView_desc)?:""
                 itemBean.desc=desc
             //desc设置:标题颜色
             val descRGB=getColor(R.styleable.ItemSettingsView_descColor,0)
             itemBean.descColor = descRGB

             //arrow设置:图片
                  val arrowRes=getResourceId(R.styleable.ItemSettingsView_arrow,R.drawable.ic_right)
             itemBean.arrowRes=arrowRes

             //arrow设置:图片颜色
             val arrowRGB=getColor(R.styleable.ItemSettingsView_arrowColor,resources.getColor(R.color.colorSecondaryText))
             itemBean.arrowColor = arrowRGB
         }

         // 回收 recycle
          attributes.recycle()



     }


    //region 设置资源属性
    /**
     * 设置整个item 的对象info
     * 如果要创建新的ItemSettingsBean对象
     */
    fun setInfo(info: ItemSettingsBean) {
        itemBean = info
        obItemInfo.set(info)
    }

    /**
     * 设置title属性
     */
    fun setTitle(title: String) {
        itemBean.title = title
    }


    /**
     * 设置内容描述属性
     */
    fun setDesc(desc: String) {
        itemBean.desc = desc
    }


    /**
     * 设置icon图标属性
     */
    fun setIcon(iconRes: Any) {
        itemBean.iconRes = iconRes
    }


    /**
     * 设置icon图标
     */
    fun setArrow(arrowRes: Any) {
        itemBean.arrowRes = arrowRes
    }

    //endregion

  //region 设置颜色属性
    /**
     * 设置标题title颜色
     */
    fun setIconColor(colorRes: Int) {
        itemBean.iconColor = colorRes
    }
    /**
     * 设置标题title颜色
     */
    fun setTitleColor(colorRes: Int) {
        itemBean.titleColor = colorRes
    }

    /**
     * 设置标题title颜色
     */
    fun setDescColor(colorRes: Int) {
        itemBean.descColor = colorRes
    }

    /**
     * 设置标题title颜色
     */
    fun setArrowColor(colorRes: Int) {
        itemBean.arrowColor = colorRes
    }


    //endregion


    //region 设置点击事件
    /**
     * 单独点击图标
     */
    fun onClickIcon(listener: OnClickListener) {
        itemBean.iconListener = listener
    }
    /**
     * 单独点击title
     */
    fun onClickTitle(listener: OnClickListener) {
        itemBean.titleListener = listener
    }

    /**
     * 单独点击desc
     */
    fun onClickDesc(listener: OnClickListener) {
        itemBean.descListener = listener
    }

    /**
    * 单独点击箭头
    */
    fun onClickArrow(listener: OnClickListener) {
        itemBean.arrowListener = listener
    }


    //endregion



    /**
     *  ItemSettingsView 是个 viewGroup   其立面有很多的子view
     *    默认viewGroup会把点击事件分发给立面的子view，所以当我们点击子view时, viewGroup及整个item没有点击响应
     *    所以我们要禁掉viewGroup向下的事件分发
     *  这样当我们点击到子view 时   viewGroup整个item也会有点击响应
     * */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return  hasOnClickListeners()//=true viewGroup  自己消费点击事件  会拦截事件分发 不往子view 分发
    }




}


@Keep
class ItemSettingsBean{
    var iconRes: Any = R.drawable.ic_gift_card //标题图片
    var title: String = "" //标题文本
    var desc: String = "" //内容文本
    var titleColor: Int = R.color.colorPrimaryText //标题字体颜色
    var descColor: Int = R.color.colorSecondaryText//内容文本颜色
    var iconColor: Int = R.color.colorPrimaryLight //标题图片颜色
    var arrowColor: Int = R.color.colorPrimary//返回箭头颜色
    var arrowRes: Any = R.drawable.ic_right //返回箭头图片



    //item的子View的点击listener
    var iconListener: View.OnClickListener? = null
    var titleListener: View.OnClickListener? = null
    var descListener: View.OnClickListener? = null
    var arrowListener: View.OnClickListener? = null
}
