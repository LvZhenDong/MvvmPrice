package com.kklv.store.ui.page

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kklv.store.R
import com.kklv.store.BR
import com.kklv.store.databinding.FragmentStoreDetailsDataBinding
import com.kklv.store.domain.request.StoreDetailsRequester
import com.kunminx.architecture.ui.page.BaseFragment
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Author:lvzhendong
 * Created:2023/8/16
 * Desc:
 */
class StoreDetailsDataFragment : BaseFragment<FragmentStoreDetailsDataBinding>() {

    companion object {
        private const val ARG_STORE_ID = "storeId"
        fun getInstance(storeId: String): StoreDetailsDataFragment {
            val fragment = StoreDetailsDataFragment()
            val args = Bundle()
            args.putString(ARG_STORE_ID, storeId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mStates: StoreDetailsDataFragmentStates
    private lateinit var mStoreDetailsRequester: StoreDetailsRequester
    override fun initViewModel() {
        mStates = getFragmentScopeViewModel(StoreDetailsDataFragmentStates::class.java)
        mStoreDetailsRequester = getActivityScopeViewModel(StoreDetailsRequester::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_store_details_data, BR.vm, mStates)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mStoreDetailsRequester.getStoreStatResult().observeSticky(viewLifecycleOwner) {
//            if (it.responseStatus.isSuccess) {
//                mStates.statDataDesc.set(it.result.description)
//                mStates.statData.set(it.result.device_data)
//            }
//        }

        //与上面的效果一样的
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mStoreDetailsRequester.storeStatFlow.collectLatest {
                    if (it.isSuccess) {
                        binding.statDataDesc = it.result.description
                        binding.statData = it.result.device_data
                    }
                }
            }
        }
    }

    class StoreDetailsDataFragmentStates : StateHolder() {
    }
}