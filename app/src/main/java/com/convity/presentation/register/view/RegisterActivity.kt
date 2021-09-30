package com.convity.presentation.register.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.convity.R
import com.convity.databinding.ActivityRegisterBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.home.view.activity.HomeActivity
import com.convity.presentation.register.viewmodel.RegisterViewModel
import com.convity.utility.setOnSafeClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val currentViewModel: RegisterViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        initClick()
    }

    private fun initClick() {
        binding.verificationContinue.setOnSafeClickListener {
            if (intent.getBooleanExtra("new", true)) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                finish()
            }
        }
    }
}