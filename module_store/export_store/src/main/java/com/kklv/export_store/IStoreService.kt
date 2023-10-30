package com.kklv.export_store

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author lvzhendong
 * @data 2023/10/30
 * @description
 */
interface IStoreService : IProvider {

    fun getStoreName():String
}