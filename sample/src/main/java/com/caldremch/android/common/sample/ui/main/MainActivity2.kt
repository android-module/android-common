package com.caldremch.android.common.sample

import android.graphics.Color
import com.caldremch.android.common.databinding.BaseDataBindingActivity
import com.caldremch.android.common.sample.databinding.ActivityMain2Binding
import com.caldremch.android.common.sample.ui.main.Main2ViewModel
import com.caldremch.android.log.debugLog


/**
 *
 *
 */
class MainActivity2 : BaseDataBindingActivity<ActivityMain2Binding, Main2ViewModel>() {
    override fun getVariableId(): Int {
        return BR.viewModel
    }

    override val statusBarColor: Int
        get() = Color.WHITE


    override val layoutId: Int
        get() = R.layout.activity_main2

    override fun initView() {
        super.initView()

        /**
         * 页面重建时(横竖屏切换), 会导触发以下打印
         */
        for (x in 0 until 1) {
            viewModel.singleLiveEvent.observe(this) {
                debugLog { "打印$x" }
//            startActivity(Intent(this, SampleMVVMActivity::class.java))
            }
        }

//        viewModel.start.observe(this) {
//            debugLog { "打印2" }
////            startActivity(Intent(this, SampleMVVMActivity::class.java))
//        }
    }
}
