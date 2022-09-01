package com.caldremch.android.http.adapter

/**
 * Created by Leon on 2022/8/20.
 */
interface IBaseViewModel<VM> {
    fun getVMClass(): Class<VM>? = null
    fun onViewModelCreated() {}
}