package com.kklv.mytest.ui.view.bind

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.kklv.mytest.R
import com.kklv.mytest.ui.view.CustomPagerTitleView
import com.kklv.mytest.ui.view.adapter.BannerImageAdapter
import com.kunminx.architecture.utils.ext.dpFloat
import com.kunminx.architecture.utils.ViewPager2Helper
import com.youth.banner.Banner
import com.youth.banner.listener.OnPageChangeListener
import net.cachapa.expandablelayout.ExpandableLayout
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * Author:lvzhendong
 * Created:2023/8/11
 * Desc:
 */

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
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
fun setBannerImages(
    banner: Banner<String, BannerImageAdapter>,
    bannerImages: ArrayList<String>?,
    bannerIndicatorTv: TextView
) {

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
        it.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                animator.cancel()
            }
        })
    }
}

@BindingAdapter(value = ["navigatorData", "navigatorViewPager"], requireAll = true)
fun setNavigatorData(magicIndicator: MagicIndicator, tabs: ArrayList<String>?, viewPager: ViewPager2) {
    val commonNavigator = CommonNavigator(magicIndicator.context)
    commonNavigator.isAdjustMode = true
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
        override fun getCount(): Int {
            return tabs?.size?:0
        }

        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
            val titleView = CustomPagerTitleView(context)
            titleView.normalColor = context.getColor(R.color.color_666666)
            titleView.selectedColor = context.getColor(R.color.color_333333)
            titleView.textSize = 14f
            titleView.text = tabs?.get(index)
            titleView.setOnClickListener {
                viewPager.currentItem = index
            }

            return titleView
        }

        override fun getIndicator(context: Context): IPagerIndicator {
            val indicator = LinePagerIndicator(context)
            indicator.mode = LinePagerIndicator.MODE_EXACTLY
            indicator.lineWidth = 16.dpFloat
            indicator.lineHeight = 2.dpFloat
            indicator.setColors(context.getColor(R.color.color_15c782))

            return indicator
        }
    }
    magicIndicator.navigator = commonNavigator

    ViewPager2Helper.bind(magicIndicator, viewPager)
}

@BindingAdapter("textColorStr")
fun setTextColorStr(textView: TextView, colorString: String) {
    val formattedColorString = colorString.replace(" ", "").let { str ->
        if (str.startsWith("#")) {
            str
        } else {
            "#$str"
        }
    }

    val colorInt = try {
        Color.parseColor(formattedColorString)
    } catch (e: IllegalArgumentException) {
        // 在颜色解析失败时返回默认颜色
        Color.parseColor("#FF0000")
    }
    textView.setTextColor(colorInt)
}