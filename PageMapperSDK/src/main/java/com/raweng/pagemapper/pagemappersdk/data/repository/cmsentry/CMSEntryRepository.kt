package com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raweng.pagemapper.pagemappersdk.data.api.CMSApiManager
import java.lang.reflect.Type

class CMSEntryRepository<T> : ICMSEntryRepository<T> {
    override suspend fun fetchCMSEntry(
        contentType: String,
        entryId: String,
        includeReference: Array<String>?,
        isNetworkOnly: Boolean
    ): T? {
        val gson = Gson()
        val entry = CMSApiManager.getEntryFromContentType(
            contentType,
            entryId,
            includeReference,
            isNetworkOnly
        )
        if (entry.isNotEmpty()) {
            val type: Type = object : TypeToken<T>() {}.type
            return gson.fromJson(entry, type)
        }
        return null
    }
}