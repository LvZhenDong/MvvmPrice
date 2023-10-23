package com.kklv.common.ext

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

/**
 * @author lvzhendong
 * @data 2023/10/23
 * @description
 */
//用户信息持久化数据
val Context.userInfoDataStore by preferencesDataStore(name = "userInfo")