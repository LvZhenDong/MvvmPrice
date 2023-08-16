package com.kklv.mytest.data.interceptor

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.Locale


/**
 * Author:lvzhendong
 * Created:2023/8/13
 * Desc:
 */
class JdInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder: Request.Builder = original.newBuilder()
            .header("app-version", "8.7.6")
            .header("platform", "android")
            .header("device-type", "android")
            .header("device-version", "8.7.6")
            .header("brand", Build.BRAND.lowercase(Locale.getDefault()))
            .header("model", Build.MODEL.lowercase(Locale.getDefault()))
            .header("language-code","zh")
            .header("script-code", "Hans")
            .header("country-code", "CN")
            .header("time-zone", "GMT+08:00")
            .header("ac-token", "uat_1e94c0341c8346d893602f6ad3aaed49")
            .addHeader("User-Agent", "XpKLpLXgf9YDALcVeYr7vsI0")
            .addHeader("Cookie", "sid=uat_c32cd44a23e544859a8998e3379b6295;uid=5af2adaa2161c")
            .method(original.method, original.body)

        val request = builder.build()
        val response = chain.proceed(request)

        return response
    }

}