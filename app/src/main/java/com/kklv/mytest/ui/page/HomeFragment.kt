package com.kklv.mytest.ui.page

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.databinding.FragmentHomeBinding
import com.kklv.mytest.databinding.FragmentStoreDetailsDataBinding
import com.kunminx.architecture.ui.page.BaseFragment
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.page.StateHolder

/**
 * Author:lvzhendong
 * Created:2023/8/16
 * Desc:
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    companion object {

        private const val ARG_STORE_ID = "storeId"

        fun getInstance(storeId: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_STORE_ID, storeId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mStates: HomeFragmentStates
    override fun initViewModel() {
        mStates = getActivityScopeViewModel(HomeFragmentStates::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_home, BR.vm, mStates).addBindingParam(BR.click,ClickProxy())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    inner class ClickProxy {

        fun goToStoreDetailsActivity() {
            activity?.let {
                Navigation.findNavController(it, R.id.fragmentContainerView)
                    .navigate(R.id.action_homeFragment_to_storeDetailsFragment)
            }

        }
    }

    class HomeFragmentStates : StateHolder() {
    }
}