package com.raweng.pagemapper.pagemappersdk.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raweng.pagemapper.pagemappersdk.data.provider.base.IBaseProvider
import com.raweng.pagemapper.pagemappersdk.domain.uistate.UiStateComponentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal abstract class BaseViewModel(private val provider: IBaseProvider) : ViewModel() {

    protected var uiStateMutable: MutableLiveData<UiStateComponentModel> =
        MutableLiveData(UiStateComponentModel(loading = true))
    var uiStateLiveData: LiveData<UiStateComponentModel> = uiStateMutable

    fun fetchResponse() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = provider.getData()
                uiStateMutable.postValue(UiStateComponentModel(response))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}