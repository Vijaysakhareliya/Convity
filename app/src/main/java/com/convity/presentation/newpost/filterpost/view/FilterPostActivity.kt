package com.convity.presentation.newpost.filterpost.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.convity.R
import com.convity.databinding.ActivityFilterPostBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.newpost.createpost.view.CreatePostActivity
import com.convity.presentation.newpost.filterpost.view.adapter.FilterAdapter
import com.convity.presentation.newpost.filterpost.viewmodel.FilterPostViewModel
import com.convity.utility.setOnSafeClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterPostActivity : BaseActivity() {

    private var path: String? = null
    private lateinit var binding: ActivityFilterPostBinding
    private val currentViewModel: FilterPostViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_post)
        path = intent.getStringExtra("path")
        initData()
        initClick()
        initAdapter()
    }

    private fun initData() {
        Glide.with(this).load(path).into(binding.filterImage)
    }

    private fun initAdapter() {
        val filterAdapter = FilterAdapter(this)
        binding.filterRecycler.apply {
            layoutManager =
                LinearLayoutManager(this@FilterPostActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = filterAdapter
        }
    }

    private fun initClick() {
        binding.backFilter.setOnSafeClickListener {
            onBackPressed()
        }
        binding.nextFilter.setOnSafeClickListener {
            startActivity(Intent(this, CreatePostActivity::class.java))
        }
    }
}