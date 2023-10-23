package com.kklv.mytest.data.bean.base

/**
 * @author lvzhendong
 * @data 2023/8/24
 * @description
 */
data class PageListBean<T>(
    val list:ArrayList<T>,
    val page:PageBean
)

data class PageBean(
    val current:Int,
    val size:Int,
    val total:Int
)