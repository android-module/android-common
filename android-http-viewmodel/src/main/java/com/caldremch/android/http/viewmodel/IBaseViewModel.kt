package com.caldremch.android.http.viewmodel

/**
 * Created by Leon on 2022/8/20.
 */
interface IBaseViewModel {
    fun <VM> getVMClass(): Class<VM>? = null
    fun onViewModelCreated() {}
}