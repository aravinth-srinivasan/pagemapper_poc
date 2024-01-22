package com.raweng.pagemapper.pagemappersdk.utils

import com.raweng.dfe.models.schedule.DFEScheduleModel
import com.raweng.pagemapper.pagemappersdk.type.GameStatus
import java.util.Calendar

internal object GameUtils {

    private fun isGameDay(time: String?): Boolean {
        if (!time.isNullOrEmpty()) {
            val currentTime = Calendar.getInstance()
            val timeStamp = time.toLong()
            val receivedTime = Calendar.getInstance()
            receivedTime.timeInMillis = timeStamp
            return receivedTime[Calendar.YEAR] == currentTime[Calendar.YEAR] &&
                    receivedTime[Calendar.MONTH] == currentTime[Calendar.MONTH] &&
                    receivedTime[Calendar.DAY_OF_MONTH] == currentTime[Calendar.DAY_OF_MONTH]
        }
        return false
    }

    fun isTodayScheduledGame(gameStatus: Int, gameTime: String): Boolean {
        return (gameStatus == 2 || gameStatus == 3) && isGameDay(gameTime)
    }

    fun getGameStatus(data: DFEScheduleModel?): GameStatus? {
        return GameStatus.fromValue(data?.gameStatus ?: 0)
    }

}