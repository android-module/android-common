package com.caldremch.common.tools

import com.blankj.utilcode.util.ToastUtils

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