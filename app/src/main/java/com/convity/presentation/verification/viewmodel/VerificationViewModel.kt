package com.convity.presentation.verification.viewmodel

import androidx.lifecycle.MutableLiveData
import com.convity.domain.network.ApiServices
import com.convity.presentation.base.BaseViewModel
import com.convity.presentation.login.model.SendOtpResponse
import com.convity.presentation.verification.model.VerificationOtpResponse
import kotlinx.coroutines.launch

class VerificationViewModel(var apiServices: ApiServices) : BaseViewModel() {

    val verifyOtpResponse: MutableLiveData<VerificationOtpResponse> = MutableLiveData()
    val sendOtpResponse: MutableLiveData<SendOtpResponse> = MutableLiveData()

    fun sendOtp(code: String, number: String) {
        val map = HashMap<String, String>()
        map["countryCode"] = code
        map["phone"] = number
        launch {
            kotlin.runCatching {
                apiServices.sendOtpApi(map)
            }.fold(
                {
                    sendOtpResponse.postValue(it)
                }, {

                }
            )
        }
    }

    fun verifyOtp(otp: String, number: String) {
        val map = HashMap<String, String>()
        map["otp"] = otp
        map["phone"] = number
        launch {
            kotlin.runCatching {
                apiServices.verifyOtpApi(map)
            }.fold({
                verifyOtpResponse.postValue(it)
            }, {

            })
        }
    }
}