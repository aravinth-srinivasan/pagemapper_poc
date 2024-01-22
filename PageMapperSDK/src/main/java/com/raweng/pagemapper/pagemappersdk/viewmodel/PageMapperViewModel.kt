package com.raweng.pagemapper.pagemappersdk.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raweng.dfe.models.config.DFEConfigModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.data.api.base.Error
import com.raweng.pagemapper.pagemappersdk.data.repository.dfe.config.DFEConfigRepository
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.uistate.UiSatePageMapperModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.DFERequest
import com.raweng.pagemapper.pagemappersdk.domain.dfep.DFEScheduleRepository
import com.raweng.pagemapper.pagemappersdk.utils.JSONLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val SAMPLE_PAGE_MAPPER_JSON_FILE_NAME = "pagemapper.json"
private const val LOAD_FROM_JSON_ENABLE = true //TODO need to make this false

class PageMapperViewModel : ViewModel() {

    private var uiStateMutable: MutableLiveData<UiSatePageMapperModel> =
        MutableLiveData(UiSatePageMapperModel(loading = true))
    var uiStateLiveData: LiveData<UiSatePageMapperModel> = uiStateMutable

    private var uiStateDFEConfigMutable: MutableLiveData<DFEConfigModel> =
        MutableLiveData()
    var uiStateDFEConfigLiveData: LiveData<DFEConfigModel> = uiStateDFEConfigMutable

    var allComponentDataFetched = hashMapOf<String, Boolean>()


    fun initData() {
        if (LOAD_FROM_JSON_ENABLE) {
            val response = getDemoJSONFromAsset()
            notifyUiState(response)
        }
    }

    private fun getDemoJSONFromAsset(): DynamicScreenResponse? {
        return PageMapperSDK.getApplication()?.let {
            JSONLoader.getDynamicallyByAssetJSON(
                application = it,
                SAMPLE_PAGE_MAPPER_JSON_FILE_NAME
            )
        }
    }

    fun notifyUiState(
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

    fun updateAllComponentFetchedStatus(item: DynamicScreenResponse.Component) {
        Log.e("TAG", "updateAllComponentFetchedStatus: Called ${item.uid}")
        allComponentDataFetched[item.uid.orEmpty()] = true
        val receivedStatusSize = allComponentDataFetched.size
        val totalComponentSize = (uiStateLiveData.value?.data?.components?.size ?: 0)
        if (receivedStatusSize == totalComponentSize) {
            Log.e("TAG", "updateAllComponentFetchedStatus: All component fetched")
        }
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val config = fetchConfig()
            fetchSchedule()
            uiStateDFEConfigMutable.postValue(config)
        }
    }

    private suspend fun fetchConfig(): DFEConfigModel {
        val config = DFEConfigRepository().fetchConfigFromNetwork()
        PageMapperSDK.setConfig(config)
        return config
    }

    private suspend fun fetchSchedule() {
        val request = DFERequest(
            year = PageMapperSDK.getNBAModel()?.year ?: 0,
            leagueId = PageMapperSDK.getNBAModel()?.league_id,
            teamId = PageMapperSDK.getNBAModel()?.team_id
        )
        DFEScheduleRepository().fetchScheduleList(request, requestType = RequestType.NetworkElseDatabase)
    }

    fun getDFEConfigLiveData(): LiveData<DFEConfigModel> {
        return uiStateDFEConfigLiveData
    }
}