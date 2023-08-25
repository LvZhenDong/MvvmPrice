package com.kklv.mytest.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kklv.mytest.R
import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.databinding.ItemVisitBinding

/**
 * @author lvzhendong
 * @data 2023/8/24
 * @description
 */
class VisitListAdapter:PagingDataAdapter<VisitBean,VisitListAdapter.ViewHolder>(Diff) {
    class ViewHolder(val binding: ItemVisitBinding):RecyclerView.ViewHolder(binding.root)

    object Diff : DiffUtil.ItemCallback<VisitBean>() {

        override fun areItemsTheSame(oldItem: VisitBean, newItem: VisitBean): Boolean {
            return oldItem.visit_id == newItem.visit_id
        }

        override fun areContentsTheSame(oldItem: VisitBean, newItem: VisitBean): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemVisitBinding>(inflater, R.layout.item_visit, parent, false)
        return ViewHolder(binding)
    }
}