package com.caldremch.common.adapter

/**
 * Created by Leon on 2022/8/20
 */
interface IEventAdapter {
    fun register(`object`: Any?)
    fun unregister(`object`: Any?)
    fun isRegistered(`object`: Any?)
    fun post(`object`: Any?)
    fun postStick(`object`: Any?)
}