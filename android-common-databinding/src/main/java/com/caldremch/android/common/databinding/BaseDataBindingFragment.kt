package com.caldremch.android.common.databinding

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.caldremch.android.common.databinding.ext.MyViewModelLazy
import com.caldremch.android.http.viewmodel.IBaseViewModel
import com.caldremch.common.base.AbsActivity
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
        viewModel = createViewModel(getVMClass() ?: TypeUtils.getClz(this, 1))
        onViewModelCreated()
        binding.setVariable(getVariableId(), viewModel)
        return binding.root
    }

    abstract fun getVariableId(): Int

    private fun createViewModel(clz: Class<VM>): VM {
        val factoryPromise = {
            defaultViewModelProviderFactory
        }
        return MyViewModelLazy(clz,
            { viewModelStore },
            factoryPromise,
            { this.defaultViewModelCreationExtras }).value
    }


}