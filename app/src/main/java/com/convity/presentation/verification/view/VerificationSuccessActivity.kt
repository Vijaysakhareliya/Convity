package com.convity.presentation.verification.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.convity.R
import com.convity.databinding.ActivityVerificationSuccessBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.register.view.RegisterActivity
import com.convity.presentation.verification.viewmodel.VerificationSuccessViewModel
import com.convity.utility.setOnSafeClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerificationSuccessActivity : BaseActivity() {

    private lateinit var binding: ActivityVerificationSuccessBinding

    private val currentViewModel: VerificationSuccessViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verification_success)
        initClick()
    }

    private fun initClick() {
        binding.verificationContinue.setOnSafeClickListener {
            startActivity(Intent(this, RegisterActivity::class.java).putExtra("new", true))
        }
    }
}