package com.caldremch.android.select

import android.view.View
import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *
 * @author Caldremch
 *
 * @date 2020-09-03 11:58
 *
 * @email caldremch@163.com
 *
 * @describe 多选Adapter, 不支持数据源的变更, 如果数据源每次都不一样, 使用[MultiSelectAdapter]
 *
 * 基于[BaseQuickAdapter]
 *
 * [handleInside] true 代表多选在内部处理了, 只需要处理回调点击事件
 *
 *
 **/
abstract class MultiPositionSelectAdapter<T, VH : BaseViewHolder>
@JvmOverloads constructor(@LayoutRes private val layoutResId: Int,
                          data: MutableList<T>? = null) : BaseQuickAdapter<T, VH>(layoutResId, data) {

    /**
     * 选中数据
     */
    private val selectedPosList = arrayListOf<Int>()

    /**
     * 获取选中数据
     */
    open fun getSelectedData(): ArrayList<Int> {
        return selectedPosList
    }

    override fun setList(list: Collection<T>?) {
        //清除之前的选中, list为空的时候
        if (list == null) {
            selectedPosList.clear()
        }
        super.setList(list)
    }

    /**
     * @param
     * @return 是否选中
     */
    open fun isSelectWithPos(pos: Int): Boolean {
        return selectedPosList.contains(pos)
    }

    /**
     * @param key 根据位置进行删除
     */
    open fun removeSelectByPos(pos: Int) {
        selectedPosList.remove(pos)
    }

    //添加到选择
    open fun addToSelected(post: Int) {
        selectedPosList.add(post)
    }

    fun clearAllSelected() {
        selectedPosList.clear()
    }

    /**
     * 全部选中的位置
     */
    open fun addToSelected(postList: List<Int>) {
        selectedPosList.addAll(postList)
    }

    /**
     * @return 获取选中的长度
     */
    open fun getSelectedSize(): Int {
        return selectedPosList.size
    }

    /**
     * 打开多选, 并设置监听
     */
    fun setOnItemClickListenerEx(listener: OnItemClickListener?) {
        setOnItemClickListener(OnItemClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->
            val isSelected = isSelectWithPos(position)
            val toggle = isSelected.not()
            if (toggle) {
                addToSelected(position)
            } else {
                removeSelectByPos(position)
            }
            onHolderSelect(toggle, position, view)
            listener?.onItemClick(adapter, view, position)
        })
    }


    /**
     * 该方法在 [handleInside] 为 true 会调用, 可以通过onHolderSelect做UI 展示
     *
     * [isSelect] 操作结果, true 选中, false 反选
     * [position] 操作位置
     * [itemView] 通过[View.findViewById]查找到对应的 View 进行 UI 展示
     */
    open fun onHolderSelect(isSelect: Boolean, position: Int, itemView: View) {

    }


}