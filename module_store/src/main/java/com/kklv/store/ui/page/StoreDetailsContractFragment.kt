package com.kklv.store.ui.page

import android.os.Bundle
import android.view.View
import com.drake.statelayout.Status
import com.kklv.common.ui.view.adapter.BaseSimpleAdapter
import com.kklv.store.BR
import com.kklv.store.R
import com.kklv.store.data.bean.ContractBean
import com.kklv.store.databinding.FragmentStoreDetailsContractBinding
import com.kklv.store.databinding.ItemContractBinding
import com.kklv.store.domain.request.ContractRequester
import com.kunminx.architecture.ui.page.BaseFragment
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import com.kunminx.architecture.ui.state.State

/**
 * Author:lvzhendong
 * Created:2023/8/16
 * Desc:
 */
class StoreDetailsContractFragment : BaseFragment<FragmentStoreDetailsContractBinding>() {

    companion object {
        private const val ARG_STORE_ID = "storeId"
        fun getInstance(storeId: String): StoreDetailsContractFragment {
            val fragment = StoreDetailsContractFragment()
            val args = Bundle()
            args.putString(ARG_STORE_ID, storeId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mStates: StoreDetailsDataFragmentStates
    private lateinit var mRequester: ContractRequester
    override fun initViewModel() {
        mStates = getFragmentScopeViewModel(StoreDetailsDataFragmentStates::class.java)
        mRequester = getFragmentScopeViewModel(ContractRequester::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_store_details_contract, BR.vm, mStates)
    }

    private lateinit var mAdapter: BaseSimpleAdapter<ContractBean, ItemContractBinding>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        mRequester.getContractListResult().observe(viewLifecycleOwner) { resultData ->
            if (resultData.isSuccess && resultData.result.list.isNullOrEmpty().not()) {
                binding.stateLayout.showContent()
                mAdapter = BaseSimpleAdapter(
                    resultData.result.list!!,
                    R.layout.item_contract
                ) { _, data, binding ->
                    binding.apply {
                        this.data = data
                    }
                }
                binding.rvContract.adapter = mAdapter
            } else if (resultData.status == Status.EMPTY) {
                binding.stateLayout.showEmpty()
            }
        }
    }

    private fun initView(){
        binding.stateLayout.apply {
            emptyLayout = com.kklv.common.R.layout.layout_empty_list
        }
    }

    override fun onResume() {
        super.onResume()
        if (mStates.isInitDataLoad.get() == false) {
            mStates.isInitDataLoad.set(true)
            mRequester.getContractList(arguments?.getString(ARG_STORE_ID) ?: "")
        }
    }

    class StoreDetailsDataFragmentStates : StateHolder() {
        val isInitDataLoad: State<Boolean> = State(false)
    }
}