package com.raweng.pagemapper.pagemappersdk.dependency

class PageMapperDependency {

    private lateinit var parentScreen: String
    fun getParentScreenName(): String? {
        if (this::parentScreen.isInitialized) {
            return parentScreen
        }
        return null
    }

    fun setParentScreenName(parentScreen: String?) {
        if (!parentScreen.isNullOrEmpty()) {
            this.parentScreen = parentScreen
        }
        this.parentScreen = ""
    }
}