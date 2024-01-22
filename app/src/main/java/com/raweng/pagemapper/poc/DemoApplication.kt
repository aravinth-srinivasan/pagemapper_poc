package com.raweng.pagemapper.poc

import android.app.Application
import android.util.Log
import com.raweng.dfe.DFEManager
import com.raweng.dfe.models.config.DFEConfigCallback
import com.raweng.dfe.models.config.DFEConfigModel
import com.raweng.dfe.modules.policy.ErrorModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.dfe_components_android.services.themeManager.ThemeMapper
import com.raweng.pagemapper.ComponentPlaceHolder
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.data.api.DFEApiManager
import com.raweng.pagemapper.pagemappersdk.data.api.base.DFEResponseCallback
import com.raweng.pagemapper.pagemappersdk.type.Components
import kotlinx.coroutines.runBlocking
import java.util.EnumMap

private typealias NBAThemeMapper = com.raweng.nba_components_android.services.themeManager.ThemeMapper

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        ThemeMapper.setAppTheme(applicationContext)
        ThemeMapper.setThemeMode(ThemeMapper.ThemeMode.SYSTEM)
        NBAThemeMapper.setAppTheme(applicationContext)
        initDFE()
        initPageMapperSDK()
    }


    private fun initDFE() {
        val dfeManager = DFEManager.getInst(this)
        dfeManager.initialize(this)
        Log.e("TAG", "initDFE: done")
    }

    private fun initPageMapperSDK() {
        PageMapperSDK.init(this)
        ComponentPlaceHolder.init()
        ComponentCMSIncludeReference.init()
    }

}