package com.caldremch.android.common.sample

import com.caldremch.android.common.sample.databinding.ActivitySampleDatabindingBinding
import com.caldremch.android.common.sample.mvvm.BaseActivity

/**
 * Created by Leon on 2022/8/17.
 */


class SampleMVVMActivity :
    BaseActivity<ActivitySampleDatabindingBinding, SampleDataBindingViewModel>() {


//    override val statusBarColor: Int?
//        get() = Color.GREEN
//
//
//    override val titleBackground: Int?
//        get() = Color.GREEN
//
//    override val isUseStatusBar: Boolean
//        get() = true
//
//    override val titleBarTitle: String
//        get() = "MVVM示例"


//    override fun getVariableId(): Int {
//        return BR.viewModel
//    }

    override val layoutId: Int
        get() = R.layout.activity_sample_databinding

//
//    override fun onViewModelCreated() {
//        viewModel.start.observe(this){
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.add(R.id.fragment_container, SampleMVVMFragment())
//            transaction.commitNowAllowingStateLoss()
//        }
//
//    }

}