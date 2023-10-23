package com.kklv.common.data.exception

import okio.IOException

/**
 * @author lvzhendong
 * @data 2023/8/29
 * @description
 */
class NetWorkException(message:String = "网络异常，请稍后重试"):IOException(message)