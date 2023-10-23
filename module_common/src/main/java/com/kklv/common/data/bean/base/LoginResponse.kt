package com.kklv.mytest.data.bean.base

/**
 * @author lvzhendong
 * @data 2023/9/19
 * @description
 */
data class LoginResponse(
    val rf_token:String,
    val ac_token:String,
    val user:UserId
)

data class UserId(
    val user_id:String,
    val uid:String
)