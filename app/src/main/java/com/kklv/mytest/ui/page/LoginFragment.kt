package com.kklv.mytest.ui.page

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.drake.statelayout.Status
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.databinding.FragmentLoginBinding
import com.kklv.mytest.domain.request.LoginRequester
import com.kklv.mytest.ui.dialog.LoadingDialog
import com.kunminx.architecture.ui.page.BaseFragment
import com.kunminx.architecture.ui.page.DataBindingConfig

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private lateinit var mLoginRequester: LoginRequester
    private lateinit var loadingDialog: LoadingDialog

    override fun initViewModel() {
        mLoginRequester = getFragmentScopeViewModel(LoginRequester::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_login, BR.vm, mLoginRequester)
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())

        mLoginRequester.getLoginResult().observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                loadingDialog.hideLoading()
                goToStoreDetails()
            } else if (it.status == Status.LOADING) {
                loadingDialog.showLoading()
            }
        }
    }

    private fun goToStoreDetails() {
        activity?.let {
            Navigation.findNavController(it, R.id.fragmentContainerView)
                .navigate(R.id.action_loginFragment_to_storeDetailsFragment)
        }
    }

    inner class ClickProxy {
        fun login() {
            mLoginRequester.login()
        }
    }
}