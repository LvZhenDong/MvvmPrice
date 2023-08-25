package com.kklv.mytest.domain.request

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.data.bean.request.PageSizeBean
import com.kklv.mytest.data.paging.PagingUtil
import com.kklv.mytest.data.paging.VisitDataSource
import com.kunminx.architecture.domain.request.Requester
import io.reactivex.disposables.Disposable

/**
 * @author lvzhendong
 * @data 2023/8/22
 * @description
 */
class VisitRequester : Requester(), DefaultLifecycleObserver {

    private var mDisposable: Disposable? = null

    val pagingData = Pager(
        config = PagingUtil.getPagingConfig(),
        pagingSourceFactory = { VisitDataSource() }
    ).liveData.cachedIn(viewModelScope)

}