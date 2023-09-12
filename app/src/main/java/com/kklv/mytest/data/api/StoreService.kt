package com.kklv.mytest.data.api

import com.kklv.mytest.data.bean.ContractListBean
import com.kklv.mytest.data.bean.NavBtnsBean
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.data.bean.StoreDetailsStatBean
import com.kklv.mytest.data.bean.base.BaseJdResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Author:lvzhendong
 * Created:2023/8/12
 * Desc:
 */
interface StoreService {

    /**
     * 门店详情
     */
    @GET("/bapi/jd_hero/stores/v3/detail/base_data")
    fun getStoreDetailsInfo(
        @Query("store_id") storeId: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Call<BaseJdResponse<StoreDetailsBean>>

    /**
     * 门店详情统计数据
     */
    @GET("/bapi/jd_hero/stores/v3/detail/statistics_data")
    fun getStoreDetailsStatInfo(
        @Query("store_id") storeId: String,
        @Query("time_sign") time: String
    ): Call<BaseJdResponse<StoreDetailsStatBean>>

    /**
     * 门店详情nav btn数据1
     */
    @GET("/bapi/jd_hero/stores/v3/detail/buttons1")
    fun getStoreDetailsNavBtn1(
        @Query("store_id") storeId: String
    ): Call<BaseJdResponse<NavBtnsBean>>

    /**
     * 门店详情nav btn数据2
     */
    @GET("/bapi/jd_hero/stores/v3/detail/buttons2")
    fun getStoreDetailsNavBtn2(
        @Query("store_id") storeId: String
    ): Call<BaseJdResponse<NavBtnsBean>>

    /**
     * 获取门店的合同列表
     */
    @GET("/bapi/jd_hero/stores/v3/detail/contracts")
    fun getStoreContractList(
        @Query("store_id") storeId: String
    ): Call<BaseJdResponse<ContractListBean>>

    @POST("/Device/Store/MarkStore")
    fun collectStore(
        @Body body: HashMap<String, Any>
    ): Call<BaseJdResponse<Boolean>>

}