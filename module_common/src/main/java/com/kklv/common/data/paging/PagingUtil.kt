package com.kklv.common.data.paging

import androidx.paging.PagingConfig
import com.kklv.common.data.bean.request.PageSizeBean

/**
 * @author lvzhendong
 * @data 2023/8/25
 * @description
 */
class PagingUtil {
    companion object {
        fun getPagingConfig(): PagingConfig {
            return PagingConfig(
                pageSize = PageSizeBean.PAGE_SIZE, // 每页的项数
                prefetchDistance = 5, // 预加载距离
                initialLoadSize = PageSizeBean.PAGE_SIZE,//初始加载项数
                enablePlaceholders = false // 是否启用占位符
            )
        }
    }
}