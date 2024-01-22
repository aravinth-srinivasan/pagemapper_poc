package com.raweng.pagemapper.pagemappersdk.domain.dfep

data class DFERequest(
    var fields: String? = null,
    var year: Int,
    var leagueId: String?,
    var seasonId: String? = null,
    var sortBy: String? = "",
    var sortOrder: String? = "",
    var skip: Int = -1,
    var limit: Int = -1,
    var teamId: String? = null
)