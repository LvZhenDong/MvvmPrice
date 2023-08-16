package com.kklv.mytest.utils

import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * 修改TextView顶部的图片
 */
fun TextView.drawableTop(drawableId: Int) {
    val drawable = ContextCompat.getDrawable(context, drawableId) // 从资源中获取 Drawable
    drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight) // 设置 drawable 的边界大小
    setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null) // 设置 drawableTop
}

/**
 * 修改TextView左边的图片
 */
fun TextView.drawableLeft(drawableId: Int?) {
    val drawable = if (drawableId != null) ContextCompat.getDrawable(context, drawableId) else null
    drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
}


fun TextView.setDrawablePadding(padding: Int) {
    // 获取 TextView 中的可绘制对象数组
    val drawables: Array<Drawable?> = this.compoundDrawables
    // 设置每个可绘制对象的间距
    for (drawable in drawables) {
        drawable?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
        }
    }
    // 应用间距到 TextView
    this.compoundDrawablePadding = padding
    // 重新设置可绘制对象到 TextView
    this.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3])
}

fun TextView.clearAllTextWatcher() {
    try {
        val listenersField = TextView::class.java.getDeclaredField("mListeners")
        listenersField.isAccessible = true
        val listeners = listenersField.get(this) as? ArrayList<TextWatcher>

        listeners?.clear()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}
