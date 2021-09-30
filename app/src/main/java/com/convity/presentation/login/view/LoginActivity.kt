package com.convity.presentation.login.view

import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.convity.R
import com.convity.databinding.ActivityLoginBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.country.view.activity.CountryPickerActivity
import com.convity.presentation.login.view.adapter.IntroductionAdapter
import com.convity.presentation.login.viewmodel.LoginViewModel
import com.convity.presentation.verification.view.VerificationActivity
import com.convity.utility.setOnSafeClickListener
import com.convity.utility.toast
import com.mukesh.countrypicker.Country
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private var code: String? = null
    private var country: String? = null
    private var alpha2Code: String? = null
    private lateinit var binding: ActivityLoginBinding

    private val currentViewModel: LoginViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        countryFromSIM
        initObserver()
        initAdapter()
        initClick()
    }

    private fun initObserver() {
        currentViewModel.sendOtpResponse.observe(this) {
            if (it.success) {
                toast(it.message!!)
                startActivity(
                    Intent(this, VerificationActivity::class.java).putExtra(
                        "code",
                        it.data!!.otp
                    ).putExtra("number", binding.number.text.toString()).putExtra("ccode", code)
                )
            } else {
                toast(it.message!!)
            }
        }
    }

    private val countryFromSIM: Unit
        get() {
            try {
                val countryAndCode: String
                val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                countryAndCode = telephonyManager.simCountryIso
                if (!TextUtils.isEmpty(countryAndCode)) {
                    setCountryAndCode(countryAndCode)
                } else {
                    countryFromNetwork
                }
            } catch (e: Exception) {
            }
        }

    private val countryFromNetwork: Unit
        get() {
            Thread {
                val result = ""
                if (result == "") {
                    countryFromLocal
                } else {
                    setCountryAndCode(result)
                }
            }.start()
        }
    private val countryFromLocal: Unit
        get() {
            val countryAndCode: String = resources.configuration.locale.country
            if (!TextUtils.isEmpty(countryAndCode)) setCountryAndCode(countryAndCode) else setDefaultCountry()
        }

    private fun setDefaultCountry() {
        runOnUiThread {
            binding.countryCode.text = "+91"
            binding.countryFlag.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.flag_in
                )
            )
        }
        country = "india"
        code = "+91"
        alpha2Code = "IN"
    }

    private fun setCountryAndCode(countryAndCode: String) {
        val countryObject = Country.getCountryByISO(countryAndCode)
        if (countryObject != null) {
            runOnUiThread {
                binding.countryCode.text = countryObject.dialCode
                binding.countryFlag.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        countryObject.flag
                    )
                )

                country = countryObject.name
                alpha2Code = countryObject.code
                code = countryObject.dialCode
            }
        } else setDefaultCountry()
    }

    private fun initClick() {
        binding.loginContinue.setOnSafeClickListener {
            val number = binding.number.text.toString()
            if (number.isEmpty()) {
                toast(resources.getString(R.string.pleaseEnterNumber))
                return@setOnSafeClickListener
            }
            if (number.length != 10) {
                toast(resources.getString(R.string.pleaseEnterValidNumber))
                return@setOnSafeClickListener
            }
            currentViewModel.sendOtp(code = code!!, number = number)
        }
        binding.countryPick.setOnSafeClickListener {
            startActivityForResult(Intent(this, CountryPickerActivity::class.java), 100)
        }
    }

    private fun initAdapter() {
        binding.introductionViewPager.adapter = IntroductionAdapter(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {

            if (requestCode == 100) {
                alpha2Code = data!!.getStringExtra("alpha2Code")
                country = data.getStringExtra("country")
                code = data.getStringExtra("codeC")
                binding.countryCode.text = code

                val countrySelected = Country.getAllCountries().find { it.dialCode == code }
                if (countrySelected != null) {
                    binding.countryFlag.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            countrySelected.flag
                        )
                    )
                }
            }

        }
    }
}