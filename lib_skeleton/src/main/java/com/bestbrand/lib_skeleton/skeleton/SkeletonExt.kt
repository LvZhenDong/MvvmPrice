package com.bestbrand.lib_skeleton.skeleton

import android.view.View
import androidx.annotation.LayoutRes
import com.bestbrand.lib_skeleton.R

/**
 * @author lvzhendong
 * @data 2023/9/7
 * @description
 */


fun buildSkeleton(
    rootView: View,
    @LayoutRes loadId: Int,
    showShimmer: Boolean = true
): ViewSkeletonScreen {
    return Skeleton.bind(rootView)
        .load(loadId)
        .color(R.color.color_skeleton_shimmer)
        .shimmer(showShimmer)
        .duration(1400)
        .angle(0)
        .show()
}