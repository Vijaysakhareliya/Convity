package com.convity.presentation.newpost.createpost.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.convity.R
import com.convity.databinding.ActivityCreatePostBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.newpost.createpost.viewmodel.CreatePostViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePostActivity : BaseActivity() {
    private lateinit var binding: ActivityCreatePostBinding

    private val currentViewModel: CreatePostViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)
    }
}