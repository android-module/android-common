package com.caldremch.android.common.sample.biz

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.caldremch.android.common.sample.R
import com.caldremch.android.log.debugLog
import com.caldremch.common.base.AbsActivity
import com.caldremch.common.base.ILifeCycleLogger
import com.caldremch.common.base.ITitleInit
import com.hjq.bar.TitleBar

/**
 * Created by Leon on 2022/7/6
 */
open class BaseActivity : AbsActivity(), ITitleInit {
    override fun getLogger(): ILifeCycleLogger {
        return object : ILifeCycleLogger {
            override fun log(tag: String, msg: String) {
                debugLog(tag) { msg }
            }
        }
    }

    override fun initTitleBar(titleView: View?) {
        titleView?.findViewById<TitleBar>(R.id.title_bar)?.let { titleBar ->
            TitleViewComposer.initTitle(titleBar, this)
        }
    }

    override val titleView: View
        get() = LayoutInflater.from(this).inflate(R.layout.common_titlebar_layout, null)

    fun requireContext(): Context = this
    fun requireActivity(): Activity = this
    open fun onDestroyView() {

    }

    override fun onDestroy() {
        onDestroyView()
        super.onDestroy()
    }
}