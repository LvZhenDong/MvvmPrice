package com.kklv.mytest.data.api

import com.kklv.mytest.data.bean.BaseJdResponse
import com.kklv.mytest.data.bean.StoreDetailsBean
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Author:lvzhendong
 * Created:2023/8/12
 * Desc:
 */
interface StoreService {

    @GET("/bapi/jd_hero/stores/v3/detail/base_data")
    fun getStoreDetailsInfo(
        @Query("store_id") storeId: String,
        @Query("latitude")latitude:String,
        @Query("longitude")longitude:String
    ): Call<BaseJdResponse<StoreDetailsBean>>
}