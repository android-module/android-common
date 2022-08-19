package com.caldremch.android.common.sample

import com.caldremch.android.common.databinding.BaseDataBindingActivity
import com.caldremch.android.common.sample.databinding.ActivitySampleDatabindingBinding

/**
 * Created by Leon on 2022/8/17.
 */


class SampleMVVMActivity :
    BaseDataBindingActivity<ActivitySampleDatabindingBinding, SampleDataBindingViewModel>() {
    override fun getVariableId(): Int {
        return BR.viewModel
    }

    override val layoutId: Int
        get() = R.layout.activity_sample_databinding


}