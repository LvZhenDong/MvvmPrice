package com.kklv.mytest.domain.request

import androidx.lifecycle.viewModelScope
import com.kklv.mytest.data.api.StoreService
import com.kklv.mytest.data.bean.ContractListBean
import com.kklv.mytest.data.repository.DataRepository
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.domain.message.MutableResult
import com.kunminx.architecture.domain.message.Result
import com.kunminx.architecture.domain.request.Requester
import kotlinx.coroutines.launch

/**
 * @author lvzhendong
 * @data 2023/8/22
 * @description
 */
class ContractRequester : Requester() {

    private val contractListResult: MutableResult<DataResult<ContractListBean>> = MutableResult()

    fun getContractListResult(): Result<DataResult<ContractListBean>> {
        return contractListResult
    }

    fun getContractList(storeId: String) {
        viewModelScope.launch {
            val dataResult = DataRepository.getInstance().getNetWorkData(StoreService::class.java) { storeService ->
                storeService.getStoreContractList(storeId)
            }
            contractListResult.value = dataResult
        }
    }
}