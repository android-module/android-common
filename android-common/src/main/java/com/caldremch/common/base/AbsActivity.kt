package com.caldremch.common.base

import android.os.Bundle
import android.view.View
import com.caldremch.common.R
import com.caldremch.common.utils.EventManager
import com.caldremch.common.widget.status.IStatusView
import com.gyf.immersionbar.ktx.immersionBar
import com.caldremch.common.widget.DecorViewProxy
import com.caldremch.common.widget.status.StatusView
import com.caldremch.common.widget.status.ViewState

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-28 22:51
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
open class AbsActivity : LifeCycleLogActivity(), BaseInit, IStatusView {

    protected lateinit var mContentView: View
    private lateinit var contentViewDelegate: DecorViewProxy
    open val navigationBarColorRes: Int = android.R.color.white
    open val statusBarColorRes: Int? = null
    open val keyboadFixed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isUseEvent) {
            EventManager.register(this)
        }

        if (layoutId != 0) {
            val decorViewProxyBuilder = DecorViewProxy.Builder(this)
            decorViewProxyBuilder.isUseLoading(isUseLoading)
            decorViewProxyBuilder.setContentViewLayoutId(layoutId)
            if (isUseDefaultTitleBar) {
                decorViewProxyBuilder.setTitleViewLayoutId(titleViewId)
                decorViewProxyBuilder.setTitleView(titleView)
            }

            contentViewDelegate = decorViewProxyBuilder.build()
            mContentView = contentViewDelegate.proxySetContentView()
            setContentView(mContentView)

            if (isUseStatusBar) {
                immersionBar {
                    keyboardEnable(keyboadFixed)
                    statusBarColorRes?.let { this.statusBarColor(it) }
                    if (statusBarColorRes == android.R.color.white) {
                        this.statusBarDarkFont(true, 0.2f)
                    } else {
                        this.statusBarDarkFont(false)
                    }
                    navigationBarColor(navigationBarColorRes)
                    titleBar(contentViewDelegate.titleView)
                }
            }

        }
        initTitleBar(contentViewDelegate.titleView)
        initView()
        initData()
        initEvent()
    }

    override fun onLeftClick(titleView: View?) {
        finish()
    }

    override fun setViewStatus(status: Int ) {
        contentViewDelegate.statusView?.setViewStatus(status)
    }

    protected fun setErrorStatus() {
        setViewStatus(ViewState.VIEW_STATE_ERROR)
    }

    protected fun setContentStatus() {
        setViewStatus(ViewState.VIEW_STATE_CONTENT)
    }

    protected fun setLoadingStatus() {
        setViewStatus(ViewState.VIEW_STATE_LOADING)
    }


}

