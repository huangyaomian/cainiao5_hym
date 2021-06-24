package com.cainiao5.hym.mine

import androidx.databinding.ObservableField
import com.blankj.utilcode.util.ToastUtils
import com.cainiao5.hym.common.base.BaseActivity
import com.cainiao5.hym.mine.databinding.ActivityMineBinding
import com.cainiao5.hym.mine.widget.ItemSettingsBean

class MineActivity : BaseActivity<ActivityMineBinding>(){
    override fun getLayoutRes(): Int {
          return R.layout.activity_mine
    }


    override fun initView() {
        super.initView()

        mBinding.apply {
      val ib  = ItemSettingsBean()
        val observalBean = ObservableField(ib)
             bean = observalBean
           ib.title="你的学习卡2"
             ib.titleColor = R.color.colorAccent

            ib.arrowRes=R.drawable.ic_like_it
            ib.arrowColor=R.color.colorAccent

            ib.desc ="你个王八犊子"
            ib.descColor=R.color.colorWindowBackGround


            ib.iconRes = "https://www.easyicon.net/api/resizeApi.php?id=1283371&size=96"
            ib.iconColor=R.color.colorIcons


            isvCard.setOnClickListener {
              ToastUtils.showShort("点击整个Item")
          }

            isvCard.onClickArrow {
                ToastUtils.showShort("点击了Arrow箭头")
            }
  }


    }



}