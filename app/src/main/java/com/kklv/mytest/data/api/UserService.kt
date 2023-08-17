package com.kklv.mytest.data.api

import com.kklv.mytest.data.bean.TokenBodyBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


/**
 * Author:lvzhendong
 * Created:2023/8/16
 * Desc:
 */
interface UserService {
    @GET("/user_v1/tokens/access_token")
    fun refreshToken(@QueryMap param: Map<String, String>): Call<TokenBodyBean>
}