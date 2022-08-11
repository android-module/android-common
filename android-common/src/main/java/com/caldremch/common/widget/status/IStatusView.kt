package com.caldremch.common.widget.status

import android.view.View

/**
 * @author Caldremch
 * @describe
 */
interface IStatusView {

    /**
     * 初始化ErrorView的实现
     *
     * @return
     */
    fun initErrorViewImpl(): View? {
        return null
    }

    /**
     * 初始化LoadingView的实现
     *
     * @return
     */
    fun initLoadingViewImpl(): View? {
        return null
    }

    /**
     * 开始加载, 用于重写自己的statusView 的startLoading动作
     */
    fun startLoadingImpl() {}

    /**
     * 停止加载
     */
    fun stopLoadingImpl() {}

    /**
     * 获取错误View
     *
     * @return
     */
    val errorView: View?
        get() = null

    /**
     * 错误页面重试
     */
    fun reTry() {}

    /**
     * 是否使用加载状态也
     */
    val isUseLoading: Boolean
        get() = false

    /**
     * 一般是页面使用功能
     */
    fun setViewStatus(@ViewState status: Int)

    val titleBarTitle: String
        get() = ""

}