package com.caldremch.android.http.viewmodel.ext

import android.os.Bundle
import com.caldremch.android.http.viewmodel.BaseComposer
import com.caldremch.android.http.viewmodel.HttpViewModel
import com.caldremch.android.http.viewmodel.IBaseViewModel
import com.caldremch.common.base.AbsFragment


/**
 * Created by Leon on 2022/7/10
 */
abstract class BaseHttpViewModelFragment<VM : HttpViewModel> : AbsFragment(), IBaseViewModel<VM> {
    protected lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        BaseComposer.injectVM<VM>(this)
        super.onCreate(savedInstanceState)
        onViewModelCreated()
    }
}




