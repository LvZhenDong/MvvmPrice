package com.kklv.mytest.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * 一个简单的 RecyclerView Adapter，使用 DataBinding 进行布局绑定
 * @param T 数据源的数据类型
 * @param VB ViewDataBinding 类型，对应布局文件生成的 Binding 类
 * @property data 数据源
 * @property layoutId 布局文件的资源 ID
 * @property onBind 绑定数据到布局的回调函数，接收三个参数：position - 当前项的位置，item - 当前项的数据，binding - 当前项的布局的 Binding 对象
 * @author:lvzhendong
 */
class BaseSimpleAdapter<T, VB : ViewDataBinding>(
    private var data: ArrayList<T>,
    private val layoutId: Int,
    private val onBind: (Int, T, VB) -> Unit
) : RecyclerView.Adapter<BaseSimpleAdapter<T, VB>.SimpleViewHolder>() {

    constructor(data: List<T>, layoutId: Int, onBind: (Int, T, VB) -> Unit) : this(ArrayList(data), layoutId, onBind)

    /**
     * 添加新的数据到数据源
     * @param newData 新的数据源
     */
    fun setData(newData: ArrayList<T>) {
        data = newData
        notifyDataSetChanged()
    }

    fun setData(newData: List<T>) {
        setData(ArrayList(newData))
    }

    /**
     * 添加新的数据到数据源
     * @param newData 新的数据源
     */
    fun addData(newData: ArrayList<T>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun addData(newData: List<T>){
        addData(ArrayList(newData))
    }

    fun getData():ArrayList<T>{
        return data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VB>(inflater, layoutId, parent, false)
        return SimpleViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        onBind.invoke(position, data[position], holder.binding)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = data.size

    inner class SimpleViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

}
