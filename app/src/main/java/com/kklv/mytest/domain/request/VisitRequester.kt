package com.kklv.mytest.domain.request

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.kklv.mytest.data.api.VisitService
import com.kklv.mytest.data.paging.PagingUtil
import com.kklv.mytest.data.paging.GenericDataSource
import com.kunminx.architecture.domain.message.MutableResult
import com.kunminx.architecture.domain.request.Requester

/**
 * @author lvzhendong
 * @data 2023/8/22
 * @description
 */
class VisitRequester : Requester(), DefaultLifecycleObserver {

    val isNeedRefresh: MutableResult<Boolean> = MutableResult(false)

    val pagingData = Pager(
        config = PagingUtil.getPagingConfig(),
        pagingSourceFactory = {
            GenericDataSource(VisitService::class.java) { visitService, pageRequestBean ->
                visitService.getVisitList(pageRequestBean)
            }
        }
    ).liveData.cachedIn(viewModelScope)

}