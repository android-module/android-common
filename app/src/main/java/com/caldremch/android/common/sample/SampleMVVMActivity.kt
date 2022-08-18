package com.caldremch.android.common.sample

import androidx.lifecycle.ViewModel
import com.caldremch.android.common.databinding.BaseMVVMActivity
import com.caldremch.android.common.sample.databinding.ActivitySampleDatabindingBinding

/**
 * Created by Leon on 2022/8/17.
 */

class SampleDataBindingViewModel : ViewModel()

class SampleMVVMActivity :
    BaseMVVMActivity<ActivitySampleDatabindingBinding, SampleDataBindingViewModel>() {
    override fun getVariableId(): Int {
       return BR.viewModel
    }

    override val layoutId: Int
        get() = R.layout.activity_sample_databinding



//    override fun getVariableId(): Int {
//        return BR.viewModel
//    }


}