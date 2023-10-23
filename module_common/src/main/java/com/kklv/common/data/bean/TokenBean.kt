package com.kklv.common.data.bean

import com.google.gson.JsonElement

/**
 * Author:lvzhendong
 * Created:2023/8/16
 * Desc:
 */
data class TokenBean(
    val ac_token:String,
    val rf_token:String
)

data class TokenBodyBean(
    val message:String,
    val action:String,
    val code:Int,
    val data:JsonElement,
    val extra:Any
)