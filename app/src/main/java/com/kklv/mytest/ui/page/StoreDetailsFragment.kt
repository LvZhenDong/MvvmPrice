package com.kklv.mytest.ui.page

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bestbrand.lib_skeleton.skeleton.ViewSkeletonScreen
import com.bestbrand.lib_skeleton.skeleton.buildSkeleton
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.data.bean.SchemaBean
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.databinding.FragmentStoreDetailsBinding
import com.kklv.mytest.databinding.ItemStoreNavigationBinding
import com.kklv.mytest.databinding.ItemStoreTagBinding
import com.kklv.mytest.domain.request.StoreDetailsRequester
import com.kklv.mytest.domain.request.VisitRequester
import com.kklv.mytest.ui.view.adapter.BaseSimpleAdapter
import com.kunminx.architecture.ui.page.BaseFragment
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import com.kunminx.architecture.ui.state.State
import com.kunminx.architecture.utils.ext.addLifecycleOnOffsetChangedListener
import com.kunminx.architecture.utils.ext.drawableLeft
import com.kunminx.architecture.utils.ext.toast
import kotlin.math.abs

class StoreDetailsFragment : BaseFragment<FragmentStoreDetailsBinding>() {

    companion object {
        private const val COLLAPSE_RATE = 0.1F
    }

    private lateinit var mStates: StoreDetailsFragmentStates
    private lateinit var mStoreDetailsRequester: StoreDetailsRequester

    private lateinit var mVisitRequester: VisitRequester

    private lateinit var mSkeleton: ViewSkeletonScreen

    override fun initViewModel() {
        mStates = getFragmentScopeViewModel(StoreDetailsFragmentStates::class.java)
        mStoreDetailsRequester = getFragmentScopeViewModel(StoreDetailsRequester::class.java)
        mVisitRequester = getFragmentScopeViewModel(VisitRequester::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_store_details, BR.vm, mStates)
            .addBindingParam(BR.click, ClickProxy())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(mStoreDetailsRequester)

        mStoreDetailsRequester.getStoreDetailsInfoResult().observe(viewLifecycleOwner) {
            if (this::mSkeleton.isInitialized && mSkeleton.isShow) hideSkeletonAndInitView()
            else initView()

            if (binding.refreshLayout.isRefreshing) binding.refreshLayout.finishRefresh()

            if (it.responseStatus.isSuccess) {
                it.result.detailsInfo?.let { storeDetailsBean ->
                    binding.infoData = storeDetailsBean

                    binding.tvContact.drawableLeft(if (storeDetailsBean.telephone == null) null else R.drawable.icon_contact)
                    storeDetailsBean.special_tags?.let { tags -> tagAdapter.setData(tags) }
                }

                it.result.navBtn?.let { list ->
                    binding.rvNavigation.adapter = BaseSimpleAdapter<SchemaBean, ItemStoreNavigationBinding>(
                        list,
                        R.layout.item_store_navigation
                    ) { _, data, binding ->
                        binding.apply {
                            imgUrl = data.icon
                            btnText = data.value
                        }
                    }
                }
            } else {
                it.responseStatus.responseCode.toast()
            }
        }

        mStoreDetailsRequester.getCollectResult().observe(viewLifecycleOwner) {
            if (it.responseStatus.isSuccess) {
                setCollectImg(mStates.isCollapsed.value ?: false, it.result)
            } else {
                it.responseStatus.responseCode.toast()
            }
        }

        mStates.isCollapsed.observe(viewLifecycleOwner) { isCollapsed ->
            binding.ivBack.setImageResource(if (isCollapsed) R.drawable.icon_store_back_black else R.drawable.icon_store_back)
            setCollectImg(isCollapsed, mStoreDetailsRequester.getCollectResult().value?.result ?: false)
        }

        //是否需要加载数据
        if (mStoreDetailsRequester.getStoreDetailsInfoResult().value == null) {
            mSkeleton = buildSkeleton(binding.refreshLayout, R.layout.skeleton_activity_store_details)
            refresh()
        }
    }

    private fun refresh(isNeedRefreshChild: Boolean = false) {
        mStoreDetailsRequester.getDetailsInfoByCoroutineScope(mStates.uuid.get() ?: "")
        if (isNeedRefreshChild) {
            mVisitRequester.isNeedRefresh.value = true
        }
    }

    private fun setCollectImg(isCollapsed: Boolean, isCollected: Boolean) {
        binding.ivCollect.setImageResource(
            if (isCollected) R.drawable.iv_store_collect
            else if (isCollapsed) R.drawable.iv_store_collect_black
            else R.drawable.iv_store_collect_white
        )
    }

    private fun hideSkeletonAndInitView() {
        mSkeleton.hide()
        initView()
    }

    private lateinit var tagAdapter: BaseSimpleAdapter<String, ItemStoreTagBinding>

    private fun initView() {
        initTab()

        tagAdapter = BaseSimpleAdapter(
            arrayListOf(),
            R.layout.item_store_tag
        ) { _, data, binding ->
            binding.apply {
                imgUrl = data
            }
        }
        binding.rvStoreTags.adapter = tagAdapter
        binding.refreshLayout.setOnRefreshListener {
            refresh(true)
        }

        binding.appBarLayout.addLifecycleOnOffsetChangedListener(viewLifecycleOwner) { appBarLayout, verticalOffset ->
            // 获取CollapsingToolbarLayout的总高度
            val totalScrollRange = appBarLayout.totalScrollRange

            // 计算折叠进度
            val collapsePercentage = (abs(verticalOffset).toFloat() / totalScrollRange.toFloat()).coerceIn(0f, 1f)
            binding.bgTop.alpha = collapsePercentage

            mStates.isCollapsed.value = collapsePercentage > COLLAPSE_RATE
        }
    }

    private fun initTab() {
        val fragments = arrayListOf(
            StoreDetailsDataFragment.getInstance(mStates.uuid.get() ?: ""),
            StoreDetailsVisitListFragment.getInstance(mStates.uuid.get() ?: ""),
            StoreDetailsContractFragment.getInstance(mStates.uuid.get() ?: "")
        )
        val mPagerAdapter =
            object : FragmentStateAdapter(childFragmentManager, lifecycle) {
                override fun getItemCount(): Int {
                    return fragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }

            }
        binding.vpStoreData.adapter = mPagerAdapter
        binding.vpStoreData.offscreenPageLimit = 2

    }

    inner class ClickProxy {
        fun clickExpandArrow() {
            mStates.isExpanded.set(!(mStates.isExpanded.get() ?: false))
        }

        fun collect() {
            mStoreDetailsRequester.collect(
                mStates.uuid.get() ?: "",
                mStoreDetailsRequester.getCollectResult().value?.result ?: false
            )
        }

        fun back() {
            activity?.let {
                Navigation.findNavController(it, R.id.fragmentContainerView).navigateUp()
            }
        }
    }

    class StoreDetailsFragmentStates : StateHolder() {
        val isExpanded: State<Boolean> = State(false)

        val isCollapsed: MutableLiveData<Boolean> = MutableLiveData(false)

        val tabData: State<ArrayList<String>> = State(arrayListOf("数据", "设备", "合同"))

        val uuid: State<String> = State("18fbec57-ee17-4cd7-adc2-7f95cb12b400")
    }
}