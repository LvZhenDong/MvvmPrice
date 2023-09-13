package com.kunminx.architecture.ui.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.architecture.utils.ext.dpInt

/**
 * @author lvzhendong
 * @data 2023/9/13
 * @description
 */
class MyItemDecorator():RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0,4.dpInt,4.dpInt,0)
    }

}