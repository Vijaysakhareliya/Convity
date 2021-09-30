package com.convity.presentation.verification.model


data class VerificationOtpResponse(
    val data: Data? = null,
    val success: Boolean = false,
    val message: String? = null,
)

data class Data(
    val token: String? = null,
    val isProfileUpdated: String? = null,
)