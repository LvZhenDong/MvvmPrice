package com.kklv.mytest.ui.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.data.bean.SchemaBean
import com.kklv.mytest.data.bean.StoreDetailsBean
import com.kklv.mytest.databinding.ItemStoreNavigationBinding
import com.kklv.mytest.databinding.ItemStoreTagBinding
import com.kklv.mytest.domain.request.StoreDetailsRequester
import com.kklv.mytest.ui.view.adapter.BaseResultDataAdapter
import com.kklv.mytest.ui.view.adapter.BaseSimpleAdapter
import com.kklv.mytest.utils.drawableLeft
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder
import com.kunminx.architecture.ui.state.State
import kotlinx.android.synthetic.main.activity_store_details.rvNavigation
import kotlinx.android.synthetic.main.activity_store_details.rvStoreTags
import kotlinx.android.synthetic.main.activity_store_details.tabStoreData
import kotlinx.android.synthetic.main.activity_store_details.tvContact
import kotlinx.android.synthetic.main.activity_store_details.vpStoreData
import net.lucode.hackware.magicindicator.ViewPagerHelper

class StoreDetailsActivity : BaseActivity() {
    private lateinit var mStates: StoreDetailsActivityStates
    private lateinit var mStoreDetailsRequester: StoreDetailsRequester

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
        initView()

        mStoreDetailsRequester.getStoreDetailsInfoResult().observe(this@StoreDetailsActivity) {
            mStates.dataInfo.set(it)
            if (it.is_mark) mStates.collectionRes.set(R.drawable.iv_store_collect)

            tvContact.drawableLeft(if (it.telephone == null) null else R.drawable.icon_contact)
            it.special_tags?.let { tags -> tagAdapter.setData(tags) }
        }

        mStoreDetailsRequester.getDetailsInfo()
    }

    private lateinit var tagAdapter: BaseSimpleAdapter<String, ItemStoreTagBinding>

    private fun initView() {
        initSkeleton()
        initTab()
        rvNavigation.adapter = BaseResultDataAdapter<SchemaBean, ItemStoreNavigationBinding>(
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
        rvStoreTags.adapter = tagAdapter
    }

    private fun initTab() {
        val fragments = arrayListOf(
            StoreDetailsDataFragment.getInstance(""),
            StoreDetailsDataFragment.getInstance(""),
            StoreDetailsDataFragment.getInstance("")
        )
        val mPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return fragments.size
            }

            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }
        }
        vpStoreData.adapter = mPagerAdapter
        vpStoreData.offscreenPageLimit = 2
        ViewPagerHelper.bind(tabStoreData, vpStoreData)
    }

    private fun initSkeleton() {

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

        val tabData:State<ArrayList<String>> = State(arrayListOf("数据", "设备", "合同"))
    }
}