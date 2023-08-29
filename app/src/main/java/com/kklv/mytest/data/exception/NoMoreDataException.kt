package com.kklv.mytest.data.exception

import okio.IOException

/**
 * @author lvzhendong
 * @data 2023/8/25
 * @description
 */
class NoMoreDataException(message:String = "没有更多数据了"):IOException(message)