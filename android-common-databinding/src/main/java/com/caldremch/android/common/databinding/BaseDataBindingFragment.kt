package com.caldremch.android.common.databinding

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.caldremch.android.http.adapter.BaseComposer
import com.caldremch.android.http.adapter.IBaseViewModel
import com.caldremch.android.http.adapter.MyViewModelLazy
import com.caldremch.common.base.AbsFragment
import com.caldremch.common.utils.TypeUtils


/**
 * Created by Leon on 2022/8/17.
 */


abstract class BaseDataBindingFragment<VB : ViewDataBinding, VM : ViewModel> : AbsFragment(),
    IBaseViewModel<VM> {
    protected lateinit var binding: VB

    override val isUseDataBinding: Boolean
        get() = true

    protected lateinit var viewModel: VM
    override fun handleDataBinding(layoutIdRes: Int): View {
        binding = DataBindingUtil.inflate(layoutInflater, layoutIdRes, null, false)
        binding.lifecycleOwner = this
        viewModel = BaseComposer.createFragmentViewModel(this,getVMClass() ?: TypeUtils.getClz(this, 1))
        onViewModelCreated()
        binding.setVariable(getVariableId(), viewModel)
        return binding.root
    }

    abstract fun getVariableId(): Int

}