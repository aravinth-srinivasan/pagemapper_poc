package com.raweng.pagemapper.pagemappersdk.data.repository.dfe.config

import com.raweng.dfe.models.config.DFEConfigModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.pagemapper.pagemappersdk.data.api.DFEApiManager

class DFEConfigRepository : IDFEConfigRepository {

    override suspend fun fetchConfigFromDB(): DFEConfigModel {
        return DFEApiManager.fetchConfig(null, RequestType.Database)
    }

    override suspend fun fetchConfigFromNetwork(): DFEConfigModel {
        return DFEApiManager.fetchConfig(null, RequestType.NetworkElseDatabase)
    }
}