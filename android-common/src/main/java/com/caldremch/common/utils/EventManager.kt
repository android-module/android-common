package com.caldremch.common.utils

import org.greenrobot.eventbus.EventBus

/**
 * Created by Caldremch on 2017-05-06 12:54
 */
object EventManager {
    fun register(`object`: Any?) {
        if (!EventBus.getDefault().isRegistered(`object`)) {
            EventBus.getDefault().register(`object`)
        }
    }

    fun unregister(`object`: Any?) {
        if (EventBus.getDefault().isRegistered(`object`)) {
            EventBus.getDefault().unregister(`object`)
        }
    }

    fun post(`object`: Any?) {
        EventBus.getDefault().post(`object`)
    }
}