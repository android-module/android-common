package com.caldremch.android.common.databinding.http

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.caldremch.android.http.adapter.BaseComposer
import com.caldremch.android.http.adapter.HttpViewModel
import com.caldremch.android.http.adapter.IBaseViewModel
import com.caldremch.common.base.AbsActivity

/**
 * Created by Leon on 2022/7/24.
 */
abstract class BaseHttpViewModelActivity<VB : ViewDataBinding, VM : HttpViewModel> : AbsActivity(),
IBaseViewModel<VM> {
    protected lateinit var binding: VB
    override val isUseDataBinding: Boolean
        get() = true

    protected lateinit var viewModel: VM
    open fun onSetupViewModel() {}

    override fun handleDataBinding(layoutIdRes: Int): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), layoutIdRes, null, false)
        binding.lifecycleOwner = this
        viewModel = BaseComposer.injectVM(this, vmIndex = 1)
        onSetupViewModel()
        onViewModelCreated()
        binding.setVariable(getVariableId(), viewModel)
        return binding.root
    }

    abstract fun getVariableId(): Int
}