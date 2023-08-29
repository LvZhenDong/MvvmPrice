package com.kklv.mytest.domain.request

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kklv.mytest.data.api.StoreService
import com.kklv.mytest.data.bean.SchemaBean
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.data.repository.DataRepository
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.data.response.ResponseStatus
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
    private val storeDetailsInfoResult: MutableResult<DataResult<DetailsInfoNavBtn>> = MutableResult()

    fun getStoreDetailsInfoResult(): Result<DataResult<DetailsInfoNavBtn>> {
        return storeDetailsInfoResult
    }

    private var mDisposable: Disposable? = null

    fun getDetailsInfo(storeId: String) {
        val observableInfo =
            DataRepository.getInstance().getNetWorkObservableData(StoreService::class.java) { service ->
                service.getStoreDetailsInfo(storeId, "30.539129", "104.054851")
            }.subscribeOn(Schedulers.io())

        val observableNavBtn1 =
            DataRepository.getInstance().getNetWorkObservableData(StoreService::class.java) { service ->
                service.getStoreDetailsNavBtn1(storeId)
            }.subscribeOn(Schedulers.io())

        val observableNavBtn2 =
            DataRepository.getInstance().getNetWorkObservableData(StoreService::class.java) { service ->
                service.getStoreDetailsNavBtn2(storeId)
            }.subscribeOn(Schedulers.io())

        Observable.zip(
            observableInfo,
            observableNavBtn1,
            observableNavBtn2
        ) { t1, t2, t3 ->
            if (t1.responseStatus.isSuccess.not() || t2.responseStatus.isSuccess.not() || t3.responseStatus.isSuccess.not()) {
                DataResult(DetailsInfoNavBtn(null, null), ResponseStatus("网络异常，请稍后重试", false))
            } else {
                val totalNavList: ArrayList<SchemaBean> = t2.result.buttons
                totalNavList.addAll(t3.result.buttons)
                totalNavList.sortBy { item -> 20 - item.display_index }

                DataResult(DetailsInfoNavBtn(t1.result, totalNavList))
            }

        }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DataResult<DetailsInfoNavBtn>> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onError(e: Throwable) {
                    Log.i("kklv", "error")
                }

                override fun onComplete() {
                    mDisposable = null
                }

                override fun onNext(t: DataResult<DetailsInfoNavBtn>) {
                    storeDetailsInfoResult.postValue(t)
                }

            })
    }

    data class DetailsInfoNavBtn(
        val detailsInfo: StoreDetailsBean?,
        val navBtn: ArrayList<SchemaBean>?
    )

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        mDisposable?.dispose()
    }
}