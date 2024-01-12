package com.raweng.pagemapper.pagemappersdk.mapper

import com.raweng.dfe.models.config.DFEConfigModel
import com.raweng.pagemapper.pagemappersdk.domain.CMSModel
import com.raweng.pagemapper.pagemappersdk.domain.ConfigModel
import com.raweng.pagemapper.pagemappersdk.domain.MapperModel
import com.raweng.pagemapper.pagemappersdk.domain.NbaModel
import org.json.JSONObject
import kotlin.reflect.KMutableProperty

class DFEConfigMapper(private val config: DFEConfigModel) {
    private val configModel = ConfigModel()

    init {
        prepareCMSIntegration()
        prepareNbaSetup()
    }

    fun getCMSModel(): CMSModel {
        return configModel.cmsModel
    }

    fun getNBAModel(): NbaModel {
        return configModel.nbaModel
    }

    private fun prepareCMSIntegration() {
        val data = JSONObject(config.integrations)
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
                    (property as KMutableProperty<*>).setter.call(mapperModel, fieldValue)
                }
            }
        }

        return mapperModel
    }

    private fun prepareNbaSetup() {
        config.integrations?.takeIf { it.isNotEmpty() }?.let { integrations ->
            val data = JSONObject(integrations)
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


    private fun checkNullSafe(data: String?): String {
        return data?.takeUnless { it.equals("null", true) } ?: ""
    }
}