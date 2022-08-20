package com.caldremch.common.base

import android.view.View

/**
 * Created by Leon on 2022/8/20
 */
interface ITitleInit {
    val titleBackground: Int?
        get() = null
    val leftIcon: Int?
        get() = null
    val titleColor: Int?
        get() = null
    val rightTitle: String?
        get() = null
    val titleBarTitle: String
        get() = ""

    fun onLeftClick(titleView: View?) {
    }

    fun onRightClick(titleView: View?) {
    }
}