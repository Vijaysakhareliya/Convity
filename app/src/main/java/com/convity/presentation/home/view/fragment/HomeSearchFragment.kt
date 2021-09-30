package com.convity.presentation.home.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.convity.R
import com.convity.databinding.FragmentHomeSearchBinding
import com.convity.presentation.base.BaseFragment
import com.convity.presentation.home.view.adapter.SearchAdapter
import com.convity.presentation.home.viewmodel.HomeSearchFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeSearchFragment : BaseFragment() {

    private var searchAdapter: SearchAdapter? = null
    private lateinit var binding: FragmentHomeSearchBinding

    private val currentViewModel: HomeSearchFragmentViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_search, container, false)
        binding.searchText.requestFocus()
        val imgr = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)  as InputMethodManager
        imgr.showSoftInput(binding.searchText, InputMethodManager.SHOW_IMPLICIT)
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter(requireActivity())
        binding.searchRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

    companion object {
        fun newInstance() = HomeSearchFragment()
    }
}