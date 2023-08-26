package com.kklv.mytest.data.bean.request

/**
 * @author lvzhendong
 * @data 2023/8/24
 * @description
 */
data class PageRequestBean(
    val page: PageSizeBean
)

data class PageSizeBean(
    val current: Int,
    val size: Int = PAGE_SIZE
) {
    companion object {
        const val PAGE_SIZE = 10
        const val PAGE_START = 1
    }
}