package com.kklv.mytest.ui.page

import android.os.Bundle
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.databinding.FragmentStoreDetailsDataBinding
import com.kunminx.architecture.ui.page.BaseFragment
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder

/**
 * Author:lvzhendong
 * Created:2023/8/16
 * Desc:
 */
class StoreDetailsDataFragment : BaseFragment<FragmentStoreDetailsDataBinding>() {

    companion object{
        private const val ARG_STORE_ID = "storeId"
        fun getInstance(storeId:String):StoreDetailsDataFragment{
            val fragment = StoreDetailsDataFragment()
            val args = Bundle()
            args.putString(ARG_STORE_ID, storeId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mStates: StoreDetailsDataFragmentStates
    override fun initViewModel() {
        mStates = getActivityScopeViewModel(StoreDetailsDataFragmentStates::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_store_details_data, BR.vm, mStates)

    }

    class StoreDetailsDataFragmentStates : StateHolder() {
    }
}