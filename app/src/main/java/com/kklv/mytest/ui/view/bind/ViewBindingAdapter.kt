package com.kklv.mytest.ui.view.bind

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.kklv.mytest.ui.view.adapter.BannerImageAdapter
import com.youth.banner.Banner
import com.youth.banner.listener.OnPageChangeListener
import net.cachapa.expandablelayout.ExpandableLayout

/**
 * Author:lvzhendong
 * Created:2023/8/11
 * Desc:
 */

@BindingAdapter("visibleOrGone")
fun setVisibleOrGone(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("imgSrc")
fun setImgSrc(imageView: ImageView, @DrawableRes imgSrc: Int) {
    imageView.setImageResource(imgSrc)
}

@BindingAdapter(value = ["bannerImages", "bannerIndicatorTv"], requireAll = true)
fun setBannerImages(banner: Banner<String, BannerImageAdapter>, bannerImages: ArrayList<String>?, bannerIndicatorTv: TextView) {

    banner.findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
        banner.addBannerLifecycleObserver(lifecycleOwner)
    }
    banner.setAdapter(BannerImageAdapter(bannerImages))
    val dataCount = banner.adapter.realCount
    bannerIndicatorTv.text = "1/${dataCount}"
    banner.addOnPageChangeListener(object : OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            bannerIndicatorTv.text = "${position + 1}/${dataCount}"
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

    })
}

@BindingAdapter("isExpanded")
fun isExpanded(expandableLayout: ExpandableLayout, isExpanded: Boolean) {
    if (!isExpanded) {
        expandableLayout.collapse(true);
    } else {
        expandableLayout.expand(true);
    }
}