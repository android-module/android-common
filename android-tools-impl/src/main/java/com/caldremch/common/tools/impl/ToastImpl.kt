package com.caldremch.common.tools.impl

import com.blankj.utilcode.util.ToastUtils
import com.caldremch.common.tools.IToast

/**
 * Created by Leon on 2022/7/12
 */
class ToastImpl : IToast {
    override fun show(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun show(msg: Int?) {
        msg?.let {
            ToastUtils.showShort(it)
        }
    }
}