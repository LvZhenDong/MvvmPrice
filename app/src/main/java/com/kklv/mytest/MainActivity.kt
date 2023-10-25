package com.kklv.mytest

import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.kklv.common.data.DataManager
import com.kklv.common.domain.message.PageMessenger
import com.kklv.mytest.databinding.ActivityMainBinding
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import com.kunminx.architecture.ui.state.State

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var mStates: MainActivityStates
    private lateinit var mMessenger: PageMessenger

    override fun initViewModel() {
        mStates = getActivityScopeViewModel(MainActivityStates::class.java)
        mMessenger = getApplicationScopeViewModel(PageMessenger::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_main, BR.mainVm, mStates).addBindingParam(BR.click, ClickProxy())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMessenger.output(this) { message ->
            Log.i("kklv", "message id:${message.eventId}")
        }
    }

    inner class ClickProxy {
        fun goToStoreDetailsActivity() {

            if (DataManager.getInstance().isNeedLogin()) {
                ARouter.getInstance().build("/login/loginActivity").navigation()
            } else {
                ARouter.getInstance().build("/store/storeDetailsActivity").navigation()
            }

        }
    }

    class MainActivityStates : StateHolder() {
        val isShow: State<Boolean> = State(true)
        val showText: State<String> = State("默认文案")
    }
}