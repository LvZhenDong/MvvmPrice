package com.kklv.mytest.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.kklv.mytest.data.api.VisitService
import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.data.bean.request.PageRequestBean
import com.kklv.mytest.data.bean.request.PageSizeBean
import com.kklv.mytest.data.bean.request.PageSizeBean.Companion.PAGE_START
import com.kklv.mytest.data.repository.DataRepository
import io.reactivex.Single

/**
 * @author lvzhendong
 * @data 2023/8/24
 * @description
 */
class VisitDataSource() : RxPagingSource<Int, VisitBean>() {
    override fun getRefreshKey(state: PagingState<Int, VisitBean>): Int? {
        // 检查当前数据集是否为空，如果为空，则返回 null，表示需要刷新。
        // 否则，返回当前数据集中的第一个项目的页数，以表示不需要刷新。
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, VisitBean>> {
        return Single.fromCallable {
            val currentPageKey = params.key ?: PAGE_START
            val resultData = DataRepository.getInstance().getNetWorkData(VisitService::class.java) { visitService ->
                visitService.getVisitList(PageRequestBean(PageSizeBean(currentPageKey, size = params.loadSize)))
            }

            val data = mutableListOf<VisitBean>()
            data.addAll(resultData.result.list)

            val prevKey = if (currentPageKey == 1) null else currentPageKey - 1

            LoadResult.Page(data = data, prevKey = prevKey, nextKey = currentPageKey.plus(1))
        }
    }
}