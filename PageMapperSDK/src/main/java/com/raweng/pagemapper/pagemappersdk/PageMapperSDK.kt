package com.raweng.pagemapper.pagemappersdk

import android.app.Application
import com.raweng.dfe.models.config.DFEConfigModel
import com.raweng.pagemapper.pagemappersdk.dependency.cmsreference.ComponentCMSIncludeReferenceDependency
import com.raweng.pagemapper.pagemappersdk.data.api.CMSApiManager
import com.raweng.pagemapper.pagemappersdk.domain.dfep.CMSModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.DFERequest
import com.raweng.pagemapper.pagemappersdk.domain.dfep.NbaModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.PubNubModel
import com.raweng.pagemapper.pagemappersdk.mapper.config.DFEConfigMapper
import com.raweng.pagemapper.pagemappersdk.dependency.placeholder.ComponentPlaceHolderDependency
import com.raweng.pagemapper.pagemappersdk.dependency.ticketmaster.TicketMasterDependency
import com.raweng.pagemapper.pagemappersdk.type.Components


object PageMapperSDK  {

    private lateinit var application: Application
    private lateinit var configMapper: DFEConfigMapper
    lateinit var PUBNUB_SUB_KEY: String


    fun init(application: Application) {
        this.application = application
    }

    fun setConfig(config: DFEConfigModel) {
        try {
            configMapper = DFEConfigMapper(config)
            val cmsModel = configMapper.getCMSModel()
            CMSApiManager.initContentStack(
                application = application,
                key = cmsModel.app_key,
                token = cmsModel.delivery_token,
                env = cmsModel.environment,
                cdnUrl = cmsModel.url
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setComponentPlaceHolder(components: Components, placeHolder: Any) {
        ComponentPlaceHolderDependency.config(components, placeHolder)
    }

    fun setComponentCMSIncludeRef(components: Components, includeRef: Array<String>) {
        ComponentCMSIncludeReferenceDependency.config(components, includeRef)
    }

    fun getTicketMasterId(): String? {
        return TicketMasterDependency.getTicketMasterId()
    }

    fun setTicketMasterId(ticketMasterId: String?) {
        return TicketMasterDependency.setTicketMasterId(ticketMasterId)
    }

    internal fun getCMSModel(): CMSModel? {
        if (this::configMapper.isInitialized) {
            return configMapper.getCMSModel()
        }
        return null
    }

    internal fun getNBAModel(): NbaModel? {
        if (this::configMapper.isInitialized) {
            return configMapper.getNBAModel()
        }
        return null
    }

    internal fun getPubNubModel(): PubNubModel? {
        if (this::configMapper.isInitialized) {
            return configMapper.getPubNubModel()
        }
        return null
    }

    internal fun getDFERequest(): DFERequest {
        return DFERequest(
            fields = null,
            year = configMapper.getNBAModel().year,
            leagueId = configMapper.getNBAModel().league_id,
            teamId = configMapper.getNBAModel().team_id
        )
    }


    internal fun getApplication(): Application? {
        if (this::application.isInitialized) {
            return application
        }
        return null
    }

}