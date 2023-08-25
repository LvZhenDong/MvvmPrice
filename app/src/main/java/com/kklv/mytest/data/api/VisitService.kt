package com.kklv.mytest.data.api

import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.data.bean.base.BaseJdResponse
import com.kklv.mytest.data.bean.base.PageListBean
import com.kklv.mytest.data.bean.request.PageRequestBean
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author lvzhendong
 * @data 2023/8/24
 * @description
 */
interface VisitService {
    /**
     * 获取拜访列表
     */
    @POST("/bapi/jd_hero/visit_v2/list")
    fun getVisitList(
        @Body page: PageRequestBean
    ): Call<BaseJdResponse<PageListBean<VisitBean>>>
}