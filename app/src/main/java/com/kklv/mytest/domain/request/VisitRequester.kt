package com.kklv.mytest.domain.request

import androidx.lifecycle.DefaultLifecycleObserver
import com.kklv.mytest.data.api.VisitService
import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.data.bean.base.PageListBean
import com.kklv.mytest.data.bean.request.PageRequestBean
import com.kklv.mytest.data.bean.request.PageSizeBean
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

    private val pageRequestBean = PageRequestBean(PageSizeBean(1))

    fun getContractList() {
        DataRepository.getInstance().getNetWorkData(VisitService::class.java) { service ->
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
}