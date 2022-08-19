package com.caldremch.common.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import com.caldremch.common.base.BaseInit
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar

/**
 * Created by Leon on 2022/8/19.
 */
internal object BarUtils {


    fun initStatusBar(fragment: Fragment, baseInit: BaseInit, titleView: View?) {
        fragment.immersionBar {
            setData(baseInit, titleView)
        }
    }

    fun initStatusBar(activity: Activity, baseInit: BaseInit, titleView: View?) {
        activity.immersionBar {
            setData(baseInit, titleView)
        }
    }

    private fun ImmersionBar.setData(
        baseInit: BaseInit,
        titleView: View?
    ) {
        keyboardEnable(baseInit.keyboardFixed)
        baseInit.statusBarColor?.let { this.statusBarColorInt(it) }
        if (baseInit.statusBarColor == Color.WHITE) {
            this.statusBarDarkFont(true, 0.2f)
        } else {
            this.statusBarDarkFont(false)
        }
        baseInit.navigationBarColor?.let { this.navigationBarColor(it) }
        titleBar(titleView)
    }

}