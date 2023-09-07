package com.kunminx.architecture.data.response

class ResponseStatus(
    val isSuccess: Boolean = true,
    val responseCode: String = "网络异常，请稍后重试",
    val source: ResultSource = ResultSource.NETWORK
) {


}
