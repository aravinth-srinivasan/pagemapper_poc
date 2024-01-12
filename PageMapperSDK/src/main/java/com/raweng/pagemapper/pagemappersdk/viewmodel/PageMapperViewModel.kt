package com.raweng.pagemapper.pagemappersdk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raweng.pagemapper.pagemappersdk.data.manager.PagerDataManager
import com.raweng.pagemapper.pagemappersdk.data.manager.api.base.Error
import com.raweng.pagemapper.pagemappersdk.domain.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.domain.UiSatePageMapperModel
import com.raweng.pagemapper.pagemappersdk.utils.JSONLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val SAMPLE_PAGE_MAPPER_JSON_FILE_NAME = "pagemapper.json"
private const val LOAD_FROM_JSON_ENABLE = true //TODO need to make this false

class PageMapperViewModel(private val application: Application) : AndroidViewModel(application) {
    val componentDataResults = mutableMapOf<String, ResponseDataModel>()

    private var uiStateMutable: MutableLiveData<UiSatePageMapperModel> =
        MutableLiveData(UiSatePageMapperModel(loading = true))
    var uiStateLiveData: LiveData<UiSatePageMapperModel> = uiStateMutable


    fun initData() {
        if (LOAD_FROM_JSON_ENABLE) {
            val response = getDemoJSONFromAsset()
            fetchComponentData(response)
        }
    }

    private fun fetchComponentData(response: DynamicScreenResponse?) {
        viewModelScope.launch(Dispatchers.IO) {
            val manager = PagerDataManager(
                response?.components,
                componentDataResults,
                application
            )
            manager.onComplete = {
                notifyUiState(response)
            }
            manager.sync()
        }

    }


    private fun getDemoJSONFromAsset(): DynamicScreenResponse? {
        return JSONLoader.getDynamicallyByAssetJSON(
            application = application,
            SAMPLE_PAGE_MAPPER_JSON_FILE_NAME
        )
    }

    private fun notifyUiState(
        data: DynamicScreenResponse? = null,
        loading: Boolean = false,
        error: Error? = null
    ) {
        uiStateMutable.postValue(
            UiSatePageMapperModel(
                loading = loading,
                data = data,
                error = error
            )
        )
    }

}