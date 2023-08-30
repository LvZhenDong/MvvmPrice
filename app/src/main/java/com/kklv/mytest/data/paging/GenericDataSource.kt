package com.kklv.mytest.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kklv.mytest.data.bean.base.BaseJdResponse
import com.kklv.mytest.data.bean.base.PageListBean
import com.kklv.mytest.data.bean.request.PageRequestBean
import com.kklv.mytest.data.bean.request.PageSizeBean
import com.kklv.mytest.data.bean.request.PageSizeBean.Companion.PAGE_START
import com.kklv.mytest.data.exception.NetWorkException
import com.kklv.mytest.data.exception.NoMoreDataException
import com.kklv.mytest.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import kotlin.math.ceil

/**
 * @author lvzhendong
 * @data 2023/8/24
 * @description
 */
class GenericDataSource<T : Any, S>(
    private val serviceClass: Class<S>,
    private val function: (S, PageRequestBean) -> Call<BaseJdResponse<PageListBean<T>>>
) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        // 检查当前数据集是否为空，如果为空，则返回 null，表示需要刷新。
        // 否则，返回当前数据集中的第一个项目的页数，以表示不需要刷新。
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val currentPageKey = params.key ?: PAGE_START

        val resultData = DataRepository.getInstance().getNetWorkData(serviceClass) { service ->
            function(service, PageRequestBean(PageSizeBean(currentPageKey, size = params.loadSize)))
        }

        if (resultData.responseStatus.isSuccess) {
            val result = resultData.result
            return if (result.page.current > ceil(result.page.total.toDouble() / result.page.size).toInt()) {
                //没有更多数据了
                LoadResult.Error(NoMoreDataException())
            } else {
                val responseData = mutableListOf<T>()
                responseData.addAll(result.list)

                val prevKey = if (currentPageKey == 1) null else currentPageKey - 1
                LoadResult.Page(data = responseData, prevKey = prevKey, nextKey = currentPageKey.plus(1))
            }
        } else {
            //网络异常
            return LoadResult.Error(NetWorkException())
        }
    }
}