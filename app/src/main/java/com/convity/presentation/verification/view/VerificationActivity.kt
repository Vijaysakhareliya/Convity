package com.convity.presentation.verification.view

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.databinding.DataBindingUtil
import com.convity.R
import com.convity.databinding.ActivityVerificationBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.login.view.adapter.IntroductionAdapter
import com.convity.presentation.verification.viewmodel.VerificationViewModel
import com.convity.utility.PreferenceStore
import com.convity.utility.setOnSafeClickListener
import com.convity.utility.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class VerificationActivity : BaseActivity() {

    private lateinit var binding: ActivityVerificationBinding

    private val currentViewModel: VerificationViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel
    var code = ""
    var number = ""
    var countryCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verification)
        code = intent.getStringExtra("code")!!
        number = intent.getStringExtra("number")!!
        countryCode = intent.getStringExtra("ccode")!!
        setTimmer()
        initObserver()
        initAdapter()
        initClick()
    }

    private fun setTimmer() {
        binding.resend.isEnabled = false
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.resend.text =
                    resources.getString(R.string.otpNotReceive) + "Resend in " + String.format(
                        "%02d",
                        millisUntilFinished / 1000
                    )
            }

            override fun onFinish() {
                binding.resend.isEnabled = true
                binding.resend.text =
                    resources.getString(R.string.otpNotReceive) + "Resend"
            }
        }.start()
    }

    private fun initObserver() {
        currentViewModel.verifyOtpResponse.observe(this) {
            if (it.success) {
                toast(it.message!!)
                PreferenceStore.prefs.token!!.put(it.data!!.token)
                PreferenceStore.prefs.isLogin!!.put(true)
                startActivity(
                    Intent(
                        this@VerificationActivity,
                        VerificationSuccessActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
            } else {
                toast(it.message!!)
            }
        }

        currentViewModel.sendOtpResponse.observe(this) {
            if (it.success) {
                toast(it.message!!)
            } else {
                toast(it.message!!)
            }
        }
    }

    private fun initClick() {
        binding.otpView.otpListener = (object : OTPListener {
            override fun onInteractionListener() {
                // fired when user types something in the Otpbox
            }

            override fun onOTPComplete(otp: String) {
                currentViewModel.verifyOtp(otp, number)
            }
        })

        binding.resend.setOnSafeClickListener {
            setTimmer()
            currentViewModel.sendOtp(countryCode, number)
        }

        binding.diffNumber.setOnSafeClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        binding.introductionViewPager.adapter = IntroductionAdapter(this)
    }
}