package com.raweng.pagemapper.pagemappersdk.mapper.config

import com.raweng.dfe.models.config.DFEConfigModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.CMSModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.ConfigModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.MapperModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.NbaModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.PubNubModel
import com.raweng.pagemapper.pagemappersdk.utils.Utils
import com.raweng.pagemapper.pagemappersdk.utils.Utils.checkNullSafe
import org.json.JSONObject
import kotlin.reflect.KMutableProperty

class DFEConfigMapper(private val config: DFEConfigModel) {
    private val configModel = ConfigModel()
    private lateinit var integrationMapper: DFEConfigIntegrationMapper

    init {
        setupDefault()
    }

    private fun setupDefault() {
        integrationMapper = DFEConfigIntegrationMapper(
            config.integrations,
            configModel
        )
    }

    fun getCMSModel(): CMSModel {
        return configModel.cmsModel
    }

    fun getNBAModel(): NbaModel {
        return configModel.nbaModel
    }

    fun getPubNubModel(): PubNubModel {
        return configModel.pubnub
    }

}