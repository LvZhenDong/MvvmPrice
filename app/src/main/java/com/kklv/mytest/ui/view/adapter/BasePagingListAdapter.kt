package com.kklv.mytest.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.paging.PagingDataAdapter

class BasePagingListAdapter<T : Any, VB : ViewDataBinding>(
    private val layoutId: Int,
    private val onBind: (Int, T, VB) -> Unit,
    areItemsTheSameFun: (T, T) -> Boolean,
    areContentsTheSameFun: (T, T) -> Boolean
) : PagingDataAdapter<T, BasePagingListAdapter<T, VB>.ViewHolder>(
    GenericDiffUtilCallback(areItemsTheSameFun, areContentsTheSameFun)
) {

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            onBind(position, it, holder.binding)
            holder.binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VB>(inflater, layoutId, parent, false)
        return ViewHolder(binding)
    }
}

class GenericDiffUtilCallback<T:Any>(
    private val areItemsTheSameFun: (T, T) -> Boolean,
    private val areContentsTheSameFun: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return areItemsTheSameFun(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentsTheSameFun(oldItem, newItem)
    }
}


