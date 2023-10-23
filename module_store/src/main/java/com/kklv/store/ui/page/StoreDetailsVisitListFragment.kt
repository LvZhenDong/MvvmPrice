package com.kklv.store.ui.page

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager
import com.kklv.common.data.paging.FooterAdapter
import com.kklv.common.data.paging.HeaderAdapter
import com.kklv.common.ui.view.adapter.BasePagingListAdapter
import com.kklv.common.ui.view.adapter.BaseSimpleAdapter
import com.kklv.store.R
import com.kklv.store.BR
import com.kklv.store.data.bean.VisitBean
import com.kklv.store.data.bean.VisitTag
import com.kklv.store.databinding.FragmentStoreDetailsVisitListBinding
import com.kklv.store.databinding.ItemTagBinding
import com.kklv.store.databinding.ItemVisitBinding
import com.kklv.store.domain.request.VisitRequester
import com.kunminx.architecture.ui.page.BaseFragment
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import com.kunminx.architecture.ui.state.State
import com.kunminx.architecture.ui.view.MyItemDecorator
import com.kunminx.architecture.utils.ext.removeAllItemDecorations

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
        mRequest = getParentFragmentScopeViewModel(VisitRequester::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_store_details_visit_list, BR.vm, mStates)
    }

    private lateinit var mAdapter: BasePagingListAdapter<VisitBean, ItemVisitBinding>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = BasePagingListAdapter(R.layout.item_visit, { _, data, bind ->
            bind.data = data
            data.tags?.let { tags ->
                val adapter = BaseSimpleAdapter<VisitTag, ItemTagBinding>(
                    tags,
                    R.layout.item_tag
                ) { _, data, binding ->
                    binding.apply {
                        text = data.getTagText()
                    }
                }
                bind.rvTag.adapter = adapter
                val layoutManager = ChipsLayoutManager
                    .newBuilder(context)
                    .setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .build()
                bind.rvTag.removeAllItemDecorations()
                bind.rvTag.addItemDecoration(MyItemDecorator())
                bind.rvTag.layoutManager = layoutManager
            }

        }, { oldItem, newItem ->
            return@BasePagingListAdapter oldItem.visit_id == newItem.visit_id
        }, { oldItem, newItem ->
            return@BasePagingListAdapter oldItem == newItem
        })

        binding.rvVisit.adapter = ConcatAdapter(HeaderAdapter(), mAdapter.withLoadStateFooter(footer = FooterAdapter()))

        mRequest.pagingData.observe(viewLifecycleOwner) { pagingData ->
            mAdapter.submitData(lifecycle, pagingData)
        }

        mRequest.isNeedRefresh.observe(viewLifecycleOwner) { isNeedRefresh ->
            if (isNeedRefresh) {
                mAdapter.refresh()
            }
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