package com.kklv.mytest.data.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kklv.mytest.R
import com.kklv.mytest.databinding.ViewListFooterBinding

/**
 * @author lvzhendong
 * @data 2023/8/26
 * @description
 */
class HeaderFooterAdapter() : LoadStateAdapter<HeaderFooterAdapter.FooterViewHolder>() {

    inner class FooterViewHolder(val binding: ViewListFooterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {

        if (loadState == LoadState.Loading) {
            holder.binding.tvLoading.text = "加载中..."
        } else {
            holder.binding.tvLoading.text = "没有更多了"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        Log.i("kklv", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        return FooterViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_list_footer, parent, false))
    }
}