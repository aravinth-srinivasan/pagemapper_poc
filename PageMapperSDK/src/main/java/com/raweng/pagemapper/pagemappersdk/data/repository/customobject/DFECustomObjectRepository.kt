package com.raweng.pagemapper.pagemappersdk.data.repository.customobject

import com.raweng.pagemapper.pagemappersdk.data.manager.api.DFEApiManager
import org.json.JSONObject

class DFECustomObjectRepository : IDFECustomObjectRepository {

    override suspend fun fetchCustomObject(className: String): JSONObject? {
        return DFEApiManager.fetchCustomObject(className)
    }
}