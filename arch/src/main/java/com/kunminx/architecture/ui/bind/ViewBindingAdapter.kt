package com.kunminx.architecture.ui.bind

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Author:lvzhendong
 * Created:2023/8/11
 * Desc:
 */

@BindingAdapter("app:visibleOrGone")
fun setVisibleOrGone(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}