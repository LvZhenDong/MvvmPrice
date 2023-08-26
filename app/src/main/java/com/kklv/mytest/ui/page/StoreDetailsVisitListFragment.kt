package com.kklv.mytest.ui.page

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.paging.LoadState
import androidx.paging.liveData
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.data.bean.VisitBean
import com.kklv.mytest.data.paging.HeaderFooterAdapter
import com.kklv.mytest.databinding.FragmentStoreDetailsVisitListBinding
import com.kklv.mytest.databinding.ItemVisitBinding
import com.kklv.mytest.domain.request.VisitRequester
import com.kklv.mytest.ui.view.adapter.BasePagingListAdapter
import com.kunminx.architecture.ui.page.BaseFragment
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import com.kunminx.architecture.ui.state.State
import kotlinx.coroutines.flow.collectLatest

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

    private lateinit var mAdapter: BasePagingListAdapter<VisitBean, ItemVisitBinding>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = BasePagingListAdapter(R.layout.item_visit, { _, data, bind ->
            bind.data = data
        }, { oldItem, newItem ->
            return@BasePagingListAdapter oldItem.visit_id == newItem.visit_id
        }, { oldItem, newItem ->
            return@BasePagingListAdapter oldItem == newItem
        })
        binding.rvVisit.adapter = mAdapter.withLoadStateFooter(HeaderFooterAdapter())

        mRequest.pagingData.observe(viewLifecycleOwner) { pagingData ->
            mAdapter.submitData(lifecycle, pagingData)
        }
    }

    override fun onResume() {
        super.onResume()
        if (mStates.isInitLoad.get() == false) {
            mStates.isInitLoad.set(true)

        }
    }

    class StoreDetailsVisitListFragmentStates : StateHolder() {
        val isInitLoad: State<Boolean> = State(false)
    }
}