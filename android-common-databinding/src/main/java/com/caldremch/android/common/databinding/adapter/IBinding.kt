package com.caldremch.android.common.databinding.adapter

/**
 * Created by Leon on 2022/8/18.
 */
interface IBindingClick {
    fun click()
}

interface IBindingCallback<T>{
    fun onCallback(data:T)
}