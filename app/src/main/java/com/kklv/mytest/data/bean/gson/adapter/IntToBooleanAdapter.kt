package com.kklv.mytest.data.bean.gson.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * 将Int解析为boolean
 */
class IntToBooleanAdapter : TypeAdapter<Boolean>() {
    override fun write(out: JsonWriter?, value: Boolean?) {
        if (value == null) {
            out?.nullValue()
        } else {
            out?.value(if (value) 1 else 0)
        }
    }

    override fun read(`in`: JsonReader?): Boolean {
        if (`in`?.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return false
        }
        return `in`?.nextInt() == 1
    }
}
