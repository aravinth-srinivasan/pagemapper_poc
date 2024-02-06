package com.raweng.pagemapper.pagemappersdk.views.components.carousel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raweng.pagemapper.pagemappersdk.domain.uistate.UiStateComponentModel
import com.raweng.pagemapper.pagemappersdk.livegame.LiveGameViewModel
import com.raweng.pagemapper.pagemappersdk.viewmodel.BaseViewModel
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.mapper.CarouselMapper
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.provider.CarouselViewDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class CarouselViewModel(private val provider: CarouselViewDataProvider) : BaseViewModel(provider) {

    private var uiUpdatedStateMutable: MutableLiveData<UiStateComponentModel> =
        MutableLiveData(UiStateComponentModel(loading = true))
    var uiUpdatedStateLiveData: LiveData<UiStateComponentModel> = uiUpdatedStateMutable

    fun getGameStatsMapper(): CarouselMapper {
        return provider.getCarouselMapper()
    }

    fun fetchLiveGamePlayByPlay(viewModel: LiveGameViewModel) {
        viewModelScope.launch(Dispatchers.IO) {
            viewModel.getDFEPlayByPlayLiveFlow().collect {
                if (!it.isNullOrEmpty()) {
                    val res = uiStateLiveData.value?.data
                    if (res != null) {
                        val updatedData = provider.getCarouselMapper().setLiveResponse(it)
                        if (updatedData != null) {
                            uiUpdatedStateMutable.postValue(UiStateComponentModel(data = updatedData))
                        }
                    }
                }
            }
        }
    }
}