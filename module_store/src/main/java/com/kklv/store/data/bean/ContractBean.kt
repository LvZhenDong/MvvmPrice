package com.kklv.store.data.bean

import com.google.gson.annotations.SerializedName
import com.kklv.mytest.data.bean.base.ColorTagBean

/**
 * @author lvzhendong
 * @data 2023/8/22
 * @description
 */
data class ContractBean(
    val merchant_id: String,
    val type_text: String,
    @SerializedName("merchant_name")
    val merchant_name: ColorTagBean?,
    val merchant_detail_url: String,
    val contract_status: ColorTagBean?,
    val business_condition: String,
    val gross_tag: ColorTagBean?,
    @SerializedName("contrac_duration", alternate = ["contract_duration"])
    val contract_duration: String,
    val expire_tag: ColorTagBean?,
    val signed_user: String,
    val tags: ArrayList<ColorTagBean>?,
    val contract_detail_url: String
) {
    fun getContractStatusText(): String {
        return contract_status?.text ?: ""
    }

    fun getContractStatusColor(): String {
        return contract_status?.text_color ?: "#FF0000"
    }

    fun getExpireTagText(): String {
        return expire_tag?.text ?: ""
    }

    fun isExpireTagVisible(): Boolean = expire_tag?.text.isNullOrEmpty().not()
}

data class ContractListBean(
    val is_have_auth: Boolean,
    val list: ArrayList<ContractBean>?
)