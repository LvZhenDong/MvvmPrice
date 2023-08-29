package com.kklv.mytest.data.repository

import com.kklv.mytest.data.api.APIs
import com.kklv.mytest.data.api.UserService
import com.kklv.mytest.data.api.VisitService
import com.kklv.mytest.data.bean.TokenBodyBean
import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.data.bean.base.BaseJdResponse
import com.kklv.mytest.data.bean.base.PageListBean
import com.kklv.mytest.data.bean.request.PageRequestBean
import com.kklv.mytest.data.bean.request.PageSizeBean
import com.kklv.mytest.data.exception.NetWorkException
import com.kklv.mytest.data.interceptor.JdInterceptor
import com.kklv.mytest.data.interceptor.RefreshTokenInterceptor
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.data.response.ResponseStatus
import com.kunminx.architecture.data.response.ResultSource
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Create by KunMinX at 19/10/29
 */
class DataRepository private constructor() {

    companion object {
        private val S_REQUEST_MANAGER = DataRepository()
        fun getInstance(): DataRepository {
            return S_REQUEST_MANAGER
        }
    }

    val retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(16, TimeUnit.SECONDS)
            .readTimeout(16, TimeUnit.SECONDS)
            .writeTimeout(16, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(JdInterceptor())
            .addInterceptor(RefreshTokenInterceptor())
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(APIs.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <S, T> getNetWorkObservableData(
        serviceClass: Class<S>,
        function: (S) -> Call<BaseJdResponse<T>>
    ): Observable<DataResult<T>> {
        return Observable.create { emitter ->
            val service = retrofit.create(serviceClass)
            val call = function(service)
            val response: Response<BaseJdResponse<T>>
            try {
                response = call.execute()
                if (response.body()?.isSuccess() == true) {
                    val responseStatus = ResponseStatus(
                        response.code().toString(),
                        response.isSuccessful,
                        ResultSource.NETWORK
                    )
                    emitter.onNext(DataResult(response.body()?.data, responseStatus))
                } else {
                    throw NetWorkException(response.body()?.message ?: "网络异常，请稍后重试")
                }
            } catch (e: IOException) {
                emitter.onNext(
                    DataResult(
                        null,
                        ResponseStatus(e.message, false, ResultSource.NETWORK)
                    )
                )
            }
        }
    }

    fun <S, T> getNetWorkData(
        serviceClass: Class<S>,
        function: (S) -> Call<BaseJdResponse<T>>
    ): DataResult<T> {
        val service = retrofit.create(serviceClass)
        val call = function(service)
        val response: Response<BaseJdResponse<T>>
        return try {
            response = call.execute()
            if (response.body()?.isSuccess() == true) {
                val responseStatus = ResponseStatus(
                    response.code().toString(),
                    response.isSuccessful,
                    ResultSource.NETWORK
                )
                DataResult(response.body()?.data, responseStatus)
            } else {
                throw NetWorkException(response.body()?.message ?: "网络异常，请稍后重试")
            }
        } catch (e: IOException) {
            DataResult(
                null,
                ResponseStatus(e.message, false, ResultSource.NETWORK)
            )

        }
    }

    fun refreshToken(map: Map<String, String>): TokenBodyBean? {
        val call = retrofit.create(UserService::class.java).refreshToken(map)
        val response: Response<TokenBodyBean>
        return try {
            response = call.execute()
            response.body()!!
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}
