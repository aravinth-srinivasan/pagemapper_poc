package com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry

interface ICMSEntryRepository<T> {
    suspend fun fetchCMSEntry(
        contentType: String,
        entryId: String,
        includeReference: Array<String>? = null,
        isNetworkOnly: Boolean = true
    ): T?
}