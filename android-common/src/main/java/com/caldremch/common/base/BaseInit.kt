package com.caldremch.common.base

import android.view.View
import androidx.annotation.ColorInt
import com.caldremch.common.adapter.IEventAdapter

/**
 * @author Caldremch
 * @date 2019/1/24
 */
interface BaseInit {
    val isUseStatusBar: Boolean
        get() = true
    val isUseEvent: Boolean
        get() = false
    val layoutView: View?
        get() = null
    val isUseTitleBar: Boolean
        get() = true
    val layoutId: Int
        get() = 0
    val titleViewId: Int
        get() = 0
    val titleView: View?
        get() = null
    val navigationBarColor: Int?
        get() = null

    @get:ColorInt
    val statusBarColor: Int?
        get() = null
    val keyboardFixed: Boolean
        get() = false
    val isUseDataBinding: Boolean
        get() = false

    fun createEventAdapter(): IEventAdapter? = null
    fun handleDataBinding(layoutIdRes: Int): View? = null
    fun initView() {}
    fun initTitleBar(titleView: View?) {}
    fun initData() {}
    fun initEvent() {}
}