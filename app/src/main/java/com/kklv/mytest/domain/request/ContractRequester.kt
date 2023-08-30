package com.kklv.mytest.domain.request

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author lvzhendong
 * @data 2023/8/22
 * @description
 */
class ContractRequester : Requester(), DefaultLifecycleObserver {

    private val contractListResult: MutableResult<DataResult<ContractListBean>> = MutableResult()

    fun getContractListResult(): Result<DataResult<ContractListBean>> {
        return contractListResult
    }

    fun getContractList(storeId: String) {
        viewModelScope.launch {
            val dataResult = DataRepository.getInstance().getNetWorkData(StoreService::class.java) { storeService ->
                storeService.getStoreContractList(storeId)
            }
            contractListResult.postValue(dataResult)
        }
    }
}