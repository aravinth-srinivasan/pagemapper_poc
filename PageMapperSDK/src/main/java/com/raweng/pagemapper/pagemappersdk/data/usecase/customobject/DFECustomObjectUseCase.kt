package com.raweng.pagemapper.pagemappersdk.data.usecase.customobject

import com.google.gson.Gson
import com.raweng.pagemapper.pagemappersdk.data.repository.dfe.customobject.IDFECustomObjectRepository
import com.raweng.pagemapper.pagemappersdk.views.ads.domain.GoogleAdsSponsor
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.views.ads.extension.toGoogleAdsDataModel

class DFECustomObjectUseCase(
    private val repository: IDFECustomObjectRepository
) {

    suspend fun execute(className: String): ResponseDataModel {
        val response = try {
            repository.fetchCustomObject(className)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        var googleAdsSponsor: GoogleAdsSponsor? = null
        if (response != null) {
            val json = response.toString()
            if (json.isNotEmpty()) {
                val gson = Gson()
                googleAdsSponsor = gson.fromJson(json, GoogleAdsSponsor::class.java)
            }
        }

        return ResponseDataModel(
            apiResponse = googleAdsSponsor,
            convertedData = googleAdsSponsor.toGoogleAdsDataModel("grizzlies://screen/home") //TODO need to discuss
        )
    }
}