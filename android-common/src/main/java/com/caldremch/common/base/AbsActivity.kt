package com.caldremch.common.base

import android.os.Bundle
import android.view.View
import com.caldremch.common.utils.BarUtils
import com.caldremch.common.utils.EventManager
import com.caldremch.common.widget.DecorViewProxy
import com.caldremch.common.widget.DecorViewProxyUtils
import com.caldremch.common.widget.status.IStatusView
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
open class AbsActivity : LifeCycleLogActivity(), BaseInit, IStatusView, IViewModel {

    protected lateinit var mContentView: View
    private lateinit var contentViewDelegate: DecorViewProxy


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isUseEvent) {
            EventManager.register(this)
        }

        if (layoutId != 0) {
            val decorViewProxyBuilder = DecorViewProxy.Builder(this)
            contentViewDelegate = DecorViewProxyUtils.initWith(decorViewProxyBuilder, this, this)
            mContentView = contentViewDelegate.proxySetContentView()
            setContentView(mContentView)
            if (isUseStatusBar) {
                BarUtils.initStatusBar(this, this, contentViewDelegate.titleView)
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

    override fun setViewStatus(status: Int) {
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

