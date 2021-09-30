package com.convity.presentation.home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.convity.R
import com.convity.databinding.FragmentSearchBinding
import com.convity.presentation.base.BaseFragment
import com.convity.presentation.home.view.adapter.CategoryAdapter
import com.convity.presentation.home.view.adapter.OtherPostAdapter
import com.convity.presentation.home.view.adapter.TrendingAdapter
import com.convity.presentation.home.viewmodel.SearchFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {

    private var otherPostAdapter: OtherPostAdapter? = null
    private var trendingAdapter: TrendingAdapter? = null
    private var categoryAdapter: CategoryAdapter? = null
    private lateinit var binding: FragmentSearchBinding

    private val currentViewModel: SearchFragmentViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        categoryAdapter = CategoryAdapter(requireActivity())
        binding.categoryRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        trendingAdapter = TrendingAdapter(requireActivity())
        binding.trendingRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }


        otherPostAdapter = OtherPostAdapter(requireActivity())
        val layoutManagers = GridLayoutManager(requireContext(), 12, RecyclerView.VERTICAL, false)
        layoutManagers.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return return if (position <= 2) {
                    4
                } else {
                    3
                }
            }
        }
        binding.otherRecycler.apply {
            layoutManager = layoutManagers
            adapter = otherPostAdapter
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}