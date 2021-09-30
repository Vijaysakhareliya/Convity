package com.convity.presentation.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.convity.domain.network.ApiServices
import com.convity.presentation.base.BaseViewModel
import com.convity.presentation.login.model.SendOtpResponse
import kotlinx.coroutines.launch

class LoginViewModel(var apiServices: ApiServices) : BaseViewModel() {

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
}