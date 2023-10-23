package com.kklv.common.data

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kklv.common.ext.userInfoDataStore
import com.kunminx.architecture.utils.Utils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/**
 * Author:lvzhendong
 * Created:2023/8/17
 * Desc:
 */
class DataManager private constructor() {

    private val acTokenKey = stringPreferencesKey("acToken")
    private val rfTokenKey = stringPreferencesKey("rfToken")
    private val uidKey = stringPreferencesKey("uid")

    companion object {

        @Volatile
        private var instance: DataManager? = null

        fun getInstance(): DataManager {
            return instance ?: synchronized(this) {
                instance ?: DataManager().also { instance = it }
            }
        }
    }

    fun getAcToken(): String {
        val preference = runBlocking {
            Utils.getApp().userInfoDataStore.data.first()
        }
        return preference[acTokenKey] ?: ""
    }

    fun setAcToken(acToken: String) {
        runBlocking {
            Utils.getApp().userInfoDataStore.edit { preferences ->
                preferences[acTokenKey] = acToken
            }
        }
    }

    fun getRfToken(): String {
        val preference = runBlocking {
            Utils.getApp().userInfoDataStore.data.first()
        }
        return preference[rfTokenKey] ?: ""
    }

    fun setRfToken(rfToken: String) {
        runBlocking {
            Utils.getApp().userInfoDataStore.edit { preferences ->
                preferences[rfTokenKey] = rfToken
            }
        }
    }

    fun getUid(): String {
        val preference = runBlocking {
            Utils.getApp().userInfoDataStore.data.first()
        }
        return preference[uidKey] ?: ""
    }

    fun setUid(uid: String) {
        runBlocking {
            Utils.getApp().userInfoDataStore.edit { preferences ->
                preferences[uidKey] = uid
            }
        }
    }

    fun isNeedLogin(): Boolean {
        return getAcToken().isNullOrEmpty()
    }

}
