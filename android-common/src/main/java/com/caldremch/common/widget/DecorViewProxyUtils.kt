package com.caldremch.common.widget

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.caldremch.common.widget.status.StatusView

/**
 * Created by Leon on 2022/7/31.
 */
object DecorViewProxyUtils {

    fun createStatusView(
        context: Context,
        isActivity: Boolean,
        contentViewLayoutId: Int,
        fragment: Fragment?,
        childRootView: View,
    ): StatusView {
        return if (isActivity) StatusView(context, contentViewLayoutId) else StatusView(
            fragment!!,
            childRootView
        )
    }

    fun createMatchAllLp(): ConstraintLayout.LayoutParams {
        val match = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        match.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        match.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        match.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        match.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        return match
    }

    fun wrapLoading(context: Context, statusView: StatusView): View {
        val parentLayout = ConstraintLayout(context)
        val contentViewLp = createMatchAllLp()
        parentLayout.addView(statusView, contentViewLp)
        return parentLayout
    }

}