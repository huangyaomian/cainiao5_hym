import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.cainiao5.hym.common.base.BaseFragment
import com.cainiao5.hym.mine.R
import com.cainiao5.hym.mine.databinding.FragmentMineBinding

/**
 * author: huangyaomian
 * created on: 2021/6/17 8:03 上午
 * description:mine fragment
 */
class MineFragment : BaseFragment(){
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentMineBinding.bind(view)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine
    }
}