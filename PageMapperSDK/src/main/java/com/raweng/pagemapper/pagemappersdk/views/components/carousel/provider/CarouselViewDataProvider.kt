package com.raweng.pagemapper.pagemappersdk.views.components.carousel.provider

import android.util.Log
import com.raweng.dfe_components_android.components.herocardcarousel.model.HeroCardCarouselDataModel
import com.raweng.pagemapper.pagemappersdk.dependency.cmsreference.ComponentCMSIncludeReferenceDependency
import com.raweng.pagemapper.pagemappersdk.data.provider.base.BaseProvider
import com.raweng.pagemapper.pagemappersdk.data.repository.cmsentry.CMSEntryRepository
import com.raweng.pagemapper.pagemappersdk.data.repository.dfe.feeds.DFEFeedsRepository
import com.raweng.pagemapper.pagemappersdk.data.usecase.base.CMSBaseUseCase
import com.raweng.pagemapper.pagemappersdk.data.usecase.feeds.DFEFeedUseCase
import com.raweng.pagemapper.pagemappersdk.domain.ResponseDataModel
import com.raweng.pagemapper.pagemappersdk.domain.dependency.InternalComponentDependency
import com.raweng.pagemapper.pagemappersdk.type.Components
import com.raweng.pagemapper.pagemappersdk.type.ContentType
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.CarouselViewResponse
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.domain.DFEFeedDataModel
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.extension.toCarousalItem
import com.raweng.pagemapper.pagemappersdk.views.components.carousel.mapper.CarouselMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class CarouselViewDataProvider(
    private val dependency: InternalComponentDependency
) : BaseProvider(dependency) {

    private val mapper = CarouselMapper(dependency)
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
            item = dependency.item,
            apiResponse = data.second,
            cmsResponse = data.first,
            convertedData = getConvertedData(data)
        )
    }

    private fun getConvertedData(data: Pair<CarouselViewResponse?, DFEFeedDataModel>): HeroCardCarouselDataModel {
        return if (data.second.isWSCFeed) {
            data.second.wscFeeds.toCarousalItem(
                dependency,
                data.first,
                mapper.getPlaceholderManager()
            )
        } else {
            val feedType = DFEFeedsRepository.getFeedType(dependency.item.dfepDataSource)
            data.second.feeds.toCarousalItem(
                dependency,
                data.first,
                feedType,
                mapper.getPlaceholderManager()
            )
        }
    }

    private fun fetchCMSEntry(): ResponseDataModel {
        return getEmptyResponseDataModel()
    }


    private fun isWSCFeed(): Boolean {
        return (dependency.item.dfepDataSource?.lowercase()
            ?.equals(DFEFeedsRepository.WSC_FEEDS, true) == true)
    }

    private suspend fun getDFEData(): DFEFeedDataModel {
        val model = DFEFeedDataModel().apply {
            val dfeFeedRepository = DFEFeedsRepository()
            val useCase = DFEFeedUseCase(dfeFeedRepository)
            val limit = (dependency.item.dfepNoOfItems?.toInt() ?: 0)
            if (isWSCFeed()) {
                if (!dependency.dependency.gameId.isNullOrEmpty()) {
                    wscFeeds = useCase.execute(dependency.dependency.gameId.orEmpty(), limit)
                } else {
                    Log.e(
                        "CarouselViewDataProvider",
                        "getDFEData: unable execute useCase game id is null",
                    )
                }
            } else {
                feeds = useCase.execute(limit, dependency.item.dfepDataSource.orEmpty())
            }
            isWSCFeed = isWSCFeed()
        }
        return model
    }


    private suspend fun getCMSData(): CarouselViewResponse? {
        val cmsRepository = CMSEntryRepository<CarouselViewResponse>()
        val cmsUseCase =
            CMSBaseUseCase(
                dependency.item,
                cmsRepository,
                CarouselViewResponse::class.java
            )
        val includeReference =
            ComponentCMSIncludeReferenceDependency.getComponentCMSIncludeRef(Components.HERO_CARD_CAROUSEL_VIEW)
        return cmsUseCase.execute(includeReference, isNetworkOnly = true)
    }
}