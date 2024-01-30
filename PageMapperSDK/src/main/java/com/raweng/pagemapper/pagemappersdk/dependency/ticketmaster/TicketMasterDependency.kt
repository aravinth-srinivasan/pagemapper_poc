package com.raweng.pagemapper.pagemappersdk.dependency.ticketmaster

internal object TicketMasterDependency {

    private lateinit var ticketMasterId: String

    fun getTicketMasterId(): String? {
        if (this::ticketMasterId.isInitialized) {
            return ticketMasterId
        }
        return null
    }

    fun setTicketMasterId(ticketMasterId: String?) {
        if (!ticketMasterId.isNullOrEmpty()) {
            this.ticketMasterId = ticketMasterId
        }
        this.ticketMasterId = ""
    }
}