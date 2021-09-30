package com.convity.presentation.login.model

data class SendOtpResponse(
    val data: Data? = null,
    val success: Boolean = false,
    val message: String? = null,
)

data class Data(
    val otp: String? = null
)