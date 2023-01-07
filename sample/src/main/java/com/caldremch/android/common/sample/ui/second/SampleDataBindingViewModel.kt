package com.caldremch.android.common.sample.ui.second

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caldremch.android.common.databinding.adapter.IBindingClick

/**
 * Created by Leon on 2022/8/19.
 */
class SampleDataBindingViewModel : ViewModel() {
    val start = MutableLiveData<Any?>()
    val click = object : IBindingClick {
        override fun click() {
            start.postValue(null)
        }
    }
}
