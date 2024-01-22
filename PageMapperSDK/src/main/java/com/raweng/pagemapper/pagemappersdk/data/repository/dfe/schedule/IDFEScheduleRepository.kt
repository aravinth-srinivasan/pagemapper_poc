package com.raweng.pagemapper.pagemappersdk.data.repository.dfe.schedule

import com.raweng.dfe.models.schedule.DFEScheduleModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.pagemapper.pagemappersdk.domain.dfep.DFERequest

interface IDFEScheduleRepository {
    suspend fun fetchScheduleList(
        request: DFERequest,
        fromDate: String? = "",
        toDate: String? = "",
        requestType: RequestType
    ): List<DFEScheduleModel>
}