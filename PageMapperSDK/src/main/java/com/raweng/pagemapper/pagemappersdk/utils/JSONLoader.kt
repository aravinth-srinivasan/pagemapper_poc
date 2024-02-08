package com.raweng.pagemapper.pagemappersdk.utils

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import java.io.IOException

internal object JSONLoader {

    private fun loadJSONFile(context: Application, fileName: String): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val byteArray = ByteArray(size)
            inputStream.read(byteArray)
            inputStream.close()
            json = String(byteArray)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }

    internal fun getDynamicallyByAssetJSON(
        application: Application?,
        fileName: String
    ): DynamicScreenResponse? {
        val json = application?.let {
            loadJSONFile(it, fileName)
        }

        return json?.let {
            val gson = Gson()
            gson.fromJson(json, DynamicScreenResponse::class.java)
        }
    }

    internal fun getHasMapByAssetJSON(
        application: Application?,
        fileName: String
    ): HashMap<String, String>? {
        val json = application?.let {
            loadJSONFile(it, fileName)
        }

        return json?.let {
            val gson = Gson()
            val type = object : TypeToken<HashMap<String, String>>() {}.type
            return gson.fromJson(json, type)
        }
    }

}