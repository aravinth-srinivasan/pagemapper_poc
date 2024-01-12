package com.raweng.pagemapper.pagemappersdk.data.manager.api

import android.app.Application
import com.contentstack.sdk.CachePolicy
import com.contentstack.sdk.Config
import com.contentstack.sdk.ContentType
import com.contentstack.sdk.Contentstack
import com.contentstack.sdk.Entry
import com.contentstack.sdk.EntryResultCallBack
import com.contentstack.sdk.ResponseType
import com.contentstack.sdk.Stack
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import java.lang.Exception
import kotlin.coroutines.resume

typealias Error = com.contentstack.sdk.Error

object CMSApiManager {

    private var mStack: Stack? = null
    private lateinit var application: Application

    fun initContentStack(
        application: Application,
        key: String?,
        token: String?,
        env: String?,
        cdnUrl: String?
    ) {
        this.application = application
        try {
            val config = Config()
            config.host = cdnUrl
            mStack = Contentstack.stack(
                application,
                key,
                token,
                env,
                config
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getContentStack(): Stack? {
        if (mStack == null) {
            val key: String = PageMapperSDK.getCMSModel()?.app_key.orEmpty()
            val token: String = PageMapperSDK.getCMSModel()?.delivery_token.orEmpty()
            val env: String = PageMapperSDK.getCMSModel()?.environment.orEmpty()
            val url: String = PageMapperSDK.getCMSModel()?.url.orEmpty()
            if (this::application.isInitialized) {
                initContentStack(application, key, token, env, url)
            }
        }
        return mStack
    }

    suspend fun getEntryFromContentType(
        contentType: String,
        entryUid: String,
        includeReference: Array<String>? = null,
        isNetworkOnly: Boolean = true
    ): String = suspendCancellableCoroutine {
        try {
            val mContentType: ContentType? = getContentStack()?.contentType(contentType)
            val entry: Entry? = mContentType?.entry(entryUid)
            includeReference?.let { ref ->
                entry?.includeReference(ref)
            }
            if (isNetworkOnly) {
                entry?.setCachePolicy(CachePolicy.NETWORK_ONLY)
            } else {
                entry?.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK)
            }
            entry?.fetch(object : EntryResultCallBack() {
                override fun onCompletion(
                    responseType: ResponseType?,
                    error: Error?
                ) {
                    if (error == null) {
                        val jsonObject: JSONObject = entry.toJSON()
                        it.resume(jsonObject.toString())
                    } else {
                        it.resume("")
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            it.resume("")
        }
    }
}