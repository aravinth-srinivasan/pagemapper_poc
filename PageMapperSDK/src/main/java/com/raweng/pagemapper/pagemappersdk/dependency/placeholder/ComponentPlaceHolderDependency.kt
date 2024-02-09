package com.raweng.pagemapper.pagemappersdk.dependency.placeholder

internal object ComponentPlaceHolderDependency {

    private lateinit var placeholder: HashMap<String, Any>
    fun setPlaceholder(placeholder: HashMap<String, Any>) {
        this.placeholder = placeholder
    }

    fun getPlaceholder(): HashMap<String, Any>? {
        if (this::placeholder.isInitialized) {
            return placeholder
        }
        return null
    }
}
