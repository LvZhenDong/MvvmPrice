package com.kklv.mytest.domain.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.drake.statelayout.Status
import com.kklv.mytest.data.api.StoreService
import com.kklv.mytest.data.bean.SchemaBean
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.data.bean.StoreDetailsGraphDeviceData
import com.kklv.mytest.data.bean.StoreDetailsStatBean
import com.kklv.mytest.data.repository.DataRepository
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.data.response.ResponseStatus
import com.kunminx.architecture.domain.message.MutableResult
import com.kunminx.architecture.domain.message.Result
import com.kunminx.architecture.domain.request.Requester
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Author:lvzhendong
 * Created:2023/8/12
 * Desc:
 */
class StoreDetailsRequester : Requester() {
    private val storeDetailsInfoResult: MutableLiveData<DataResult<DetailsInfoNavBtn>> = MutableLiveData(DataResult(Status.LOADING))

    fun getStoreDetailsInfoResult(): LiveData<DataResult<DetailsInfoNavBtn>> {
        return storeDetailsInfoResult
    }

    private val _storeStatFlow: MutableStateFlow<DataResult<StoreDetailsStatBean>> = MutableStateFlow(
        DataResult(
            StoreDetailsStatBean(
                description = "",
                device_data = StoreDetailsGraphDeviceData()
            )
        )
    )

    val storeStatFlow: StateFlow<DataResult<StoreDetailsStatBean>> = _storeStatFlow.asStateFlow()

    private val collectResult: MutableResult<DataResult<Boolean>> = MutableResult()

    fun getCollectResult(): Result<DataResult<Boolean>> {
        return collectResult
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

                val statDataJob = async {
                    DataRepository.getInstance().getNetWorkData(StoreService::class.java) { storeService ->
                        storeService.getStoreDetailsStatInfo(storeId, "today")
                    }
                }

                val t1 = storeInfoJob.await()
                val t2 = navBtn1Job.await()
                val t3 = navBtn2Job.await()
                val t4 = statDataJob.await()

                val dataResult =
                    if (t1.isSuccess.not() || t2.isSuccess.not() || t3.isSuccess.not()) {
                        DataResult(DetailsInfoNavBtn(null, null), Status.ERROR)
                    } else {
                        val totalNavList: ArrayList<SchemaBean> = t2.result.buttons
                        totalNavList.addAll(t3.result.buttons)
                        totalNavList.sortBy { item -> 20 - item.display_index }

                        collectResult.value = DataResult(t1.result.is_mark)

                        DataResult(DetailsInfoNavBtn(t1.result, totalNavList))
                    }

                storeDetailsInfoResult.value = dataResult

                _storeStatFlow.value = t4
            }
        }
    }

    fun collect(storeId: String, isCollected: Boolean) {
        viewModelScope.launch {
            val result = DataRepository.getInstance().getNetWorkData(StoreService::class.java) { storeService ->
                val map = HashMap<String, Any>()
                map["store_id"] = storeId
                map["collect"] = !isCollected
                map["isTokenIntercept"] = true
                storeService.collectStore(map)
            }
            if (result.isSuccess) {
                collectResult.value = DataResult(!isCollected)
            } else {
                collectResult.value = DataResult(isCollected, Status.ERROR)
            }

        }
    }

    data class DetailsInfoNavBtn(
        val detailsInfo: StoreDetailsBean?,
        val navBtn: ArrayList<SchemaBean>?
    )
}