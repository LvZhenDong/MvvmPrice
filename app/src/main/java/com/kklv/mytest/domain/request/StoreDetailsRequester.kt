package com.kklv.mytest.domain.request

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import com.kklv.mytest.data.bean.BaseJdResponse
import com.kklv.mytest.data.bean.StoreDetailsBean
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
 * Author:lvzhendong
 * Created:2023/8/12
 * Desc:
 */
class StoreDetailsRequester:Requester(),DefaultLifecycleObserver {
    private val storeDetailsInfoResult: MutableResult<DataResult<StoreDetailsBean>> = MutableResult()

    fun getStoreDetailsInfoResult():Result<DataResult<StoreDetailsBean>>{
        return storeDetailsInfoResult
    }

    private var mDisposable: Disposable? = null

    fun getDetailsInfo(){
        DataRepository.getInstance().getStoreDetailsInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DataResult<StoreDetailsBean>> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    mDisposable = null
                }

                override fun onNext(t: DataResult<StoreDetailsBean>) {
                    Log.i("kklv","data:${t.result.store_name}")
                }

            })
    }
}