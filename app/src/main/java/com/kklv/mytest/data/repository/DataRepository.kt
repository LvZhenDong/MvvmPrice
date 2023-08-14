package com.kklv.mytest.data.repository


import android.util.Log
import com.kklv.mytest.data.api.APIs
import com.kklv.mytest.data.api.StoreService
import com.kklv.mytest.data.bean.BaseJdResponse
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.data.interceptor.JdInterceptor
import com.kunminx.architecture.data.response.DataResult
import com.kunminx.architecture.data.response.ResponseStatus
import com.kunminx.architecture.data.response.ResultSource
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    private val retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.SECONDS)
            .readTimeout(8, TimeUnit.SECONDS)
            .writeTimeout(8, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(JdInterceptor())
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(APIs.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getStoreDetailsInfo(): Observable<DataResult<StoreDetailsBean>> {
        return Observable.create { emitter ->
            val call = retrofit.create(StoreService::class.java).getStoreDetailsInfo("ed7e9adf-bce9-4120-aa89-61f807c29f4b","30.539129","104.054851")
            val response: Response<BaseJdResponse<StoreDetailsBean>>
            try {
                response = call.execute()
                val responseStatus = ResponseStatus(
                    response.code().toString(),
                    response.isSuccessful,
                    ResultSource.NETWORK
                )
                emitter.onNext(DataResult(response.body()?.data, responseStatus))
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
}
