package com.kklv.mytest.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.architecture.domain.message.Result

/**
 * 一个可以订阅数据的 RecyclerView Adapter，使用 DataBinding 进行布局绑定，
 * @param T 数据源的数据类型
 * @param VB ViewDataBinding 类型，对应布局文件生成的 Binding 类
 * @property stateData 可订阅的数据源
 * @property lifecycleOwner LifecycleOwner
 * @property layoutId 布局文件的资源 ID
 * @property onBind 绑定数据到布局的回调函数，接收三个参数：position - 当前项的位置，item - 当前项的数据，binding - 当前项的布局的 Binding 对象
 * @author:lvzhendong
 */
class BaseResultDataAdapter<T, VB : ViewDataBinding>(
    private val stateData: Result<ArrayList<T>>,
    private val lifecycleOwner: LifecycleOwner,
    private val layoutId: Int,
    private val onBind: (Int, T, VB) -> Unit
) : RecyclerView.Adapter<BaseResultDataAdapter<T, VB>.SimpleViewHolder>() {

    private var data:ArrayList<T> = arrayListOf()

    init {
        stateData.observe(lifecycleOwner){
            data = it
            notifyDataSetChanged()
        }
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
