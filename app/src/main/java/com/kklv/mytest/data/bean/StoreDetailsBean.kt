package com.kklv.mytest.data.bean

/**
 * Author:lvzhendong
 * Created:2023/8/13
 * Desc:
 */
data class StoreDetailsBean(
    val bg_imgs: ArrayList<String>? = null,
    val store_name: String = "",
    var is_mark: Boolean = false,
    val full_address: String = "",
    val distance: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val store_level: String? = null,
    val store_level_icon: String? = null,
    val special_tags: ArrayList<String>? = null,
    val merchant_info: StoreDetailsMerchantInfo? = null,
    val opening_hours: String = "",
    val opening_hours_url: String = "",
    val can_edit_opening_hours: Boolean = false,
    val contact_text: String = "",
    val telephone: String? = null,
    private val rival_text: String = "",
    private val create_time: String = "",
    private val sign_user_name: String = "",
    private val maintain_user_name: String = "",
    private val source: String = "",
    private val classfiy: String = "",
    private val latest_claim_time: String = ""
) {
    fun getRivalText(): String {
        return "竞对情况：$rival_text"
    }

    fun getCreateTime(): String {
        return "门店创建时间：$create_time"
    }

    fun getSignerAndOperation(): String {
        return "签约：${sign_user_name}  |  运维：${maintain_user_name}"
    }

    fun getStoreSource(): String {
        return "门店来源：${source}"
    }

    fun getStoreType(): String {
        return "分类：${classfiy}"
    }

    fun getLatestClaimTime(): String {
        return "最新认领时间：${latest_claim_time}"
    }

    fun getStoreUUID(): String {
        return "门店UUID：storeId"
    }

    fun getMerchantName():String{
        return merchant_info?.name?:""
    }
}

data class StoreDetailsMerchantInfo(
    val name: String,
    val merchant_detail_url: String,
    val is_bind_merchant: Boolean,
    val is_have_auth: Boolean
)