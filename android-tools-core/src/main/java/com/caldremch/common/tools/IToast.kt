package com.caldremch.common.tools

import androidx.annotation.StringRes

/**
 * Created by Leon on 2022/7/12
 */
interface IToast {
    fun show(msg:String?)
    fun show(@StringRes msg: Int?)
}