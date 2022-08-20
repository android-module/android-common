package com.caldremch.android.http.viewmodel

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import com.caldremch.android.http.viewmodel.adapter.IRequestContextCompositeAdapter
import com.caldremch.android.http.viewmodel.adapter.RequestContextCompositeAdapterImpl
import com.caldremch.android.http.viewmodel.ext.IHttpDialogEvent
import com.caldremch.android.http.viewmodel.viewmodels.MyViewModelLazy
import com.caldremch.android.log.debugLog
import com.caldremch.common.utils.TypeUtils
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get

/**
 * Created by Leon on 2022/8/19.
 *
 * 解决Fragment和Activity一致性的问题. 避免两种实现都要在不同的页面类型写一遍
 *
 */
object BaseComposer {

    fun <VM : HttpViewModel> injectVM(obj: Fragment): VM {
        //创建ViewModel
        val viewModel = createFragmentViewModel<VM>(obj, TypeUtils.getClz<VM>(obj, 0))
        //初始化ViewModel
        initViewModel(viewModel, obj.requireContext(), obj)
        return viewModel
    }

    fun <VM : HttpViewModel> initViewModel(
        viewModel: VM,
        context: Context,
        lifecycleOwner: LifecycleOwner
    ) {
        val httpDialogEvent: IHttpDialogEvent =
            get(IHttpDialogEvent::class.java) { parametersOf(context) }
           val requestContextComposite: IRequestContextCompositeAdapter  by lazy { RequestContextCompositeAdapterImpl() }
        //监听ViewModel
        viewModel.dialogEvent.observe(lifecycleOwner) {
            if (it) {
                httpDialogEvent.dialogShowTiming()
            } else {
                httpDialogEvent.dialogDismissTiming()
            }
        }
        viewModel.requestContext.observe(lifecycleOwner) {
            debugLog { "添加请求实例" }
            requestContextComposite.add(it)
        }
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                requestContextComposite.clear()
            }
        })
    }

    fun <VM : HttpViewModel> injectVM(obj: ComponentActivity): VM {
        //创建ViewModel
        val viewModel = createActivityViewModel<VM>(obj, TypeUtils.getClz<VM>(obj, 0))
        initViewModel(viewModel, obj, obj)
        return viewModel
    }


    private fun <VM : ViewModel> createFragmentViewModel(fragment: Fragment, clz: Class<VM>): VM {
        val owner by lazy(LazyThreadSafetyMode.NONE) { fragment }
        return MyViewModelLazy<VM>(clz, { owner.viewModelStore }, {
            (owner as? HasDefaultViewModelProviderFactory)?.defaultViewModelProviderFactory
                ?: fragment.defaultViewModelProviderFactory
        }, {
            (owner as? HasDefaultViewModelProviderFactory)?.defaultViewModelCreationExtras
                ?: CreationExtras.Empty
        }).value
    }

    private fun <VM : ViewModel> createActivityViewModel(
        activity: ComponentActivity, clz: Class<VM>
    ): VM {
        val factoryPromise = {
            activity.defaultViewModelProviderFactory
        }
        return MyViewModelLazy<VM>(clz,
            { activity.viewModelStore },
            factoryPromise,
            { activity.defaultViewModelCreationExtras }).value
    }
}