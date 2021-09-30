package com.convity.domain.network

import com.convity.presentation.login.model.SendOtpResponse
import com.convity.presentation.verification.model.VerificationOtpResponse
import com.convity.utility.ApiConstant
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiServices {
    @POST(ApiConstant.sendOtp)
    suspend fun sendOtpApi(
        @Body params: Map<String, String>,
    ): SendOtpResponse

    @POST(ApiConstant.verifyOtp)
    suspend fun verifyOtpApi(
        @Body params: Map<String, String>,
    ): VerificationOtpResponse
}