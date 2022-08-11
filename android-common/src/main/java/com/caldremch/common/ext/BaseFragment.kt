package com.caldremch.common.ext

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.caldremch.android.log.debugLog
import com.caldremch.common.R
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.caldremch.common.base.BaseCommonFragment
import com.caldremch.common.base.ILifeCycleLogger


/**
 * @author Caldremch
 */
abstract class BaseFragment : BaseCommonFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object{
        val BIZ_LOGGER = object : ILifeCycleLogger {
            override fun log(tag: String, msg: String) {
                debugLog(tag) { msg }
            }
        }
    }

    override fun getLogger(): ILifeCycleLogger {
        return BIZ_LOGGER
    }

    open val titleBackground: Int
        get() = Color.WHITE
    open val leftIcon: Int? = null
    open val titleColor: Int? = null

    override val titleView: View?
        get() = LayoutInflater.from(requireContext()).inflate(R.layout.common_titlebar_layout, null)

    override fun initTitleBar(titleView: View?) {
        val titleBar = contentViewDelegate.titleView?.findViewById<TitleBar>(R.id.title_bar)
        titleBar?.setBackgroundColor(titleBackground)
        rightTitle?.let { titleBar?.rightTitle = it }
        leftIcon?.let { titleBar?.setLeftIcon(it) }
        titleColor?.let { titleBar?.setTitleColor(it) }
        titleBarTitle.let { titleBar?.setTitle(it) }
        titleBar?.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                this@BaseFragment.onLeftClick(titleBar)
            }

            override fun onRightClick(titleBar: TitleBar) {
                this@BaseFragment.onRightClick(titleBar)

            }
        })
    }

    open fun isUserNavigation():Boolean = true

    override fun onLeftClick(titleView: View?) {
        if(isUserNavigation()){
//           val con =  findNavController()
//            if(con.backQueue.size == 2){
//                requireActivity().finish()
//            }else{
//                con.popBackStack()
//            }
        }else{
            requireActivity().finish()
        }
    }

}