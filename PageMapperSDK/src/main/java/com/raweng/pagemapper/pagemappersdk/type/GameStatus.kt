package com.raweng.pagemapper.pagemappersdk.type

enum class GameStatus(private val value: Int) {
    FUTURE_GAME(1), LIVE_GAME(2), PAST_GAME(3);
    companion object {
        fun fromValue(value: Int): GameStatus? =
            GameStatus.entries.find {
                it.value == value
            }
    }
}