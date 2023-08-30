package com.kklv.mytest.domain.request

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.kklv.mytest.data.api.StoreService
import com.kklv.mytest.data.bean.SchemaBean
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.data.repository.DataRepository
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.data.response.ResponseStatus
import com.kunminx.architecture.domain.message.MutableResult
import com.kunminx.architecture.domain.message.Result
import com.kunminx.architecture.domain.request.Requester
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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

    fun getDetailsInfoByCoroutineScope(storeId: String) {
        viewModelScope.launch {
            coroutineScope {
                val storeInfoJob = async {
                    DataRepository.getInstance().getNetWorkData(StoreService::class.java) { storeService ->
                        storeService.getStoreDetailsInfo(storeId, "30.539129", "104.054851")
                    }
                }

                val navBtn1Job = async {
                    DataRepository.getInstance().getNetWorkData(StoreService::class.java) { storeService ->
                        storeService.getStoreDetailsNavBtn1(storeId)
                    }
                }

                val navBtn2Job = async {
                    DataRepository.getInstance().getNetWorkData(StoreService::class.java) { storeService ->
                        storeService.getStoreDetailsNavBtn2(storeId)
                    }
                }

                val t1 = storeInfoJob.await()
                val t2 = navBtn1Job.await()
                val t3 = navBtn2Job.await()

                val dataResult =
                    if (t1.responseStatus.isSuccess.not() || t2.responseStatus.isSuccess.not() || t3.responseStatus.isSuccess.not()) {
                        DataResult(DetailsInfoNavBtn(null, null), ResponseStatus("网络异常，请稍后重试", false))
                    } else {
                        val totalNavList: ArrayList<SchemaBean> = t2.result.buttons
                        totalNavList.addAll(t3.result.buttons)
                        totalNavList.sortBy { item -> 20 - item.display_index }

                        DataResult(DetailsInfoNavBtn(t1.result, totalNavList))
                    }

                storeDetailsInfoResult.postValue(dataResult)
            }
        }
    }

    data class DetailsInfoNavBtn(
        val detailsInfo: StoreDetailsBean?,
        val navBtn: ArrayList<SchemaBean>?
    )
}