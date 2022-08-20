package com.caldremch.android.http.viewmodel.ext

import android.os.Bundle
import com.caldremch.android.http.viewmodel.BaseComposer
import com.caldremch.android.http.viewmodel.HttpViewModel
import com.caldremch.android.http.viewmodel.IBaseViewModel
import com.caldremch.common.base.AbsActivity
/**
 * Created by Leon on 2022/7/24.
 */
abstract class BaseHttpViewModelActivity<VM : HttpViewModel> : AbsActivity(), IBaseViewModel {
    protected lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = BaseComposer.injectVM(this)
        onViewModelCreated()
    }

}