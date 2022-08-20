package com.caldremch.android.http.viewmodel.adapter

/**
 * Created by Leon on 2022/8/20
 */
class RequestContextCompositeAdapterImpl : IRequestContextCompositeAdapter {
    private val ctxSet: HashSet<IRequestContextAdapter> by lazy { HashSet() }
    override fun add(ctx: IRequestContextAdapter) {
        ctxSet.add(ctx)
    }

    override fun clear() {
        ctxSet.forEach {
            it.cancel()
        }
    }
}