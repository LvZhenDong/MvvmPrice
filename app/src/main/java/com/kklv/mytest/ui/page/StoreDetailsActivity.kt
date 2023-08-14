package com.kklv.mytest.ui.page

import android.os.Bundle
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.domain.request.StoreDetailsRequester
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import com.kunminx.architecture.ui.state.State

class StoreDetailsActivity : BaseActivity() {
    private lateinit var mStates: StoreDetailsActivityStates
    private lateinit var mStoreDetailsRequester: StoreDetailsRequester

    override fun initViewModel() {
        mStates = getActivityScopeViewModel(StoreDetailsActivityStates::class.java)
        mStoreDetailsRequester = getActivityScopeViewModel(StoreDetailsRequester::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_store_details, BR.vm, mStates)
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(mStoreDetailsRequester)

        mStoreDetailsRequester.getStoreDetailsInfoResult().observe(this@StoreDetailsActivity) {
            mStates.dataInfo.set(it.result)
            if(it.result.is_mark)mStates.collectionRes.set(R.drawable.iv_store_collect)
        }

        mStoreDetailsRequester.getDetailsInfo()
    }

    inner class ClickProxy {
        fun clickExpandArrow() {
            mStates.isExpanded.set(!(mStates.isExpanded.get() ?: false))
        }

        fun back(){
            finish()
        }
    }


    class StoreDetailsActivityStates : StateHolder() {
        val dataInfo: State<StoreDetailsBean> = State(StoreDetailsBean())

        val isExpanded: State<Boolean> = State(false)

        val collectionRes:State<Int> = State(R.drawable.iv_store_collect_white)
    }
}