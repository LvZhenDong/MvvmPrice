package com.kklv.mytest

import android.app.Application
import android.content.Context
import com.kklv.mytest.utils.SingleContainer

/**
 * @author lvzhendong
 * @data 2023/8/29
 * @description
 */
class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        SingleContainer.init(this)
    }
}
