package com.caldremch.common.widget.status

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.caldremch.common.R
import com.caldremch.common.widget.status.ViewState.Companion.VIEW_STATE_CONTENT
import com.caldremch.common.widget.status.ViewState.Companion.VIEW_STATE_ERROR
import com.caldremch.common.widget.status.ViewState.Companion.VIEW_STATE_LOADING

/**
 * @author Caldremch
 */
class StatusView : FrameLayout, IStatusView, DefaultLifecycleObserver {

    //一版界面状态,不含empty
    private var contentView: View? = null
    private var mErrorView: View? = null
    private var mLoadingView: View? = null
    private var mLoadWidget: LoadingCircleRing? = null
    private var overrideStatusView: IStatusView? = null
    private var handleContentView = true

    constructor(
        context: Fragment,
        fragmentRootView: View?
    ) : super(context.requireContext()) {
        //重写回调
        if (context is IStatusView) {
            overrideStatusView = context
        }

        //生命周期
        context.lifecycle.addObserver(this)

        contentView = fragmentRootView
        addSubView(contentView)
        initStatusView()
    }

    /**
     * 将 ContentView 加到了当前容器
     *
     * @param context
     * @param layoutResID
     */
    constructor(context: Context, @LayoutRes layoutResID: Int) : super(context) {

        //重写回调
        if (context is IStatusView) {
            overrideStatusView = context
        }

        //生命周期
        if (context is LifecycleOwner) {
            (context as LifecycleOwner).lifecycle.addObserver(this)
        }
        contentView = View.inflate(getContext(), layoutResID, null)
        addSubView(contentView)
        initStatusView()
    }

    /**
     * 如果直接传递了 contentView 进来, 那么不会对 contentView 进行隐藏或者显示, 而是知识单纯的出现 loading, error状态,
     * 这两种状态任意一个Gone, 都显示出 contentView 内容
     *
     * @param context
     */
    constructor(context: Context) : super(context) {

        //重写回调
        if (context is IStatusView) {
            overrideStatusView = context
        }

        //生命周期
        if (context is LifecycleOwner) {
            (context as LifecycleOwner).lifecycle.addObserver(this)
        }
        handleContentView = false //不处理
        initStatusView()
    }

    private fun initStatusView() {
        //提供可重写的errorView
        mErrorView = if (overrideStatusView != null) {
            if (overrideStatusView!!.errorView == null) initErrorViewImpl() else overrideStatusView!!.errorView
        } else {
            initErrorViewImpl()
        }
        if (mErrorView != null) {
            addSubView(mErrorView)
            mErrorView!!.visibility = View.GONE
        }
        mLoadingView = initLoadingViewImpl()
        if (mLoadingView != null) {
            addSubView(mLoadingView)
            mLoadingView!!.visibility = View.GONE
        }

        //默认状态
    }

    fun addSubView(view: View?) {
        addView(
            view,
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun initErrorViewImpl(): View {
        mErrorView = LayoutInflater.from(context).inflate(R.layout.cm_common_loading_error, null)
        mErrorView?.setBackgroundColor(Color.WHITE)
        val textView = mErrorView!!.findViewById<TextView>(R.id.tv_reload)
        textView?.setOnClickListener { v: View? ->
            if (overrideStatusView != null) {
                setViewStatus(VIEW_STATE_LOADING)
                overrideStatusView!!.reTry()
            }
        }
        return mErrorView!!
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun initLoadingViewImpl(): View {
        mLoadWidget = LoadingCircleRing(context)
        val frameLayout = FrameLayout(context)
        frameLayout.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        frameLayout.setBackgroundColor(Color.WHITE)
        val params = LayoutParams(
            dp2px(context, 30f),
            dp2px(context, 30f)
        )
        params.gravity = Gravity.CENTER
        frameLayout.addView(mLoadWidget, params)
        return frameLayout
    }

    override fun startLoadingImpl() {
        mErrorView!!.visibility = View.GONE
        contentView?.visibility = View.GONE
        mLoadingView!!.visibility = View.VISIBLE
        if (mLoadWidget != null && !mLoadWidget!!.isRunning) {
            mLoadWidget!!.startAnim()
        }
    }

    override fun stopLoadingImpl() {
        if (mLoadWidget != null && mLoadWidget!!.isRunning) {
            mLoadWidget!!.stopAnim()
        }
    }

    override val errorView: View?
        get() = mErrorView!!


    override fun setViewStatus(@ViewState status: Int) {
        when (status) {
            VIEW_STATE_LOADING -> startLoadingImpl()
            VIEW_STATE_CONTENT -> {
                stopLoadingImpl()
                if (mErrorView!!.visibility == View.VISIBLE) {
                    mErrorView!!.visibility = View.GONE
                }

                contentView?.let {
                    if (it.visibility == View.GONE) {
                        it.visibility = View.VISIBLE
                    }
                }

                if (mLoadingView!!.visibility == View.VISIBLE) {
                    mLoadingView!!.visibility = View.GONE
                }
            }
            VIEW_STATE_ERROR -> {
                stopLoadingImpl()
                mErrorView!!.visibility = View.VISIBLE
                contentView?.visibility = View.GONE
                mLoadingView!!.visibility = View.GONE
            }
        }
    }


    override fun onDestroy(owner: LifecycleOwner) {
        stopLoadingImpl()
    }


}