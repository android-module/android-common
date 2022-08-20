package com.caldremch.common.utils

import com.caldremch.common.adapter.IEventAdapter
import org.koin.java.KoinJavaComponent

/**
 * Created by Caldremch on 2017-05-06 12:54
 */
object EventManager {

    private var evenAdapter: IEventAdapter = KoinJavaComponent.get(IEventAdapter::class.java)

    fun register(`object`: Any?) {
        evenAdapter.register(`object`)
    }

    fun unregister(`object`: Any?) {
        evenAdapter.unregister(`object`)
    }

    fun post(`object`: Any?) {
        evenAdapter.post(`object`)
    }
}