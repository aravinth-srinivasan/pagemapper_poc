package com.raweng.pagemapper.pagemappersdk.domain.dfep

import com.raweng.dfe.models.schedule.DFEScheduleModel
import com.raweng.dfe.modules.policy.RequestType
import com.raweng.pagemapper.pagemappersdk.data.api.DFEApiManager
import com.raweng.pagemapper.pagemappersdk.data.repository.dfe.schedule.IDFEScheduleRepository

class DFEScheduleRepository : IDFEScheduleRepository {
    override suspend fun fetchScheduleList(
        request: DFERequest,
        fromDate: String?,
        toDate: String?,
        requestType: RequestType
    ): List<DFEScheduleModel> {
        return DFEApiManager.fetchScheduleList(
            request, fromDate, toDate, requestType
        )
    }
}