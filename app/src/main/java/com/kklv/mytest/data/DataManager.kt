package com.kklv.mytest.data

/**
 * Author:lvzhendong
 * Created:2023/8/17
 * Desc:
 */
class DataManager private constructor() {

    private var acToken: String = "uat_7d1a5744fb0849679943c3c97a79a5d2"
    private var rfToken:String = "urt_83a1702be63d4181a535781dfd3f8d70"

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
        return acToken
    }

    fun setAcToken(acToken: String) {
        this.acToken = acToken
    }
    fun getRfToken(): String {
        return rfToken
    }

    fun setRfToken(rfToken: String) {
        this.rfToken = rfToken
    }

}
