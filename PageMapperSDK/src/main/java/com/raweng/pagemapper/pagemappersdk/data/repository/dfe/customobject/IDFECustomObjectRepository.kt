package com.raweng.pagemapper.pagemappersdk.data.repository.dfe.customobject

import org.json.JSONObject

interface IDFECustomObjectRepository {
    suspend fun fetchCustomObject(className: String): JSONObject?
}