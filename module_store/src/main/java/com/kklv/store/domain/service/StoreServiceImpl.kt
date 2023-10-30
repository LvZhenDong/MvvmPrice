package com.kklv.store.domain.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.kklv.common.data.RouterPath
import com.kklv.export_store.IStoreService

/**
 * @author lvzhendong
 * @data 2023/10/30
 * @description
 */
@Route(path = RouterPath.PATH_SERVICE_STORE)
class StoreServiceImpl : IStoreService {
    override fun getStoreName(): String {
        return "门店名称"
    }

    override fun init(context: Context?) {

    }
}