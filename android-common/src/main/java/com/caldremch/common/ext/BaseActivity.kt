package com.caldremch.common.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import com.caldremch.android.log.debugLog
import com.caldremch.common.R
import com.caldremch.common.base.BaseCommonActivity
import com.caldremch.common.base.ILifeCycleLogger
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar

/**
 * Created by Leon on 2022/7/6
 */
@Deprecated(message = "do not use, customize by yourself", level = DeprecationLevel.ERROR)
open class BaseActivity : BaseCommonActivity() {

    open val leftIcon: Int? = null

    override fun getLogger(): ILifeCycleLogger {
        return object : ILifeCycleLogger {
            override fun log(tag: String, msg: String) {
                debugLog(tag) { msg }
            }
        }
    }

    override fun initTitleBar(titleView: View?) {
        val titleBar = titleView?.findViewById<TitleBar>(R.id.title_bar)
        titleBar?.title = titleBarTitle
        titleBar?.setBackgroundColor(titleBackground)
        leftIcon?.let { titleBar?.setLeftIcon(it) }
        titleColor?.let { titleBar?.setTitleColor(it) }
        rightTitle?.let { titleBar?.setRightTitle(it) }
        titleBar?.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                this@BaseActivity.onLeftClick(titleBar)
            }

            override fun onRightClick(titleBar: TitleBar) {
                this@BaseActivity.onRightClick(titleBar)
            }
        })
    }

    override val titleView: View
        get() = LayoutInflater.from(this).inflate(R.layout.common_titlebar_layout, null)

    open val rightTitle: String? = null
    open val titleColor: Int? = null
    open val titleBackground: Int
        get() = Color.WHITE

    fun requireContext(): Context = this
    fun requireActivity(): Activity = this
    open fun onDestroyView() {

    }

    override fun onDestroy() {
        onDestroyView()
        super.onDestroy()
    }
}