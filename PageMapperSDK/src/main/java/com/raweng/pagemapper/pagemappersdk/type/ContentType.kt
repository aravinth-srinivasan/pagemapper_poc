package com.raweng.pagemapper.pagemappersdk.type

enum class ContentType(private val value: String) {

    DFEP("dfep"),
    CONTENT_STACK("contentstack");
    companion object {
        fun fromValue(value: String): ContentType? =
            ContentType.entries.find {
                it.value.equals(value, ignoreCase = true)
            }
    }

    override fun toString(): String {
        return value
    }
}