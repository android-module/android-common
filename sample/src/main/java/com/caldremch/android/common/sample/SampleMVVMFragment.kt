package com.caldremch.android.common.sample

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.caldremch.android.common.sample.databinding.FragmentSampleMvvmBinding
import com.caldremch.android.common.sample.mvvm.BaseFragment

/**
 * Created by Leon on 2022/9/1
 */

class SampleMVVMViewModel : ViewModel()

class SampleMVVMFragment : BaseFragment<FragmentSampleMvvmBinding, SampleMVVMViewModel>() {

    override val statusBarColor: Int?
        get() = Color.GREEN


    override val titleBackground: Int?
        get() = Color.GREEN

    override val isUseStatusBar: Boolean
        get() = true


    override val layoutId: Int
        get() = R.layout.fragment_sample_mvvm
}