package com.kklv.mytest.ui.view.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter

class BannerImageAdapter(mData: List<String>?) :
    BannerAdapter<String, BannerImageAdapter.BannerViewHolder>(mData) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        // 注意，必须设置为 match_parent，这个是 ViewPager2 强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder,
        data: String,
        position: Int,
        size: Int
    ) {
        Glide.with(holder.imageView)
            .load(data)
            .into(holder.imageView)
    }

    class BannerViewHolder(view: ImageView) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view
    }
}
