package com.convity.presentation.home.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.convity.R
import com.convity.databinding.FragmentBusinessDetailBinding
import com.convity.presentation.base.BaseFragment
import com.convity.presentation.home.view.adapter.MemberAdapter
import com.convity.presentation.home.view.adapter.PopularPostsAdapter
import com.convity.presentation.home.viewmodel.BusinessDetailFragmentViewModel
import com.convity.presentation.register.view.RegisterActivity
import com.convity.utility.setOnSafeClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusinessDetailFragment : BaseFragment() {

    private var memberAdapter: MemberAdapter? = null
    private lateinit var binding: FragmentBusinessDetailBinding
    private val currentViewModel: BusinessDetailFragmentViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_business_detail, container, false)
        initListener()
        initAdapter()
        return binding.root
    }

    private fun initListener() {
        binding.businessAdd.setOnSafeClickListener {
            startActivity(Intent(requireContext(), RegisterActivity::class.java).putExtra("new", false))
        }
    }

    private fun initAdapter() {
        memberAdapter = MemberAdapter(requireActivity())
        binding.memberRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = memberAdapter
        }
    }

    companion object {

        fun newInstance() =
            BusinessDetailFragment()
    }
}