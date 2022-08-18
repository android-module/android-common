package com.caldremch.common.widget

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.caldremch.android.log.debugLog
import com.caldremch.common.widget.status.StatusView
import com.caldremch.common.R

/**
 *
 * @author Caldremch
 *
 * @date 2020-06-29 00:20
 *
 * @email caldremch@163.com
 *
 * @describe 承载 View
 *
 **/
class DecorViewProxy {

    private lateinit var context: Context
    private var isActivity: Boolean = true
    private var isUseLoading: Boolean = false
    private var contentViewLayoutId: Int = 0
    private var titleViewLayoutId: Int = 0
    private var container: ViewGroup? = null //fragment 使用
    var statusView: StatusView? = null
    private var inflater: LayoutInflater? = null
    var titleView: View? = null
    var layoutView: View? = null
    var enablePlaceHolderStatusView: Boolean = false
    private  var fragment: Fragment?=  null

    class Builder {
        private  var activity: Activity? = null
        private  var fragment: Fragment? = null
        private var context: Context
        private var isActivity: Boolean = true
        private var isUseLoading: Boolean = false
        private var contentViewId: Int = 0
        private var titleViewLayoutId: Int = 0
        private var container: ViewGroup? = null //fragment 使用
        private var inflater: LayoutInflater? = null
        private var titleView: View? = null
        private var layoutView: View? = null
        private var enablePlaceHolderView: Boolean = false

        constructor(fragment: Fragment, inflater: LayoutInflater, container: ViewGroup?) {
            this.fragment = fragment
            context = fragment.requireContext()
            this.container = container
            isActivity = false
            this.inflater = inflater
        }

        constructor(activity: Activity) {
            this.activity = activity
            context = activity
            isActivity = true
        }

        fun isUseLoading(isUseLoading: Boolean): Builder {
            this.isUseLoading = isUseLoading
            return this
        }

        fun enablePlaceHolderView(): Builder {
            this.enablePlaceHolderView = true
            return this
        }

        fun setContentViewLayoutId(contentViewId: Int): Builder {
            this.contentViewId = contentViewId
            return this
        }

        fun setTitleViewLayoutId(titleViewId: Int): Builder {
            this.titleViewLayoutId = titleViewId
            return this
        }

        fun setTitleView(titleView: View?): Builder {
            this.titleView = titleView
            return this
        }

        fun build(): DecorViewProxy {
            val proxy = DecorViewProxy()
            proxy.isUseLoading = isUseLoading
            proxy.isActivity = isActivity
            proxy.contentViewLayoutId = contentViewId
            proxy.titleViewLayoutId = titleViewLayoutId
            proxy.inflater = inflater
            proxy.context = context
            proxy.titleView = titleView
            proxy.layoutView = layoutView
            proxy.fragment = fragment
            proxy.enablePlaceHolderStatusView = enablePlaceHolderView
            return proxy
        }

        fun setContentView(layoutView: View?): Builder {
            this.layoutView = layoutView
            return this
        }

    }

    private lateinit var rootXml: View
    private var systemStatusBarView: View? = null

    fun getRoomXml() = rootXml
    fun getSystemStatusBarView() = systemStatusBarView

    //由开发者设置的ContentView或者LayoutId
    private fun getUserView(): View {
        val childRootView: View
        if (isActivity) {
            childRootView = if(layoutView != null) layoutView!! else LayoutInflater.from(context).inflate(contentViewLayoutId, null)
        } else {
            //优先使用View
            if (layoutView != null) {
                childRootView = layoutView!!
            } else {
                childRootView = inflater!!.inflate(contentViewLayoutId, container, false)
            }
        }

        rootXml = childRootView
        return childRootView
    }

