package com.raweng.pagemapper.pagemappersdk.domain.dfep

class ConfigModel {
    var cmsModel: CMSModel = CMSModel()
    var nbaModel: NbaModel = NbaModel()
    var pubnub = PubNubModel()
}


class CMSModel {
    var app_key: String = ""
    var authorization: String = ""
    var environment: String = ""
    var url: String = ""
    var access_token: String = ""
    var delivery_token: String = ""
    var mapper = MapperModel()
}

class MapperModel {
    var parking_direction: String = ""
    var event_faqs: String = ""
    var game_guide: String = ""
    var arena_policies: String = ""
    var get_there: String = ""
    var menu4: String = ""
    var featured_feeds: String = ""
    var config: String = ""
    var component_sponsor: String = ""
    var ticket_screen_offers: String = ""
    var food_beverage: String = ""
    var contenthub_feeds: String = ""
    var arenahub_feeds: String = ""
    var menu: String = ""
    var hero_feeds: String = ""
    var stories: String = ""
    var stories_v2: String = ""
    var mini_series: String = ""
    var r_splash: String = ""
    var r_on_boarding: String = ""
    var r_tickets_screen: String = ""
    var r_menu: String = ""
    var r_horizontal_card_list: String = ""
    var r_user_category: String = ""
    var r_game_theme: String = ""
    var r_sponsors: String = ""
    var r_know_before_you_accordion: String = ""
    var segmentation_home_screen: String = ""
}


class NbaModel(
    var action_url: String = "",
    var headshot_url: String = "",
    var league_id: String = "",
    var season_id: String = "",
    var team_id: String = "",
    var team_logo_url: String = "",
    var team_name: String = "",
    var year: Int = 0
)

class PubNubModel {
    var subscribe_key: String = ""
    var publish_key: String = ""
    var waittime_publish_key: String = ""
    var waittime_subscribe_key: String = ""
}