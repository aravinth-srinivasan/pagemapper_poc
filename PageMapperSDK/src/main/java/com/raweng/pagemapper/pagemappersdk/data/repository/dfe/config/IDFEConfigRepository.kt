package com.raweng.pagemapper.pagemappersdk.data.repository.dfe.config

import com.raweng.dfe.models.config.DFEConfigModel

interface IDFEConfigRepository {
    suspend fun fetchConfigFromDB(): DFEConfigModel
    suspend fun fetchConfigFromNetwork(): DFEConfigModel
}