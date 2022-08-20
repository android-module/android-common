package com.caldremch.android.http.viewmodel.ext

import android.os.Bundle
import com.caldremch.android.http.viewmodel.HttpViewModel
import com.caldremch.android.log.debugLog
import com.caldremch.common.base.AbsActivity
import com.caldremch.common.utils.TypeUtils
import com.caldremch.http.core.framework.RequestContextComposite
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by Leon on 2022/7/24.
 */
abstract class BaseHttpViewModelActivity<VM : HttpViewModel> : AbsActivity() {

    protected lateinit var viewModel: VM
    private val httpDialogEvent: IHttpDialogEvent by inject { parametersOf(this) }
    private val requestContextComposite by lazy { RequestContextComposite() }

    private fun initViewModelWithin() {
        viewModel.dialogEvent.observe(this) {
            if (it) {
                httpDialogEvent.dialogShowTiming()
            } else {
                httpDialogEvent.dialogDismissTiming()
            }
        }
        viewModel.requestContext.observe(this) {
            debugLog { "添加请求实例" }
            requestContextComposite.add(it)
        }
    }


    open fun getVMClass(): Class<VM>? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel = createViewModel(getVMClass() ?: TypeUtils.getClz<VM>(this, 0))
        initViewModelWithin()
        onViewModelCreated()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        requestContextComposite.clear()
    }

    private fun createViewModel(clz: Class<VM>): VM {
        val factoryPromise = {
            defaultViewModelProviderFactory
        }
        return MyViewModelLazy(
            clz,
            { viewModelStore },
            factoryPromise,
            { this.defaultViewModelCreationExtras }
        ).value
    }


}