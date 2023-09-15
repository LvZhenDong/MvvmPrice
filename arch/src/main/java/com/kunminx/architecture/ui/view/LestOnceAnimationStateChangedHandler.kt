package com.kunminx.architecture.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bestbrand.lib_skeleton.skeleton.ShimmerLayout
import com.drake.statelayout.StateChangedHandler
import com.drake.statelayout.StateLayout
import com.drake.statelayout.Status
import com.kunminx.architecture.R

/**
 * 适用于骨骼图动画, 能保证动画至少完整执行一次动画, 避免屏幕闪烁
 */
open class LestOnceAnimationStateChangedHandler : StateChangedHandler {

    override fun onRemove(container: StateLayout, state: View, status: Status, tag: Any?) {
        if (status == Status.LOADING && state.parent is ShimmerLayout) {
            (state.parent as ShimmerLayout).stopShimmerAnimation()
        }
        super.onRemove(container, state, status, tag)
    }

    override fun onAdd(container: StateLayout, state: View, status: Status, tag: Any?) {
        if (container.indexOfChild(state) != -1) {
            state.visibility = View.VISIBLE
        } else if (status == Status.LOADING) {
            val shimmerLayout: ShimmerLayout = LayoutInflater.from(container.context)
                .inflate(R.layout.layout_shimmer, container, false) as ShimmerLayout
            shimmerLayout.setShimmerColor(container.context.getColor(com.bestbrand.lib_skeleton.R.color.color_skeleton_shimmer))
            shimmerLayout.setShimmerAngle(0)
            shimmerLayout.setShimmerAnimationDuration(1200)
            val lp: ViewGroup.LayoutParams = state.layoutParams
            if (lp != null) {
                shimmerLayout.layoutParams = lp
            }
            shimmerLayout.addView(state)

            shimmerLayout.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    shimmerLayout.startShimmerAnimation()
                }

                override fun onViewDetachedFromWindow(v: View) {
                    shimmerLayout.stopShimmerAnimation()
                }
            })
            container.addView(shimmerLayout)
            shimmerLayout.startShimmerAnimation()
        } else {
            container.addView(state)
        }
    }
}