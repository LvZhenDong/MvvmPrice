package com.kklv.mytest.data.bean

/**
 * Author:lvzhendong
 * Created:2023/8/15
 * Desc:
 */
data class SchemaBean(
    val icon:String,
    val value:String,
    val display_index:Int
)

data class NavBtnsBean(
    val buttons:ArrayList<SchemaBean>
)