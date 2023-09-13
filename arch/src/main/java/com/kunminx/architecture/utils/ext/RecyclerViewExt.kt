package com.kunminx.architecture.utils.ext

import androidx.recyclerview.widget.RecyclerView

/**
 * @author lvzhendong
 * @data 2023/9/13
 * @description
 */

/**
 * 移除所以recyclerView的ItemDecoration
 */
fun <T : RecyclerView> T.removeAllItemDecorations() {
    while (itemDecorationCount > 0) {
        removeItemDecorationAt(0)
    }
}