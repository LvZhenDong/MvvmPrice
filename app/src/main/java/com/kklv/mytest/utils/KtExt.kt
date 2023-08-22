package com.kklv.mytest.utils

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import androidx.annotation.LayoutRes
import com.bestbrand.lib_skeleton.skeleton.Skeleton
import com.bestbrand.lib_skeleton.skeleton.ViewSkeletonScreen
import com.kklv.mytest.R
import kotlin.math.roundToInt

/**
 * Author:lvzhendong
 * Created:2023/8/16
 * Desc:
 */

val Int.dpInt: Int
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).roundToInt()
    }

val Int.spInt: Int
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).roundToInt()
    }

val Int.spFloat: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }

val Float.dpInt: Int
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        ).roundToInt()
    }

val Int.dpFloat: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }

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