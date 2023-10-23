package com.kklv.mytest

import android.app.Application
import com.drake.statelayout.StateConfig
import com.kklv.common.R
import com.kunminx.architecture.ui.view.LestOnceAnimationStateChangedHandler
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @author lvzhendong
 * @data 2023/8/29
 * @description
 */
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
//
////用户信息持久化数据
//val Context.userInfoDataStore by preferencesDataStore(name = "userInfo")
