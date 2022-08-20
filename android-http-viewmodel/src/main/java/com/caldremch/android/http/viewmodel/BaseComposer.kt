package com.caldremch.android.http.viewmodel

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.caldremch.android.http.viewmodel.ext.IHttpDialogEvent
import com.caldremch.android.http.viewmodel.ext.MyViewModelLazy
import com.caldremch.common.utils.TypeUtils
import com.caldremch.http.core.framework.RequestContextComposite
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get

/**
 * Created by Leon on 2022/8/19.
 *
 * 解决Fragment和Activity一致性的问题. 避免两种实现都要在不同的页面类型写一遍
 *
 */
object BaseComposer {

    private fun <VM : ViewModel> createViewModel(activity: ComponentActivity, clz: Class<VM>): VM {
        val factoryPromise = {
            activity.defaultViewModelProviderFactory
        }
        return MyViewModelLazy<VM>(
            clz,
            { activity.viewModelStore },
            factoryPromise,
            { activity.defaultViewModelCreationExtras }
        ).value
    }

    fun <VM : ViewModel> injectVM(obj: ComponentActivity): VM {
        val httpDialogEvent: IHttpDialogEvent =
            get(IHttpDialogEvent::class.java) { parametersOf(obj) }
        val requestContextComposite by lazy { RequestContextComposite() }
        val viewModel = createViewModel<VM>(obj, TypeUtils.getClz<VM>(obj, 0))
        return viewModel
    }
}