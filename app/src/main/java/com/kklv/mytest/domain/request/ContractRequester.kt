package com.kklv.mytest.domain.request

import androidx.lifecycle.DefaultLifecycleObserver
import com.kklv.mytest.data.api.StoreService
import com.kklv.mytest.data.bean.ContractBean
import com.kklv.mytest.data.bean.ContractListBean
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
class ContractRequester : Requester(), DefaultLifecycleObserver {

    private var mDisposable: Disposable? = null

    private val contractListResult: MutableResult<DataResult<ContractListBean>> = MutableResult()

    fun getContractListResult(): Result<DataResult<ContractListBean>> {
        return contractListResult
    }



    fun getContractList(storeId: String) {
        DataRepository.getInstance().getNetWorkObservableData(StoreService::class.java) { storeService ->
            storeService.getStoreContractList(storeId)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DataResult<ContractListBean>> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    mDisposable = null
                }

                override fun onNext(t: DataResult<ContractListBean>) {
                    contractListResult.postValue(t)
                }
            })
    }
}