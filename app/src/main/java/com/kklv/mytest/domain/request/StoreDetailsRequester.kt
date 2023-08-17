package com.kklv.mytest.domain.request

import androidx.lifecycle.DefaultLifecycleObserver
import com.kklv.mytest.data.api.StoreService
import com.kklv.mytest.data.bean.SchemaBean
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.data.repository.DataRepository
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.domain.message.MutableResult
import com.kunminx.architecture.domain.message.Result
import com.kunminx.architecture.domain.request.Requester
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Author:lvzhendong
 * Created:2023/8/12
 * Desc:
 */
class StoreDetailsRequester : Requester(), DefaultLifecycleObserver {
    private val storeDetailsInfoResult: MutableResult<StoreDetailsBean> = MutableResult()

    private val navBtnsResult: MutableResult<ArrayList<SchemaBean>> = MutableResult()

    fun getStoreDetailsInfoResult(): Result<StoreDetailsBean> {
        return storeDetailsInfoResult
    }

    fun getNavBtnsResult(): Result<ArrayList<SchemaBean>> {
        return navBtnsResult
    }

    private var mDisposable: Disposable? = null

    fun getDetailsInfo(storeId:String) {
        val observableInfo = DataRepository.getInstance().getNetWorkData(StoreService::class.java) { service ->
            service.getStoreDetailsInfo(storeId, "30.539129", "104.054851")
        }.subscribeOn(Schedulers.io())

        val observableNavBtn1 = DataRepository.getInstance().getNetWorkData(StoreService::class.java) { service ->
            service.getStoreDetailsNavBtn1(storeId)
        }.subscribeOn(Schedulers.io())

        val observableNavBtn2 = DataRepository.getInstance().getNetWorkData(StoreService::class.java) { service ->
            service.getStoreDetailsNavBtn2(storeId)
        }.subscribeOn(Schedulers.io())

        Observable.zip(
            observableInfo,
            observableNavBtn1,
            observableNavBtn2
        ) { t1, t2, t3 ->
            val totalNavList: ArrayList<SchemaBean> = t2.result.buttons
            totalNavList.addAll(t3.result.buttons)
            totalNavList.sortBy { item -> 20 - item.display_index }

            DetailsInfoNavBtn(t1, DataResult(totalNavList))
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DetailsInfoNavBtn> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    mDisposable = null
                }

                override fun onNext(t: DetailsInfoNavBtn) {
                    storeDetailsInfoResult.postValue(t.detailsInfo.result)
                    navBtnsResult.postValue(t.navBtn.result)
                }

            })
    }

    data class DetailsInfoNavBtn(
        val detailsInfo: DataResult<StoreDetailsBean>,
        val navBtn: DataResult<ArrayList<SchemaBean>>
    )
}