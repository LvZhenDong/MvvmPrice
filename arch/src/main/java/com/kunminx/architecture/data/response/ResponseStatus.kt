package com.kunminx.architecture.data.response

import com.drake.statelayout.Status

class ResponseStatus(
    val status:Status = Status.CONTENT,
    val isSuccess: Boolean = true,
    val responseCode: String = "网络异常，请稍后重试",
    val source: ResultSource = ResultSource.NETWORK
) {


}
