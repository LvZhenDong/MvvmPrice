package com.kklv.common.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kklv.common.R
import com.kklv.common.databinding.ViewListHeaderBinding

/**
 * @author lvzhendong
 * @data 2023/8/26
 * @description
 */
class HeaderAdapter() : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {
    class ViewHolder(binding: ViewListHeaderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.view_list_header,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}