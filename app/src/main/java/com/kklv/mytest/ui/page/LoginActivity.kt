package com.kklv.mytest.ui.page

import android.os.Bundle
import com.drake.statelayout.Status
import com.kklv.mytest.BR
import com.kklv.mytest.R
import com.kklv.mytest.domain.request.LoginRequester
import com.kklv.common.ui.dialog.LoadingDialog
import com.kklv.mytest.databinding.ActivityLoginBinding
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var mLoginRequester: LoginRequester
    private lateinit var loadingDialog: LoadingDialog

    override fun initViewModel() {
        mLoginRequester = getActivityScopeViewModel(LoginRequester::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_login, BR.vm, mLoginRequester)
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)

        mLoginRequester.getLoginResult().observe(this) {
            if (it.isSuccess) {
                loadingDialog.hideLoading()
                goToStoreDetails()
            } else if (it.status == Status.LOADING) {
                loadingDialog.showLoading()
            }
        }
    }

    private fun goToStoreDetails() {

    }

    inner class ClickProxy {
        fun login() {
            mLoginRequester.login()
        }
    }
}