package com.caldremch.android.common.databinding.ext

import android.view.View
import androidx.databinding.BindingAdapter
import com.caldremch.android.common.databinding.adapter.IBindingClick

/**
 * Created by Leon on 2022/8/17.
 */


object ViewBindAdapter {
    @JvmStatic
    @BindingAdapter(value = ["bindClick"], requireAll = false)
    fun bindClick(view: View, bindingClick: IBindingClick) {
        view.setOnClickListener {
            bindingClick.click()
        }
    }
}