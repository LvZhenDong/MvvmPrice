package com.kklv.mytest.domain.request

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kklv.mytest.data.DataManager
import com.kklv.mytest.data.api.UserService
import com.kklv.mytest.data.bean.base.LoginResponse
import com.kklv.mytest.data.repository.DataRepository
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.domain.request.Requester
import com.kunminx.architecture.domain.result.MutableResult
import com.kunminx.architecture.domain.result.Result
import kotlinx.coroutines.launch

/**
 * @author lvzhendong
 * @data 2023/9/19
 * @description
 */
class LoginRequester : Requester() {
    val userName: MutableLiveData<String> = MutableLiveData()

    val password: MutableLiveData<String> = MutableLiveData()

    private val loginResult: MutableResult<DataResult<LoginResponse>> = MutableResult()

    fun getLoginResult(): Result<DataResult<LoginResponse>> = loginResult

    fun login() {
        viewModelScope.launch {
            val dataResult = DataRepository.getInstance().getNetWorkData(UserService::class.java) { userService ->
                val map = HashMap<String, Any>()
                map["email"] = userName.value ?: ""
                map["passwd"] = password.value ?: ""
                map["login_way"] = 2
                map["isTokenIntercept"] = false
                userService.login(map)
            }

            if (dataResult.isSuccess) {
                DataManager.getInstance().setAcToken(dataResult.result.ac_token)
                DataManager.getInstance().setRfToken(dataResult.result.rf_token)
                DataManager.getInstance().setUid(dataResult.result.user.user_id)
            }
            loginResult.value = dataResult
        }
    }
}