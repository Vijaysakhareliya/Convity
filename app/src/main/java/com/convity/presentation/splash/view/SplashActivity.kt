package com.convity.presentation.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.convity.R
import com.convity.databinding.ActivitySplashBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.home.view.activity.HomeActivity
import com.convity.presentation.login.view.LoginActivity
import com.convity.presentation.splash.viewmodel.SplashViewModel
import com.convity.utility.PreferenceStore
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val currentViewModel: SplashViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            if (PreferenceStore.prefs.isLogin!!.get(false)) {
                startActivity(
                    Intent(
                        this,
                        HomeActivity::class.java
                    )
                )
            } else {
                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )
            }
            finish()
        }, 1500)
    }
}