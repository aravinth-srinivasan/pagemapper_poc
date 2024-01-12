package com.raweng.pagemapper.pagemappersdk

import android.app.Application
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.dfe_components_android.services.themeManager.ThemeMapper
import com.raweng.pagemapper.pagemappersdk.data.manager.api.CMSApiManager
import com.raweng.pagemapper.pagemappersdk.data.manager.api.DFEApiManager
import com.raweng.pagemapper.pagemappersdk.domain.CMSModel
import com.raweng.pagemapper.pagemappersdk.domain.NbaModel
import com.raweng.pagemapper.pagemappersdk.mapper.DFEConfigMapper
import com.raweng.pagemapper.pagemappersdk.type.Components
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.EnumMap


object PageMapperSDK {

    private lateinit var application: Application
    private lateinit var configMapper: DFEConfigMapper
    private val componentPlaceholder = EnumMap<Components, Int>(Components::class.java)

    fun init(application: Application) {
        this.application = application
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mConfig = DFEApiManager.fetchConfig(null, RequestType.Network)
                configMapper = DFEConfigMapper(mConfig)
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


    }

    fun setPlaceHolder(components: Components, placeHolder: Int) {
        componentPlaceholder[components] = placeHolder
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

    internal fun getComponentPlaceholder(components: Components): Int? {
        return componentPlaceholder.get(components)
    }

    internal fun getApplication(): Application? {
        if (this::application.isInitialized) {
            return application
        }
        return null
    }

}