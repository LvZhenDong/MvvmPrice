package com.kklv.mytest.ui.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.bestbrand.lib_skeleton.skeleton.ViewSkeletonScreen
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.data.bean.SchemaBean
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.databinding.ActivityStoreDetailsBinding
import com.kklv.mytest.databinding.ItemStoreNavigationBinding
import com.kklv.mytest.databinding.ItemStoreTagBinding
import com.kklv.mytest.domain.request.StoreDetailsRequester
import com.kklv.mytest.ui.view.adapter.BaseResultDataAdapter
import com.kklv.mytest.ui.view.adapter.BaseSimpleAdapter
import com.kklv.mytest.utils.buildSkeleton
import com.kklv.mytest.utils.drawableLeft
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import com.kunminx.architecture.ui.state.State

class StoreDetailsActivity : BaseActivity<ActivityStoreDetailsBinding>() {
    private lateinit var mStates: StoreDetailsActivityStates
    private lateinit var mStoreDetailsRequester: StoreDetailsRequester

    private lateinit var mSkeleton: ViewSkeletonScreen

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

        mSkeleton = buildSkeleton(binding.coordinator, R.layout.skeleton_activity_store_details)

        mStoreDetailsRequester.getStoreDetailsInfoResult().observe(this@StoreDetailsActivity) {
            if (mSkeleton.isShow) hideSkeletonAndInitView()

            mStates.dataInfo.set(it)
            if (it.is_mark) mStates.collectionRes.set(R.drawable.iv_store_collect)

            binding.tvContact.drawableLeft(if (it.telephone == null) null else R.drawable.icon_contact)
            it.special_tags?.let { tags -> tagAdapter.setData(tags) }
        }

        mStoreDetailsRequester.getDetailsInfo(mStates.uuid.get() ?: "")
    }

    private fun hideSkeletonAndInitView() {
        mSkeleton.hide()
        initView()
    }

    private lateinit var tagAdapter: BaseSimpleAdapter<String, ItemStoreTagBinding>

    private fun initView() {
        initTab()

        binding.rvNavigation.adapter = BaseResultDataAdapter<SchemaBean, ItemStoreNavigationBinding>(
            mStoreDetailsRequester.getNavBtnsResult(),
            this,
            R.layout.item_store_navigation
        ) { _, data, binding ->
            binding.apply {
                imgUrl = data.icon
                btnText = data.value
            }
        }

        tagAdapter = BaseSimpleAdapter(
            arrayListOf(),
            R.layout.item_store_tag
        ) { _, data, binding ->
            binding.apply {
                imgUrl = data
            }
        }
        binding.rvStoreTags.adapter = tagAdapter
    }

    private fun initTab() {
        val fragments = arrayListOf(
            StoreDetailsVisitListFragment.getInstance(""),
            StoreDetailsDataFragment.getInstance(""),
            StoreDetailsContractFragment.getInstance(mStates.uuid.get() ?: "")
        )
        val mPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getCount(): Int {
                return fragments.size
            }

            override fun getItem(position: Int): Fragment {
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

        fun back() {
            finish()
        }
    }


    class StoreDetailsActivityStates : StateHolder() {
        val dataInfo: State<StoreDetailsBean> = State(StoreDetailsBean())

        val isExpanded: State<Boolean> = State(false)

        val collectionRes: State<Int> = State(R.drawable.iv_store_collect_white)

        val tabData: State<ArrayList<String>> = State(arrayListOf("数据", "设备", "合同"))

        val uuid: State<String> = State("a7ca8862-5a11-4ae7-ad53-6c869177d17f")
    }
}