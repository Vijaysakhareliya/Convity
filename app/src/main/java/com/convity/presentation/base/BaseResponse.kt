package com.convity.presentation.base

data class BaseResponse(
    var error: String? = "",
    var message: String? = "",
    var success: Boolean? = false
)