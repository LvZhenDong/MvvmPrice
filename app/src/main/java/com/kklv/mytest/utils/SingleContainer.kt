package com.kklv.mytest.utils

import android.content.Context
import android.os.Handler
import android.os.Looper

class SingleContainer {
    var mainHandler: Handler? = null
    var context: Context? = null

    companion object {
        private var INSTANCE = SingleContainer()

        fun getMainHandler(): Handler? {
            if (INSTANCE.mainHandler == null) {
                INSTANCE.mainHandler = Handler(Looper.getMainLooper())
            }
            return INSTANCE.mainHandler
        }

        fun init(context: Context?) {
            if (context != null && INSTANCE.context == null) {
                INSTANCE.context = context.applicationContext
            }
        }

        val applicationContext: Context
            get() {
                checkNotNull(INSTANCE.context) { "请先调用 init(context)方法初始化" }
                return INSTANCE.context!!
            }
    }
}


// 扩展函数，用于全局获取Application的Context
fun getAppContext(): Context {
    return SingleContainer.applicationContext
}