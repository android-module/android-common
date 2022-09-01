package com.caldremch.android.http.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class HttpViewModelPageLoad() : HttpViewModel() {

    protected val _loadDataSuccess = MutableLiveData<Boolean>()
    val loadDataSuccess: LiveData<Boolean>
        get() = _loadDataSuccess

}