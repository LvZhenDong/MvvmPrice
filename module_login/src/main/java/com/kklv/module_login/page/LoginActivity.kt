package com.kklv.module_login.page

import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.drake.statelayout.Status
import com.kklv.common.data.RouterPath
import com.kklv.common.ui.dialog.LoadingDialog
import com.kklv.export_store.IStoreService
import com.kklv.module_login.R
import com.kklv.module_login.BR
import com.kklv.module_login.databinding.ActivityLoginBinding
import com.kklv.module_login.request.LoginRequester
import com.kunminx.architecture.ui.page.BaseActivity
import com.kunminx.architecture.ui.page.DataBindingConfig

@Route(path = RouterPath.PATH_LOGIN)
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

        val storeService = ARouter.getInstance().build(RouterPath.PATH_SERVICE_STORE).navigation() as IStoreService
        Log.i("kklv",storeService.getStoreName())
    }

    private fun goToStoreDetails() {
        finish()
    }

    inner class ClickProxy {
        fun login() {
            mLoginRequester.login()
        }
    }
}