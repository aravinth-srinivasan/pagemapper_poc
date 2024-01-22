package com.raweng.pagemapper.pagemappersdk.data.api.base

class Error : Exception {

    var errorType: ErrorType
    override var message: String

    constructor(mErrorType: ErrorType) {
        errorType = mErrorType
        message = ""
    }

    constructor(mErrorType: ErrorType, mMessage: String) {
        errorType = mErrorType
        message = mMessage
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val error = other as Error
        return errorType == error.errorType &&
                message == error.message
    }

    override fun hashCode(): Int {
        var result = errorType.hashCode()
        result = 31 * result + message.hashCode()
        return result
    }
}