package com.kunminx.architecture.utils.ext

import android.widget.Toast
import com.kunminx.architecture.utils.Utils
import java.util.LinkedList
import java.util.Queue

/**
 * @author lvzhendong
 * @data 2023/8/29
 * @description
 */

// 创建一个队列保存Toast消息
private val toastQueue: Queue<String> = LinkedList()

// 全局变量，标记是否正在显示Toast消息
private var isToastShowing: Boolean = false

fun String.toast() {
    // 判断新的消息与正在显示的消息是否相同，防止同一个消息连续toast多次
    if (isToastShowing && this == toastQueue.peek()) {
        return
    }

    toastQueue.add(this)
    if (!isToastShowing) {
        Toast.makeText(Utils.getApp(), this, Toast.LENGTH_SHORT).show()
    }
}