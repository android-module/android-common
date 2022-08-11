package com.caldremch.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes

import com.caldremch.common.widget.status.IStatusView
import com.gyf.immersionbar.ktx.immersionBar
import com.caldremch.common.R
import com.caldremch.common.utils.EventManager
import com.caldremch.common.widget.DecorViewProxy
import com.caldremch.common.widget.status.ViewState
import com.caldremch.common.widget.status.ViewState.Companion.VIEW_STATE_CONTENT
import com.caldremch.common.widget.status.ViewState.Companion.VIEW_STATE_ERROR


/**
 * @author Caldremch
 */
abstract class BaseCommonFragment : LifeCycleLogFragment(), BaseInit, IStatusView {

    protected var mIsVisible = false
    private var mIsPrepare = false
    private var mIsFirst = true
    protected lateinit var mContentView: View
    protected lateinit var contentViewDelegate: DecorViewProxy
    open val keyboadFixed: Boolean = false

    protected val mRootView by lazy { mContentView }

    fun rootXml(): View {
        return contentViewDelegate.getRoomXml()
    }

//    @Deprecated("Deprecated in Java")
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (userVisibleHint) {
//            //JLog.d(this.getClass().getSimpleName()+"-->可见:"+getUserVisibleHint());
//            mIsVisible = true
//            onVisible()
//            loadData()
//        } else {
//            mIsVisible = false
//            onInvisible()
//        }
//    }

    protected fun onInvisible() {

    }

    protected fun onVisible() {

    }

    override fun setViewStatus(status: Int) {
       contentViewDelegate.statusView?.setViewStatus(status)
    }


    //主要用于ViewPage中, 否则无效
    protected fun lazyLoad() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        inflaterView(inflater, container)
        return mContentView
    }

    private fun inflaterView(inflater: LayoutInflater, container: ViewGroup?) {
        val contentViewDelegateBuilder = DecorViewProxy.Builder(this, inflater, container)

        contentViewDelegateBuilder.setContentViewLayoutId(layoutId).setContentView(layoutView)
        contentViewDelegateBuilder.isUseLoading(isUseLoading)
        if (isUseDefaultTitleBar) {
            contentViewDelegateBuilder.setTitleViewLayoutId(titleViewId)
            contentViewDelegateBuilder.setTitleView(titleView)
        }

        contentViewDelegate = contentViewDelegateBuilder.build()
        mContentView = contentViewDelegate.proxySetContentView()
    }


    protected fun <K : View> findViewById(@IdRes id: Int): K {
        return mContentView.findViewById(id)
    }


    override fun onLeftClick(titleView: View?) {

        //查看当前页面是否有Navigation
        requireActivity().finish()
    }


    open val rightTitle: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isUseEvent) {
            EventManager.register(this)
        }


        initTitleBar(contentViewDelegate.titleView)
        initView()
        initData()

        initEvent()
        mIsPrepare = true
        mIsFirst = true
        loadData()
    }


    open val statusBarColorRes: Int? = null

    open val navigationBarColorRes: Int = android.R.color.white

    override fun onResume() {
        super.onResume()
        if (isUseStatusBar) {
            immersionBar {
                keyboardEnable(keyboadFixed)
                statusBarColorRes?.let { this.statusBarColor(it) }
                if (statusBarColorRes == android.R.color.white) {
                    this.statusBarDarkFont(true, 0.2f)
                } else {
                    this.statusBarDarkFont(false)
                }

                navigationBarColor(navigationBarColorRes)
                titleBar(contentViewDelegate.titleView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setContentStatus()//用于取消 状态页面的loading
        if (isUseEvent) {
            EventManager.unregister(this)
        }
    }

    //加载数据
    protected fun loadData() {
        if (!mIsPrepare || !mIsVisible || !mIsFirst) {
            return
        }
        lazyLoad()
        mIsFirst = false
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //如果Fragment中有present, 则可以重写present里面的retry方法, 不然, 则重写Fragment的retry
    override fun reTry() {

    }

    protected fun setErrorStatus() {
        setViewStatus(ViewState.VIEW_STATE_ERROR)
    }

    protected fun setContentStatus() {
        setViewStatus(ViewState.VIEW_STATE_CONTENT)
    }

    protected fun setLoadingStatus() {
        setViewStatus(ViewState.VIEW_STATE_LOADING)
    }
}
