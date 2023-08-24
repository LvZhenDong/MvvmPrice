package com.kklv.mytest.data.bean

import com.google.gson.annotations.JsonAdapter
import com.kklv.mytest.data.bean.base.TagData
import com.kklv.mytest.data.bean.gson.adapter.IntToBooleanAdapter

/**
 * @author lvzhendong
 * @data 2023/8/24
 * @description
 */
data class VisitBean(
    val detail_url: String,
    val visit_obj: String,
    val is_store: Boolean,//true  门店
    val visit_obj_name: String,
    val store_level: String,
    val create_time: String,
    val total_visit: String,
    val user_total_visit: String,
    val visit_note: String,
    var comment_num: Int,
    var unread_num: Int,
    val visit_id: String,
    val visit_user: String,
    val tags: ArrayList<VisitTag>?
){
    fun getStoreType():String{
        visit_note.isEmpty()
        return if (is_store) "门店" else "商户"
    }
}

data class VisitTag(
    val name: String,
    @JsonAdapter(IntToBooleanAdapter::class)
    val is_distance: Boolean = false
) : TagData {
    override fun isTagSelected(): Boolean = is_distance

    override fun getTagText(): String = name
}
