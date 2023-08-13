package com.kklv.mytest.data.bean

/**
 * Author:lvzhendong
 * Created:2023/8/13
 * Desc:
 */
data class StoreDetailsBean(
    val bg_imgs: ArrayList<String>?,
    val store_name: String,
    var is_mark: Boolean,
    val full_address: String,
    val distance: String,
    val latitude: String,
    val longitude: String,
    val store_level: String?,
    val store_level_icon: String?,
    val special_tags: ArrayList<String>?,
    val opening_hours: String,
    val opening_hours_url: String,
    val can_edit_opening_hours: Boolean,
    val contact_text: String,
    val telephone: String?,
    val rival_text: String,
    val create_time: String,
    val sign_user_name: String,
    val maintain_user_name: String,
    val source: String,
    val classfiy: String,
    val latest_claim_time: String
)