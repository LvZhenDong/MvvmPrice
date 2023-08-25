package com.kklv.mytest.domain.request

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.observable
import com.kklv.mytest.data.api.VisitService
import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.data.bean.base.PageListBean
import com.kklv.mytest.data.bean.request.PageRequestBean
import com.kklv.mytest.data.bean.request.PageSizeBean
import com.kklv.mytest.data.bean.request.PageSizeBean.Companion.PAGE_SIZE
import com.kklv.mytest.data.bean.request.PageSizeBean.Companion.PAGE_START
import com.kklv.mytest.data.paging.VisitDataSource
import com.kklv.mytest.data.repository.DataRepository
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.domain.message.MutableResult
import com.kunminx.architecture.domain.message.Result
import com.kunminx.architecture.domain.request.Requester
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author lvzhendong
 * @data 2023/8/22
 * @description
 */
class VisitRequester : Requester(), DefaultLifecycleObserver {

    private var mDisposable: Disposable? = null

    private val visitListResult: MutableResult<ArrayList<VisitBean>> = MutableResult()

    fun getVisitListResult(): Result<ArrayList<VisitBean>> {
        return visitListResult
    }

    private val pageRequestBean = PageRequestBean(PageSizeBean(PAGE_START))

    fun getContractList() {
        DataRepository.getInstance().getNetWorkObservableData(VisitService::class.java) { service ->
            service.getVisitList(pageRequestBean)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DataResult<PageListBean<VisitBean>>> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    mDisposable = null
                }

                override fun onNext(t: DataResult<PageListBean<VisitBean>>) {
                    visitListResult.postValue(t.result.list)
                }
            })
    }

    val listData = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE, // 每页的项数
            prefetchDistance = 5, // 预加载距离
            enablePlaceholders = false // 是否启用占位符
        ),
        pagingSourceFactory = { VisitDataSource() }
    ).observable
}