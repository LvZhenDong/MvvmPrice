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
    val rival_text: String = "",
    val create_time: String = "",
    val sign_user_name: String = "",
    val maintain_user_name: String = "",
    val source: String = "",
    val classfiy: String = "",
    val latest_claim_time: String = ""
) {
    fun getMerchantName(): String {
        return merchant_info?.name ?: ""
    }
}

data class StoreDetailsMerchantInfo(
    val name: String,
    val merchant_detail_url: String,
    val is_bind_merchant: Boolean,
    val is_have_auth: Boolean
)

data class StoreDetailsStatBean(
    val description: String,
    val device_data: StoreDetailsGraphDeviceData
)

data class StoreDetailsGraphDeviceData(
    val sign_device_num: String = "",
    val bind_device_num: String = "",
    val offline_device_num: String = "",
    val slot_num: String = "",
    val battery_num: String = ""
)