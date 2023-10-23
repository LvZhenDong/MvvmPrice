package com.kklv.common.data.api

import com.kklv.common.data.bean.TokenBodyBean
import com.kklv.mytest.data.bean.base.BaseJdResponse
import com.kklv.mytest.data.bean.base.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap


/**
 * Author:lvzhendong
 * Created:2023/8/16
 * Desc:
 */
interface UserService {
    @GET("/user_v1/tokens/access_token")
    fun refreshToken(@QueryMap param: Map<String, String>): Call<TokenBodyBean>

    @POST("/user_v1/session/mobile")
    fun login(
        @Body body:HashMap<String,Any>
    ):Call<BaseJdResponse<LoginResponse>>
}