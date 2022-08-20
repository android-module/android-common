package com.caldremch.android.common.sample.biz

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import com.caldremch.android.common.sample.R
import com.caldremch.android.log.debugLog
import com.caldremch.common.base.AbsFragment
import com.caldremch.common.base.ILifeCycleLogger
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar


/**
 * @author Caldremch
 */
abstract class BaseFragment : AbsFragment() {


    override fun getLogger(): ILifeCycleLogger {
        return object : ILifeCycleLogger {
            override fun log(tag: String, msg: String) {
                debugLog(tag) { msg }
            }
        }
    }

    open val titleBackground: Int
        get() = Color.WHITE
    open val leftIcon: Int? = null
    open val titleColor: Int? = null

    override val titleView: View?
        get() = LayoutInflater.from(requireContext()).inflate(R.layout.common_titlebar_layout, null)

    override fun initTitleBar(titleView: View?) {
        val titleBar = titleView?.findViewById<TitleBar>(R.id.title_bar)
        titleBar?.setBackgroundColor(titleBackground)
        rightTitle?.let { titleBar?.rightTitle = it }
        leftIcon?.let { titleBar?.setLeftIcon(it) }
        titleColor?.let { titleBar?.setTitleColor(it) }
        titleBarTitle.let { titleBar?.setTitle(it) }
        titleBar?.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                this@BaseFragment.onLeftClick(titleBar)
            }

            override fun onRightClick(titleBar: TitleBar) {
                this@BaseFragment.onRightClick(titleBar)

            }
        })
    }

    open fun isUserNavigation(): Boolean = false

    override fun onLeftClick(titleView: View?) {
        requireActivity().finish()
    }

}