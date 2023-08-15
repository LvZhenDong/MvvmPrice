package com.kklv.mytest.data.api

import com.kklv.mytest.data.bean.NavBtnsBean
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.data.bean.base.BaseJdResponse
import retrofit2.Call
import retrofit2.http.GET
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

    @GET("/bapi/jd_hero/stores/v3/detail/buttons1")
    fun getStoreDetailsNavBtn1(
        @Query("store_id") storeId: String
    ): Call<BaseJdResponse<NavBtnsBean>>

    @GET("/bapi/jd_hero/stores/v3/detail/buttons2")
    fun getStoreDetailsNavBtn2(
        @Query("store_id") storeId: String
    ): Call<BaseJdResponse<NavBtnsBean>>
}