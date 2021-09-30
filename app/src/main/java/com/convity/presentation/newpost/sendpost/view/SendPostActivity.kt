package com.convity.presentation.newpost.sendpost.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.convity.R
import com.convity.databinding.ActivitySendPostBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.newpost.sendpost.viewmodel.SendPostViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendPostActivity : BaseActivity() {

    private lateinit var binding: ActivitySendPostBinding
    private val currentViewModel: SendPostViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_post)
    }
}