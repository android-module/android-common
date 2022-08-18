package com.caldremch.android.common.databinding

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.caldremch.android.common.databinding.ext.MyViewModelLazy
import com.caldremch.android.common.databinding.utils.TypeUtils
import com.caldremch.common.base.BaseCommonActivity


/**
 * Created by Leon on 2022/8/17.
 */


abstract class BaseDataBindingActivity<VB : ViewDataBinding> : BaseCommonActivity(){
    protected lateinit var binding: VB

    override val isUserDataBinding: Boolean
        get() = true

    open fun onBindViewModel(){

    }

    override fun handleDataBinding(li: Int): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), li, null, false)
        binding.lifecycleOwner = this
        onBindViewModel()
        return binding.root
    }
}

abstract class BaseMVVMActivity<VB : ViewDataBinding, VM : ViewModel> :
    BaseDataBindingActivity<VB>() {

    protected lateinit var viewModel: VM
    open fun getVMClass(): Class<VM>? {
        return null
    }

    override fun onBindViewModel() {
        viewModel = createViewModel(getVMClass() ?: TypeUtils.getClz(this, 1))
        binding.setVariable(getVariableId(), viewModel)
    }

    abstract fun getVariableId(): Int

    private fun createViewModel(clz: Class<VM>): VM {
        val factoryPromise = {
            defaultViewModelProviderFactory
        }
        return MyViewModelLazy(
            clz,
            { viewModelStore },
            factoryPromise,
            { this.defaultViewModelCreationExtras }
        ).value
    }


}