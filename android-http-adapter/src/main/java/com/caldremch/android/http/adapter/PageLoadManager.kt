package com.caldremch.android.http.adapter

import androidx.lifecycle.LifecycleOwner
import com.caldremch.android.http.adapter.HttpViewModelPageLoad
import com.caldremch.common.widget.status.IStatusView
import com.caldremch.common.widget.status.ViewState

/**
 * Created by Leon on 2022/7/10
 */
class PageLoadManager {
    private var hasBeenSuccessful = false
    fun initPageStatusViewModel(
        lifecycleOwner: LifecycleOwner,
        viewModel: HttpViewModelPageLoad,
        statusHandler: IStatusView
    ) {
        viewModel.loadDataSuccess.observe(lifecycleOwner) {
            if (it) {
                hasBeenSuccessful = true
                statusHandler.setViewStatus(ViewState.VIEW_STATE_CONTENT)
            } else {
                if (hasBeenSuccessful.not()) {
                    statusHandler.setViewStatus(ViewState.VIEW_STATE_ERROR)
                }
            }
        }
    }
}