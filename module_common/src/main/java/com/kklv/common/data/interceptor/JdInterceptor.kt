package com.kklv.common.data.interceptor

import android.os.Build
import com.kklv.common.data.DataManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.Locale


/**
 * Author:lvzhendong
 * Created:2023/8/13
 * Desc:
 */
class JdInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder: Request.Builder = original.newBuilder()
            .header("app-version", "8.7.6")
            .header("platform", "android")
            .header("device-type", "android")
            .header("device-version", "8.7.6")
            .header("brand", Build.BRAND.lowercase(Locale.getDefault()))
            .header("model", Build.MODEL.lowercase(Locale.getDefault()))
            .header("language-code", "zh")
            .header("script-code", "Hans")
            .header("country-code", "CN")
            .header("time-zone", "GMT+08:00")
            .header("ac-token", DataManager.getInstance().getAcToken())
            .addHeader("User-Agent", "XpKLpLXgf9YDALcVeYr7vsI0")
            .addHeader(
                "Cookie",
                "sid=${DataManager.getInstance().getAcToken()};uid=${DataManager.getInstance().getUid()}"
            )
            .method(original.method, original.body)

        val request = builder.build()

        return chain.proceed(request)
    }

}