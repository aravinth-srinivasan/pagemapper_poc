package com.raweng.pagemapper.pagemappersdk.domain.dfep

import androidx.compose.runtime.Immutable
import com.raweng.dfe.models.schedule.DFEScheduleModel

@Immutable
data class DFEPScoreCardListModel(
    val list: List<DFEScheduleModel>,
    val index: Int
)