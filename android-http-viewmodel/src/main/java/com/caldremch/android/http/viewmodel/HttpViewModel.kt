package com.caldremch.android.http.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caldremch.android.http.viewmodel.adapter.IIDialogHandleAdapter
import com.caldremch.android.http.viewmodel.adapter.IRequestContextAdapter
import com.caldremch.android.http.viewmodel.adapter.IRequestHandleAdapter

open class HttpViewModel() : ViewModel(), IIDialogHandleAdapter, IRequestHandleAdapter {
    private val _dialogEvent = MutableLiveData<Boolean>()
    val dialogEvent: LiveData<Boolean>
        get() = _dialogEvent

    private val _requestContext = MutableLiveData<IRequestContextAdapter>()
    val requestContext: LiveData<IRequestContextAdapter>
        get() = _requestContext


    override fun dialogShowTiming(dialogTips: String) {
        _dialogEvent.postValue(true)
    }

    override fun dialogDismissTiming() {
        _dialogEvent.postValue(false)
    }

    override fun onRequestHandle(ctx: IRequestContextAdapter) {
        _requestContext.postValue(ctx)
    }

}