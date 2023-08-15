package com.kklv.mytest.data.bean.base

/**
 * Author:lvzhendong
 * Created:2023/8/13
 * Desc:
 */
data class BaseJdResponse<T>(
    val message:String,
    val action:String,
    val code:Int,
    val data:T,
    val extra:Any
)