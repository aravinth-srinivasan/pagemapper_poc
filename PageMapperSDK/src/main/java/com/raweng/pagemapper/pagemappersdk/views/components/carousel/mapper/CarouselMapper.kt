package com.raweng.pagemapper.pagemappersdk.views.components.carousel.mapper

import com.raweng.dfe.models.playbyplay.DFEPlayByPlayModel
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.CarouselViewResponse
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.extension.toCarousalItem

class CarouselMapper {
    private lateinit var responseDataModel: ResponseDataModel
    private var initialItemSize = 0
    private var newItemSize = 0

    fun getResponseDataModel(): ResponseDataModel {
        return responseDataModel
    }

    fun setResponseDataModel(responseDataModel: ResponseDataModel) {
        val convertedData = (responseDataModel.convertedData as HeroCardCarouselDataModel)
        initialItemSize = convertedData.itemList?.size ?: 0
        this.responseDataModel = responseDataModel
    }

    private fun updateResponseDataModel(responseDataModel: ResponseDataModel) {
        val convertedData = (responseDataModel.convertedData as HeroCardCarouselDataModel)
        newItemSize = convertedData.itemList?.size ?: 0
        this.responseDataModel = responseDataModel
    }

    fun setLiveResponse(data: List<DFEPlayByPlayModel>): ResponseDataModel? {
        if (this::responseDataModel.isInitialized) {
            val convertedData = (responseDataModel.convertedData as HeroCardCarouselDataModel)
            val convertedDataItems = convertedData.itemList ?: listOf()
            val items = data.filter { pbp ->
                pbp.event_clips.isNotEmpty() && pbp.uid !in convertedDataItems.map { it.uid }
            }

            val dfepNoOfItems = responseDataModel.item?.dfepNoOfItems?.toInt() ?: 0
            if (items.isNotEmpty() && responseDataModel.cmsResponse != null) {
                val cmsResponse = (responseDataModel.cmsResponse as CarouselViewResponse)
                val updatedData = items.take(dfepNoOfItems).toCarousalItem(cmsResponse)
                val updatedResponseModel = responseDataModel.copy(convertedData = updatedData)
                updateResponseDataModel(updatedResponseModel)
                return updatedResponseModel
            }
        }

        return null
    }

    fun removeItemsSize(): Int {
        if (this::responseDataModel.isInitialized) {
            val maxSize = responseDataModel.item?.dfepNoOfItems?.let {
                val item = if (it.isNotEmpty()) {
                    it.toInt()
                } else {
                    0
                }
                item
            } ?: 0
            return ((initialItemSize + newItemSize) - maxSize)
        }
        return 0
    }
}