package com.kklv.store

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.drake.statelayout.BuildConfig
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

        // 这两行必须写在init之前，否则这些配置在init过程中将无效
//        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
//        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
        initSmartRefreshLayout()
    }

    private fun initSmartRefreshLayout(){
        //设置下拉刷新风格
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.white, R.color.color_666666)
            ClassicsHeader(context)
        }
        //设置StateLayout的状态变更处理器
        StateConfig.stateChangedHandler = LestOnceAnimationStateChangedHandler()
    }
}
