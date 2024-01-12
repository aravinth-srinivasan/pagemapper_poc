package com.raweng.pagemapper.pagemappersdk.data.repository.config

import com.raweng.dfe.models.config.DFEConfigModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.pagemapper.pagemappersdk.data.manager.api.DFEApiManager

class DFEConfigRepository : IDFEConfigRepository {

    override suspend fun fetchConfigFromDB(): DFEConfigModel {
        return DFEApiManager.fetchConfig(null, RequestType.Database)
    }
}