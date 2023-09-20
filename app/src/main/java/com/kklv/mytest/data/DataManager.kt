package com.kklv.mytest.data

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kklv.mytest.userInfoDataStore
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
    private var acTokenInit: String = "uat_7d1a5744fb0849679943c3c97a79a5d2"
    private var rfTokenInit: String = "urt_83a1702be63d4181a535781dfd3f8d70"

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
        return preference[acTokenKey] ?: acTokenInit
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
        return preference[rfTokenKey] ?: rfTokenInit
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

}
