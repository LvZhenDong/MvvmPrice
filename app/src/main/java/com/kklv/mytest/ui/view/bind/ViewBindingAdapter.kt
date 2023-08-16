package com.kklv.mytest.ui.view.bind

import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bumptech.glide.Glide
import com.kklv.mytest.R
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

@BindingAdapter("imgUrl")
fun ImageView.setImgUrl(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .into(this)
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
        expandableLayout.collapse(true)
    } else {
        expandableLayout.expand(true)
    }
}

@BindingAdapter("reverseWithAnim")
fun isReverseWithAnim(view: View, isReverse: Boolean) {
    val animator = ValueAnimator.ofFloat(if (isReverse) 360f else 180f, if (isReverse) 180f else 360f)
    animator.addUpdateListener {
        val rotation = it.animatedValue as Float
        view.rotation = rotation
    }
    animator.duration = view.context.resources.getInteger(R.integer.anim_duration).toLong()
    animator.start()

    view.findViewTreeLifecycleOwner()?.let {
        it.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                Log.i("kklv","onDestroy")
                animator.cancel()
            }
        })
    }
}