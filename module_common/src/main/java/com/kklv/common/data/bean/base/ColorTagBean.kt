package com.kklv.mytest.data.bean.base

/**
 * @author lvzhendong
 * @data 2023/8/22
 * @description
 */
data class ColorTagBean(
    val text: String,
    val text_color: String,
    val bg_color: String?
) : TagData {
    override fun isTagSelected(): Boolean {
        return false
    }

    override fun getTagText(): String {
        return text
    }
}