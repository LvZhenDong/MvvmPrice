package com.kklv.store.ui.page

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.statelayout.Status
import com.kklv.common.data.RouterPath
import com.kklv.common.ui.view.adapter.BaseSimpleAdapter
import com.kklv.ktext.drawableLeft
import com.kklv.store.BR
import com.kklv.store.R
import com.kklv.store.data.bean.SchemaBean
import com.kklv.store.databinding.ActivityStoreDetailsBinding
import com.kklv.store.databinding.ItemStoreNavigationBinding
import com.kklv.store.databinding.ItemStoreTagBinding
import com.kklv.store.domain.request.StoreDetailsRequester
import com.kklv.store.domain.request.VisitRequester
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import com.kunminx.architecture.ui.state.State
import com.kunminx.architecture.utils.ext.addLifecycleOnOffsetChangedListener
import com.kunminx.architecture.utils.ext.toast
import kotlin.math.abs
import com.kklv.common.R as CR

@Route(path = RouterPath.PATH_STORE_DETAILS)
class StoreDetailsActivity : BaseActivity<ActivityStoreDetailsBinding>() {

    companion object {
        private const val COLLAPSE_RATE = 0.1F
    }

    private lateinit var mStates: StoreDetailsFragmentStates
    val mStoreDetailsRequester: StoreDetailsRequester by viewModels()

    private lateinit var mVisitRequester: VisitRequester

    override fun initViewModel() {
        mStates = getActivityScopeViewModel(StoreDetailsFragmentStates::class.java)
        mVisitRequester = getActivityScopeViewModel(VisitRequester::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_store_details, BR.vm, mStates)
            .addBindingParam(BR.click, ClickProxy())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        mStoreDetailsRequester.getStoreDetailsInfoResult().observe(this) {

            if (binding.refreshLayout.isRefreshing) binding.refreshLayout.finishRefresh()

            if (it.isSuccess) {
                binding.stateLayout.showContent()
                it.result.detailsInfo?.let { storeDetailsBean ->
                    binding.infoData = storeDetailsBean

                    binding.tvContact.drawableLeft(if (storeDetailsBean.telephone == null) null else CR.drawable.icon_contact)
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
            } else if (it.status == Status.LOADING) {
                binding.stateLayout.showLoading()
            } else if (it.status == Status.ERROR) {
                binding.stateLayout.showError()
                it.errorMsg.toast()
            } else {
                binding.stateLayout.showEmpty()
            }
        }

        mStoreDetailsRequester.getCollectResult().observe(this) {
            if (it.isSuccess) {
                setCollectImg(mStates.isCollapsed.value ?: false, it.result)
            } else {
                it.errorMsg.toast()
            }
        }

        mStates.isCollapsed.observe(this) { isCollapsed ->
            binding.ivBack.setImageResource(if (isCollapsed) CR.drawable.icon_store_back_black else CR.drawable.icon_store_back)
            setCollectImg(isCollapsed, mStoreDetailsRequester.getCollectResult().value?.result ?: false)
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
            if (isCollected) CR.drawable.iv_store_collect
            else if (isCollapsed) CR.drawable.iv_store_collect_black
            else CR.drawable.iv_store_collect_white
        )
    }

    private lateinit var tagAdapter: BaseSimpleAdapter<String, ItemStoreTagBinding>

    private fun initView() {
        initStateLayout()
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

        binding.appBarLayout.addLifecycleOnOffsetChangedListener(this) { appBarLayout, verticalOffset ->
            // 获取CollapsingToolbarLayout的总高度
            val totalScrollRange = appBarLayout.totalScrollRange

            // 计算折叠进度
            val collapsePercentage = (abs(verticalOffset).toFloat() / totalScrollRange.toFloat()).coerceIn(0f, 1f)
            binding.bgTop.alpha = collapsePercentage

            mStates.isCollapsed.value = collapsePercentage > COLLAPSE_RATE
        }
    }

    private fun initStateLayout() {
        binding.stateLayout.apply {
            loadingLayout = R.layout.skeleton_activity_store_details
            emptyLayout = CR.layout.layout_empty
            errorLayout = CR.layout.layout_error
            setRetryIds(CR.id.tvRetry)
            onRefresh {
                this@StoreDetailsActivity.refresh()
            }
        }
    }

    private fun initTab() {
        val fragments = arrayListOf(
            StoreDetailsDataFragment.getInstance(mStates.uuid.get() ?: ""),
            StoreDetailsVisitListFragment.getInstance(mStates.uuid.get() ?: ""),
            StoreDetailsContractFragment.getInstance(mStates.uuid.get() ?: "")
        )
        val mPagerAdapter =
            object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
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
            finish()
        }
    }

    class StoreDetailsFragmentStates : StateHolder() {
        val isExpanded: State<Boolean> = State(false)

        val isCollapsed: MutableLiveData<Boolean> = MutableLiveData(false)

        val tabData: State<ArrayList<String>> = State(arrayListOf("数据", "设备", "合同"))

        val uuid: State<String> = State("5ebf3793-5a56-4a68-ad75-b376dd2a8eff")
    }
}