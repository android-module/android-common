package com.caldremch.android.common.sample.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caldremch.android.common.databinding.adapter.IBindingClick

/**
 * @author Leon
 * @date 2023/1/7
 * @desc
 */
class Main2ViewModel : ViewModel() {
    val start = MutableLiveData<Any?>()
    val singleLiveEvent = SingleLiveEvent<Any?>()
    val click = object : IBindingClick {
        override fun click() {
            singleLiveEvent.value = null
        }
    }
}
