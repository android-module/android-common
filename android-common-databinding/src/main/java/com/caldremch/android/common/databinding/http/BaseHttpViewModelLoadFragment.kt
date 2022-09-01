package com.caldremch.android.common.databinding.http

import androidx.databinding.ViewDataBinding
import com.caldremch.android.http.adapter.HttpViewModelPageLoad
import com.caldremch.android.http.adapter.PageLoadManager

/**
 * Created by Leon on 2022/7/24.
 */
abstract class BaseHttpViewModelLoadFragment<VB : ViewDataBinding,VM : HttpViewModelPageLoad> :
    BaseHttpViewModelFragment<VB, VM>() {
   private val pageLoadManager by lazy { PageLoadManager() }

    override fun onSetupViewModel() {
        pageLoadManager.initPageStatusViewModel(this, viewModel, this)
    }
}