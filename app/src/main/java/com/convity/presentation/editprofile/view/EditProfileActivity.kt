package com.convity.presentation.editprofile.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.convity.R
import com.convity.databinding.ActivityEditProfileBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.editprofile.viewmodel.EditProfileViewModel
import com.convity.utility.setOnSafeClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private val currentViewModel: EditProfileViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        initClick()
    }

    private fun initClick() {
        binding.backEdit.setOnSafeClickListener {
            finish()
        }
    }
}