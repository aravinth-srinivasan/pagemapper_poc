package com.raweng.pagemapper.pagemappersdk.data.repository.dfe.customobject

import com.raweng.pagemapper.pagemappersdk.data.api.DFEApiManager
import org.json.JSONObject

class DFECustomObjectRepository : IDFECustomObjectRepository {

    override suspend fun fetchCustomObject(className: String): JSONObject? {
        return DFEApiManager.fetchCustomObject(className)
    }
}