package com.caldremch.common.widget.status

import androidx.annotation.IntDef
import com.caldremch.common.widget.status.ViewState.Companion.VIEW_STATE_CONTENT
import com.caldremch.common.widget.status.ViewState.Companion.VIEW_STATE_ERROR
import com.caldremch.common.widget.status.ViewState.Companion.VIEW_STATE_LOADING

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    VIEW_STATE_CONTENT, VIEW_STATE_ERROR, VIEW_STATE_LOADING
)
annotation class ViewState{
    companion object {
        const val VIEW_STATE_CONTENT = 0
        const val VIEW_STATE_ERROR = 1
        const val VIEW_STATE_LOADING = 2
    }
}