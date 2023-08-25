package com.kklv.mytest.ui.page

import android.os.Bundle
import android.view.View
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.data.bean.ContractBean
import com.kklv.mytest.databinding.FragmentStoreDetailsContractBinding
import com.kklv.mytest.databinding.ItemContractBinding
import com.kklv.mytest.domain.request.ContractRequester
import com.kklv.mytest.ui.view.adapter.BaseResultDataAdapter
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

    private lateinit var mAdapter: BaseResultDataAdapter<ContractBean, ItemContractBinding>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = BaseResultDataAdapter(
            mRequester.getContractListResult(),
            this,
            R.layout.item_contract
        ) { pos, data, binding ->
            binding.apply {
                this.data = data
            }
        }
        binding.rvContract.adapter = mAdapter
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