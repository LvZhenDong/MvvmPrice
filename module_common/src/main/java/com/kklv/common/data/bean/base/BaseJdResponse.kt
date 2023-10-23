package com.kklv.mytest.data.bean.base

import com.kklv.common.domain.JdConstant.Companion.CODE_SUC

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
){
    fun isSuccess():Boolean{
        return code == CODE_SUC
    }
}