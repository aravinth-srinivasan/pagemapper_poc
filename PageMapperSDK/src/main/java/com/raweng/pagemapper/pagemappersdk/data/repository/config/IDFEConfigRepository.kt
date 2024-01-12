package com.raweng.pagemapper.pagemappersdk.data.repository.config

import com.raweng.dfe.models.config.DFEConfigModel

interface IDFEConfigRepository {
    suspend fun fetchConfigFromDB(): DFEConfigModel
}