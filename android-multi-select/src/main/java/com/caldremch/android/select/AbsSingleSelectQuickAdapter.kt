package com.caldremch.android.select

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by Leon on 2022/7/17
 */
abstract class AbsSingleSelectQuickAdapter<T, VH : BaseViewHolder> @JvmOverloads constructor(val rv: RecyclerView, @LayoutRes private val layoutResId: Int, data: MutableList<T>? = null) : BaseQuickAdapter<T, VH>(layoutResId, data) {

    init {
        //使得单选立马生效
        this.setOnItemClickListener(null)
    }

    //选中位置
    private var currentSelectedPos: Int = -1

    /**
     * 获取选中的位置
     */
    fun getSelectPos(): Int {
        return currentSelectedPos
    }

    /**
     * 设置默认选中位置
     */
    fun setSelectedPos(pos: Int) {
        currentSelectedPos = pos
    }

    /**
     * 是否有选中
     *
     */
    fun hasSelected(): Boolean {
        return currentSelectedPos != -1
    }

    /**
     * 获取选中的数据
     */
    fun getSelectedData(): T? {
        if (hasSelected()){
            return data[currentSelectedPos]
        }else{
            return null
        }
    }

    /**
     * 位置 [pos] 是否是选中的
     */
    protected fun isSelectedWith(pos: Int): Boolean {
        return currentSelectedPos == pos
    }


    /**
     * 处理选中 [view]  的处理, 通过 [View.findViewById] 找到对应的 View 进行处理
     */
    abstract fun handleSelected(view: View)

    /**
     * 取消选中 [holder], 可以通过[holder], 也可以通过 [View.findViewById] 找到对应的 View 进行处理
     */
    abstract fun handleUnSelected(holder: VH, view: View)

    /**
     * 清除选择
     */
    fun clearSelect() {
        setSelectedPos(-1)
    }
}