package com.raweng.pagemapper.pagemappersdk.views.carousel.provider

import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.cmsreference.ComponentCMSIncludeReference
import com.raweng.pagemapper.pagemappersdk.data.provider.base.BaseProvider
import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.CMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.data.repository.dfe.feeds.DFEFeedsRepository
import com.raweng.pagemapper.pagemappersdk.data.usecase.base.CMSBaseUseCase
import com.raweng.pagemapper.pagemappersdk.data.usecase.feeds.DFEFeedUseCase
import com.raweng.pagemapper.pagemappersdk.domain.cms.DynamicScreenResponse
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.type.ContentType
import com.raweng.pagemapper.pagemappersdk.views.carousel.domain.CarouselViewResponse
import com.raweng.pagemapper.pagemappersdk.views.carousel.domain.DFEFeedDataModel
import com.raweng.pagemapper.pagemappersdk.views.carousel.extension.toCarousalItem
import com.raweng.pagemapper.pagemappersdk.views.carousel.mapper.CarouselMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class CarouselViewDataProvider(
    private val item: DynamicScreenResponse.Component,
    private val gameId: String,
) : BaseProvider(item) {

    private val mapper = CarouselMapper()
    override suspend fun getData(): ResponseDataModel {
        val contentType = getContentType()
        return contentType?.let {
            when (it) {
                ContentType.DFEP -> fetchDFEEntry()
                ContentType.CONTENT_STACK -> fetchCMSEntry()
            }
        } ?: getEmptyResponseDataModel()
    }

    fun getCarouselMapper(): CarouselMapper {
        return mapper
    }

    private suspend fun fetchDFEEntry(): ResponseDataModel {
        val job = CoroutineScope(Dispatchers.IO + Job()).async {
            Pair(
                async {
                    getCMSData()
                }.await(),
                async {
                    getDFEData()
                }.await()
            )
        }
        val data = job.await()
        val response = getResponseDataModel(data)
        mapper.setResponseDataModel(response)
        try {
            job.cancel()
        } catch (e: Exception) {
            e.toString()
        }
        return response
    }

    private fun getResponseDataModel(data: Pair<CarouselViewResponse?, DFEFeedDataModel>): ResponseDataModel {
        return ResponseDataModel(
            item = item,
            apiResponse = data.second,
            cmsResponse = data.first,
            convertedData = getConvertedData(data)
        )
    }

    private fun getConvertedData(data: Pair<CarouselViewResponse?, DFEFeedDataModel>): HeroCardCarouselDataModel {
        return if (data.second.isWSCFeed) {
            data.second.wscFeeds.toCarousalItem(data.first)
        } else {
            val feedType = DFEFeedsRepository.getFeedType(item.dfepDataSource)
            data.second.feeds.toCarousalItem(data.first, feedType)
        }
    }

    private fun fetchCMSEntry(): ResponseDataModel {
        return getEmptyResponseDataModel()
    }


    private fun isWSCFeed(): Boolean {
        return (item.dfepDataSource?.lowercase()
            ?.equals(DFEFeedsRepository.WSC_FEEDS, true) == true)
    }

    private suspend fun getDFEData(): DFEFeedDataModel {
        val model = DFEFeedDataModel().apply {
            val dfeFeedRepository = DFEFeedsRepository()
            val useCase = DFEFeedUseCase(dfeFeedRepository)
            val limit = (item.dfepNoOfItems?.toInt() ?: 0)
            if (isWSCFeed()) {
                wscFeeds = useCase.execute(gameId, limit)
            } else {
                feeds = useCase.execute(limit, item.dfepDataSource.orEmpty())
            }
            isWSCFeed = isWSCFeed()
        }
        return model
    }


    private suspend fun getCMSData(): CarouselViewResponse? {
        val cmsRepository = CMSEntryRepository<CarouselViewResponse>()
        val cmsUseCase =
            CMSBaseUseCase(item, cmsRepository, CarouselViewResponse::class.java)
        val includeReference =
            ComponentCMSIncludeReference.getComponentCMSIncludeRef(Components.HERO_CARD_CAROUSEL_VIEW)
        return cmsUseCase.execute(includeReference, isNetworkOnly = true)
    }
}