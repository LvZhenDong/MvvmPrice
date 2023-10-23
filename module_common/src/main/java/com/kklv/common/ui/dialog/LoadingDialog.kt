package com.kklv.common.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.kklv.common.R

class LoadingDialog(context: Context) : Dialog(context) {

    init {
        // 设置Dialog的布局和属性
        setContentView(R.layout.dialog_loading)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params = window?.attributes
        params?.gravity = Gravity.CENTER
        window?.attributes = params

        // 添加ProgressBar
        val progressBar = ProgressBar(context)
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        progressBar.layoutParams = layoutParams
        setContentView(progressBar)
    }

    // 显示LoadingDialog
    fun showLoading() {
        if (!isShowing) {
            show()
        }
    }

    // 隐藏LoadingDialog
    fun hideLoading() {
        if (isShowing) {
            dismiss()
        }
    }
}
