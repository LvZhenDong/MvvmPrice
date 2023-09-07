package com.kklv.mytest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @author lvzhendong
 * @data 2023/8/29
 * @description
 */
@HiltAndroidApp
class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()

    }
}
