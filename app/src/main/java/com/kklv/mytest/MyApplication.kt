package com.kklv.mytest

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.drake.statelayout.StateConfig
import com.kunminx.architecture.ui.view.LestOnceAnimationStateChangedHandler
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.HiltAndroidApp

/**
 * @author lvzhendong
 * @data 2023/8/29
 * @description
 */
@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //设置下拉刷新风格
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.white, R.color.color_666666)
            ClassicsHeader(context)
        }
        //设置StateLayout的状态变更处理器
        StateConfig.stateChangedHandler = LestOnceAnimationStateChangedHandler()
    }
}

//用户信息持久化数据
val Context.userInfoDataStore by preferencesDataStore(name = "userInfo")
