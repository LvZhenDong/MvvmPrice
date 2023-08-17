package com.kklv.mytest.data.interceptor

import com.google.gson.Gson
import com.kklv.mytest.data.DataManager
import com.kklv.mytest.data.bean.TokenBean
import com.kklv.mytest.data.repository.DataRepository
import com.kklv.mytest.domain.JdConstant
import com.kklv.mytest.domain.JdConstant.Companion.CODE_SUC
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

/**
 * @author lvzhendong
 * date:2023/8/16
 * desc:acToken后自动刷新acToken
 */
class RefreshTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder: Request.Builder = original.newBuilder()
            .method(original.method, original.body)
        val request = builder.build()
        val response = chain.proceed(request)

        val body = response.peekBody(Long.MAX_VALUE).string()
        try {
            if (response.isSuccessful) {
                if (body.contains("code")) {
                    val jsonObject = JSONObject(body)
                    val code = jsonObject.optInt("code")
                    val message = jsonObject.optString("message")
                    if (code == JdConstant.ERROR_CODE_INVALID_TOKEN && message == JdConstant.ERROR_MSG_INVALID_TOKEN) {
                        //token过期，刷新token
                        refreshToken()?.let {
                            return chain.proceed(createNewRequestByNewToken(request, it))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    private fun refreshToken(): String? {
        val map = HashMap<String, String>()
        map["rf_token"] = DataManager.getInstance().getRfToken()
        val result = DataRepository.getInstance().refreshToken(map)
        return if (result?.code == CODE_SUC) {
            val tokenBean = Gson().fromJson(result.data, TokenBean::class.java)
            DataManager.getInstance().setAcToken(tokenBean.ac_token)
            DataManager.getInstance().setRfToken(tokenBean.rf_token)
            tokenBean.ac_token
        } else {
            null
        }
    }

    private fun createNewRequestByNewToken(original: Request, newToken: String): Request {
        return original.newBuilder().header("ac-token", newToken).build()
    }
}