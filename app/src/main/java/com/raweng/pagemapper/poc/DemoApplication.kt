package com.raweng.pagemapper.poc

import android.app.Application
import android.util.Log
import com.raweng.dfe.DFEManager
import com.raweng.dfe_components_android.services.themeManager.ThemeMapper
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.poc.placeholder.PlaceHolders

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
        PageMapperSDK.setComponentPlaceHolder(PlaceHolders.getPlaceHolder())
        ComponentCMSIncludeReference.init()
    }

}