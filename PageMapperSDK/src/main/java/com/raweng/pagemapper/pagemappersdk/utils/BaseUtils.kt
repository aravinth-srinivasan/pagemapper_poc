package com.raweng.pagemapper.pagemappersdk.utils

import com.google.gson.Gson
import com.google.gson.JsonObject

inline fun <reified T> convertJsonObjectToModel(jsonObject: JsonObject?): T? {
    if (jsonObject == null) {
        return null
    }

    val gson = Gson()
    return gson.fromJson(jsonObject, T::class.java)
}