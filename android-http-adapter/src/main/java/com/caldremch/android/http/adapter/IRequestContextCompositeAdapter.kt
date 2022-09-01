package com.caldremch.android.http.adapter

/**
 * Created by Leon on 2022/8/20
 */
interface IRequestContextCompositeAdapter {
    fun add(ctx: IRequestContextAdapter)
    fun clear()
}