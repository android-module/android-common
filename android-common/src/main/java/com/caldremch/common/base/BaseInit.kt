package com.caldremch.common.base

import android.view.View

/**
 * @author Caldremch
 * @date 2019/1/24
 */
interface BaseInit {
    //是否使用沉浸式
    val isUseStatusBar: Boolean
        get() = true

    //是否使用 EventBus
    val isUseEvent: Boolean
        get() = false
    val layoutView: View?
        get() = null
    val isUseDefaultTitleBar: Boolean
        get() = true
    val layoutId: Int
        get() = 0
    val titleViewId: Int
        get() = 0
    val titleView: View?
        get() = null

    fun initView() {}
    fun initTitleBar(titleView: View?) {}
    fun initData() {}
    fun initEvent() {}
    fun onLeftClick(titleView: View?) {}
    fun onRightClick(titleView: View?) {}
}