    //返回 contentView
    fun proxySetContentView(): View {

        //创建对应的contentView
        val childRootView: View = getUserView()

        var targetTitleView: View? = null
        var isSelfConfigTitleView = false//是否是自己配置设置的默认TitleView

        if (titleView == null) {
            //选寻找布局中的 titleId
            targetTitleView = childRootView.findViewById(R.id.cm_android_common_title_view_id)
        } else {
            isSelfConfigTitleView = true
            targetTitleView = titleView!!
        }

        //看看是不是单独社设置了
        if (targetTitleView == null) {
            //跟布局中没有 titleView, 看看 Activity 或者 Fragment 中是否设置了, titleViewId ,
            // 如果还是没有, 那么直接添加进入跟布局,没有 titleView
            if (titleViewLayoutId == 0) {
                //如果有loading,
                if (isUseLoading){
                    statusView = DecorViewProxyUtils.createStatusView(context, isActivity, contentViewLayoutId, fragment, childRootView)
                    return DecorViewProxyUtils.wrapLoading(context, statusView!!)
                }
                return childRootView
            } else {
                //在 Activity 中设置了 titleId, 或者 设置了 T
                val parentLayout = ConstraintLayout(context)
                val autoAddTitleView: View = LayoutInflater.from(context).inflate(titleViewLayoutId, parentLayout, false)
                parentLayout.addView(autoAddTitleView)

                val titleParams = autoAddTitleView.layoutParams as ConstraintLayout.LayoutParams
                titleParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                titleParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                titleParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                autoAddTitleView.layoutParams = titleParams
                if (autoAddTitleView.id == View.NO_ID) {
                    autoAddTitleView.id = R.id.cm_android_common_title_view_id
                }

                val contentLayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                )
                contentLayoutParams.topToBottom = autoAddTitleView.id
                contentLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                contentLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                contentLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

                //将 StatusView 添加到 title 的底下
                if (isUseLoading) {
                    statusView = StatusView(context, contentViewLayoutId)
                    //将 statusView 添加到 title 底下
                    parentLayout.addView(statusView, contentLayoutParams)
                } else {
                    parentLayout.addView(childRootView, contentLayoutParams)
                }
                return parentLayout
            }

        } else {
            //仅支持ConstraintLayout形式的布局
            //title类型在childRootView中
            if (!isSelfConfigTitleView) {
                if (childRootView is ConstraintLayout) {
                    //将 StatusView 添加到 title 的底下, statusView 金包含错误状态, 加载状态
                    if (isUseLoading) {
                        statusView = StatusView(context)
                        val stateViewLayoutParams = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                        )
                        stateViewLayoutParams.topToBottom = targetTitleView.id
                        stateViewLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                        stateViewLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                        stateViewLayoutParams.bottomToBottom =
                            ConstraintLayout.LayoutParams.PARENT_ID
                        childRootView.addView(statusView, stateViewLayoutParams)
                        return childRootView
                    } else {
                        if (context is Activity) {
                            (context as Activity).setContentView(childRootView)
                            return childRootView
                        }
                    }

                } else {
                    //如果不是ConstraintLayout
                    return childRootView
                }
            } else {

                //目前 仅仅是这种场景验证有效了

                //titleView 自己设置
                val parentLayout = ConstraintLayout(context)

                if (enablePlaceHolderStatusView) {  //用来做沉浸式的, 假如已经使用titleBarView来做 ,那么就不需要了
                    addPlaceHolderStatusView(parentLayout)
                }
                val titleBelowId:Int? = if (enablePlaceHolderStatusView) R.id.cm_default_android_common_status_view_id else null

                //add title view
                addTitleView(parentLayout, targetTitleView, null)
                //add content view or status view
                addContentView(targetTitleView, parentLayout, childRootView)
                return parentLayout

            }
        }

        return ConstraintLayout(context)
    }


    private fun addPlaceHolderStatusView(parentLayout: ConstraintLayout) {
        val systemStatusBarView = View(parentLayout.context)
        systemStatusBarView.id =  R.id.cm_default_android_common_status_view_id
        val statusViewLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
        )
        statusViewLayoutParams.height = 1
        statusViewLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        parentLayout.addView(systemStatusBarView, statusViewLayoutParams)
        this.systemStatusBarView = systemStatusBarView
    }

    private fun addTitleView(
        parentLayout: ConstraintLayout, targetTitleView: View, titleBelowId: Int? = null
    ) {
//        val tv = TypedValue()
//        var height = ConstraintLayout.LayoutParams.WRAP_CONTENT
//        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
//            height = TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
//        }
        val titleParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT

        )
        titleParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        titleParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        titleParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        if(titleBelowId == null){
            titleParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        }else{
            titleParams.topToBottom = titleBelowId

        }
        if (targetTitleView.id == View.NO_ID) {
            targetTitleView.id = R.id.cm_android_common_title_view_id
        }
        parentLayout.addView(targetTitleView, titleParams)
    }

    private fun addContentView(
        targetTitleView: View, parentLayout: ConstraintLayout, childRootView: View
    ) {
        val contentLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            0
        )
        contentLayoutParams.topToBottom = targetTitleView.id
        contentLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        contentLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        contentLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

        //将 StatusView 添加到 title 的底下
        if (isUseLoading) {
            //这里要区分activity和fragment
            statusView = if(isActivity) StatusView(context, contentViewLayoutId) else StatusView(fragment!!, childRootView)
            //将 statusView 添加到 title 底下
            parentLayout.addView(statusView, contentLayoutParams)
            debugLog { "addContentView: 添加loading" }
        } else {
           debugLog { "addContentView: 没有loading" }
            parentLayout.addView(childRootView, contentLayoutParams)
        }
    }


}