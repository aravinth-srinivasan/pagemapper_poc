package com.raweng.pagemapper.pagemappersdk.mapper.config

import com.raweng.pagemapper.pagemappersdk.PageMapperSDK
import com.raweng.pagemapper.pagemappersdk.domain.dfep.ConfigModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.MapperModel
import com.raweng.pagemapper.pagemappersdk.domain.dfep.PubNubModel
import com.raweng.pagemapper.pagemappersdk.utils.Utils.checkNullSafe
import org.json.JSONObject
import kotlin.reflect.KMutableProperty

class DFEConfigIntegrationMapper(
    private val integrations: String?,
    private val configModel: ConfigModel
) {
    private val data = JSONObject(integrations.orEmpty())

    init {
        prepareCMSIntegration()
        prepareNbaSetup()
        preparePubNub()
    }

    private fun prepareCMSIntegration() {
        if (data.has("contentstack")) {
            val jsonObject = data.optJSONObject("contentstack")
            jsonObject?.let {
                val fieldsToMap = listOf(
                    "app_key", "authorization", "environment",
                    "access_token", "delivery_token", "url", "mapper"
                )

                for (field in fieldsToMap) {
                    if (jsonObject.has(field)) {
                        when (field) {
                            "app_key" -> configModel.cmsModel.app_key =
                                checkNullSafe(jsonObject.optString(field).orEmpty())

                            "authorization" -> configModel.cmsModel.authorization =
                                checkNullSafe(jsonObject.getString(field).orEmpty())

                            "environment" -> configModel.cmsModel.environment =
                                checkNullSafe(jsonObject.getString(field).orEmpty())

                            "access_token" -> configModel.cmsModel.access_token =
                                checkNullSafe(jsonObject.getString(field).orEmpty())

                            "delivery_token" -> configModel.cmsModel.delivery_token =
                                checkNullSafe(jsonObject.getString(field).orEmpty())

                            "url" -> configModel.cmsModel.url =
                                checkNullSafe(jsonObject.getString(field).orEmpty())

                            "mapper" -> configModel.cmsModel.mapper =
                                onMapper(jsonObject.optJSONObject(field))
                        }
                    }
                }
            }
        }
    }

    private fun onMapper(jsonObject: JSONObject?): MapperModel {
        val mapperModel = MapperModel()

        jsonObject?.let {
            val fieldMappings = mapOf(
                "parking_direction" to mapperModel::parking_direction,
                "event_faqs" to mapperModel::event_faqs,
                "game_guide" to mapperModel::game_guide,
                "arena_policies" to mapperModel::arena_policies,
                "menu4" to mapperModel::menu4,
                "featured_feeds" to mapperModel::featured_feeds,
                "get_there" to mapperModel::get_there,
                "config" to mapperModel::config,
                "segmentation_home_screen" to mapperModel::segmentation_home_screen,
                "component_sponsor" to mapperModel::component_sponsor,
                "ticket_screen_offers" to mapperModel::ticket_screen_offers,
                "food_beverage" to mapperModel::food_beverage,
                "contenthub_feeds" to mapperModel::contenthub_feeds,
                "arenahub_feeds" to mapperModel::arenahub_feeds,
                "menu" to mapperModel::menu,
                "hero_feeds" to mapperModel::hero_feeds,
                "stories" to mapperModel::stories,
                "stories_v2" to mapperModel::stories_v2,
                "mini_series" to mapperModel::mini_series,
                "r_splash" to mapperModel::r_splash,
                "r_on_boarding" to mapperModel::r_on_boarding,
                "r_tickets_screen" to mapperModel::r_tickets_screen,
                "r_menu" to mapperModel::r_menu,
                "r_horizontal_card_list" to mapperModel::r_horizontal_card_list,
                "r_game_theme" to mapperModel::r_game_theme,
                "r_user_category" to mapperModel::r_user_category,
                "r_sponsors" to mapperModel::r_sponsors,
                "r_know_before_you_accordion" to mapperModel::r_know_before_you_accordion
            )

            for ((key, property) in fieldMappings) {
                if (jsonObject.has(key)) {
                    val fieldValue = checkNullSafe(jsonObject.optString(key))
                    val setter = (property as KMutableProperty<*>).setter
                    setter.call(fieldValue)
                }
            }
        }

        return mapperModel
    }

    private fun prepareNbaSetup() {
        integrations?.takeIf { it.isNotEmpty() }?.let {
            data.optJSONObject("nba")?.let { nbaData ->
                configModel.nbaModel.action_url = checkNullSafe(nbaData.optString("action_url"))
                configModel.nbaModel.headshot_url = checkNullSafe(nbaData.optString("headshot_url"))
                configModel.nbaModel.league_id = checkNullSafe(nbaData.optString("league_id"))
                configModel.nbaModel.season_id = checkNullSafe(nbaData.optString("season_id"))
                configModel.nbaModel.team_id = checkNullSafe(nbaData.optString("team_id"))
                configModel.nbaModel.team_logo_url =
                    checkNullSafe(nbaData.optString("team_logo_url"))
                configModel.nbaModel.team_name = checkNullSafe(nbaData.optString("team_name"))
                configModel.nbaModel.year = nbaData.optInt("year")
            }
        }
    }

    private fun preparePubNub() {
        if (data.has("pubnub")) {
            configModel.pubnub = onPubNub(data.getJSONObject("pubnub"))
        }
    }

    private fun onPubNub(jsonObject: JSONObject?): PubNubModel {
        val pubNubModel = PubNubModel()
        if (jsonObject != null) {
            if (jsonObject.has("subscribe_key")) {
                pubNubModel.subscribe_key = checkNullSafe(jsonObject.getString("subscribe_key"))
                PageMapperSDK.PUBNUB_SUB_KEY = pubNubModel.publish_key
            }
            if (jsonObject.has("publish_key")) {
                pubNubModel.publish_key = checkNullSafe(jsonObject.getString("publish_key"))
            }
            if (jsonObject.has("waittime_publish_key")) {
                pubNubModel.waittime_publish_key =
                    checkNullSafe(jsonObject.getString("waittime_publish_key"))
            }
            if (jsonObject.has("waittime_subscribe_key")) {
                pubNubModel.waittime_subscribe_key =
                    checkNullSafe(jsonObject.getString("waittime_subscribe_key"))
            }
        }
        return pubNubModel
    }
}