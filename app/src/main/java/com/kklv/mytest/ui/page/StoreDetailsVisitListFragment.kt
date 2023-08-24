package com.kklv.mytest.ui.page

import android.os.Bundle
import android.view.View
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.databinding.FragmentStoreDetailsVisitListBinding
import com.kklv.mytest.databinding.ItemVisitBinding
import com.kklv.mytest.domain.request.VisitRequester
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
class StoreDetailsVisitListFragment : BaseFragment<FragmentStoreDetailsVisitListBinding>() {

    companion object {
        private const val ARG_STORE_ID = "storeId"
        fun getInstance(storeId: String): StoreDetailsVisitListFragment {
            val fragment = StoreDetailsVisitListFragment()
            val args = Bundle()
            args.putString(ARG_STORE_ID, storeId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mStates: StoreDetailsVisitListFragmentStates
    private lateinit var mRequest: VisitRequester
    override fun initViewModel() {
        mStates = getFragmentScopeViewModel(StoreDetailsVisitListFragmentStates::class.java)
        mRequest = getFragmentScopeViewModel(VisitRequester::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_store_details_visit_list, BR.vm, mStates)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvVisit.adapter = BaseResultDataAdapter<VisitBean, ItemVisitBinding>(
            mRequest.getVisitListResult(), this,
            R.layout.item_visit
        ) { pos, data, binding ->
            binding.apply {
                this.data = data
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mStates.isInitLoad.get() == false) {
            mStates.isInitLoad.set(true)
            mRequest.getContractList()
        }
    }

    class StoreDetailsVisitListFragmentStates : StateHolder() {
        val isInitLoad: State<Boolean> = State(false)
    }
}