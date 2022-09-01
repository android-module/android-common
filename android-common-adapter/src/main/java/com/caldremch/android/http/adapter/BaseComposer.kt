package com.caldremch.android.http.adapter

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
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

    fun <VM : HttpViewModel> injectVM(obj: Fragment, vmIndex:Int = 0): VM {
        //创建ViewModel
        val viewModel = createFragmentViewModel(obj, TypeUtils.getClz<VM>(obj, vmIndex))
        //初始化ViewModel
        initViewModel(viewModel, obj.requireContext(), obj)
        return viewModel
    }

    fun <VM : HttpViewModel> initViewModel(
        viewModel: VM, context: Context, lifecycleOwner: LifecycleOwner
    ) {
        val httpDialogEvent: IHttpDialogEvent =
            get(IHttpDialogEvent::class.java) { parametersOf(context) }
        val requestContextComposite: IRequestContextCompositeAdapter by lazy { RequestContextCompositeAdapterImpl() }
        //监听ViewModel
        viewModel.dialogEvent.observe(lifecycleOwner) {
            if (it) {
                httpDialogEvent.dialogShowTiming()
            } else {
                httpDialogEvent.dialogDismissTiming()
            }
        }
        viewModel.requestContext.observe(lifecycleOwner) {
            requestContextComposite.add(it)
        }
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                requestContextComposite.clear()
            }
        })
    }

    fun <VM : HttpViewModel> injectVM(obj: ComponentActivity, vmIndex:Int = 0): VM {
        //创建ViewModel
        val viewModel = createActivityViewModel(obj, TypeUtils.getClz<VM>(obj, vmIndex))
        initViewModel(viewModel, obj, obj)
        return viewModel
    }


     fun <VM : ViewModel> createFragmentViewModel(fragment: Fragment, clz: Class<VM>): VM {
        val owner by lazy(LazyThreadSafetyMode.NONE) { fragment }
        return MyViewModelLazy(clz, { owner.viewModelStore }, {
            (owner as? HasDefaultViewModelProviderFactory)?.defaultViewModelProviderFactory
                ?: fragment.defaultViewModelProviderFactory
        }, {
            (owner as? HasDefaultViewModelProviderFactory)?.defaultViewModelCreationExtras
                ?: CreationExtras.Empty
        }).value
    }

     fun <VM : ViewModel> createActivityViewModel(
        activity: ComponentActivity, clz: Class<VM>
    ): VM {
        val factoryPromise = {
            activity.defaultViewModelProviderFactory
        }
        return MyViewModelLazy(clz,
            { activity.viewModelStore },
            factoryPromise,
            { activity.defaultViewModelCreationExtras }).value
    }
}