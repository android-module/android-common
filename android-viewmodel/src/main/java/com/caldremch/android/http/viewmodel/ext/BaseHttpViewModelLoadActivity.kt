package com.caldremch.android.http.viewmodel.ext

import android.os.Bundle
import com.caldremch.android.http.adapter.HttpViewModelPageLoad
/**
 * Created by Leon on 2022/7/24.
 */
abstract class BaseHttpViewModelLoadActivity<VM : HttpViewModelPageLoad> :
    BaseHttpViewModelActivity<VM>() {
    private val pageLoadManager by lazy { PageLoadManager() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageLoadManager.initPageStatusViewModel(this, viewModel, this)
    }
}