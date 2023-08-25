package com.kklv.mytest.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kklv.mytest.data.api.VisitService
import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.data.bean.request.PageRequestBean
import com.kklv.mytest.data.bean.request.PageSizeBean
import com.kklv.mytest.data.bean.request.PageSizeBean.Companion.PAGE_START
import com.kklv.mytest.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.ceil

/**
 * @author lvzhendong
 * @data 2023/8/24
 * @description
 */
class VisitDataSource : PagingSource<Int, VisitBean>() {
    override fun getRefreshKey(state: PagingState<Int, VisitBean>): Int? {
        // 检查当前数据集是否为空，如果为空，则返回 null，表示需要刷新。
        // 否则，返回当前数据集中的第一个项目的页数，以表示不需要刷新。
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VisitBean> {
        val currentPageKey = params.key ?: PAGE_START

        val result = withContext(Dispatchers.IO) {
            val resultData = DataRepository.getInstance().getNetWorkData(VisitService::class.java) { visitService ->
                visitService.getVisitList(PageRequestBean(PageSizeBean(currentPageKey, size = params.loadSize)))
            }
            resultData.result
        }
        return if (result.page.current > ceil(result.page.total.toDouble() / result.page.size).toInt()) {
            //没有更多数据了
            LoadResult.Error(NoMoreDataException())
        } else {
            val responseData = mutableListOf<VisitBean>()
            responseData.addAll(result.list)

            val prevKey = if (currentPageKey == 1) null else currentPageKey - 1
            LoadResult.Page(data = responseData, prevKey = prevKey, nextKey = currentPageKey.plus(1))
        }
    }
}