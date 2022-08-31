package com.caldremch.android.common.sample.mvvm

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.caldremch.android.common.databinding.BR
import com.caldremch.android.common.databinding.BaseDataBindingFragment
import com.caldremch.android.common.sample.R
import com.caldremch.android.common.sample.biz.TitleViewComposer
import com.caldremch.common.base.ITitleInit
import com.hjq.bar.TitleBar

/**
 * Created by Leon on 2022/9/1
 */
open class BaseFragment<VB : ViewDataBinding, VM : ViewModel> : BaseDataBindingFragment<VB, VM>() , ITitleInit{

    override fun onLeftClick(titleView: View?) {
        requireActivity().finish()
    }


    override fun initTitleBar(titleView: View?) {
        titleView?.findViewById<TitleBar>(R.id.title_bar)?.let { titleBar ->
            TitleViewComposer.initTitle(titleBar, this)
        }
    }

    override val titleView: View
        get() = LayoutInflater.from(requireContext()).inflate(R.layout.common_titlebar_layout, null)

    override fun getVariableId(): Int {
        return BR.viewModel
    }
}

