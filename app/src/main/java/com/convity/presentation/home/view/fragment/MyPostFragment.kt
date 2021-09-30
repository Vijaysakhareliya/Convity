package com.convity.presentation.home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.convity.R
import com.convity.databinding.FragmentMyPostBinding
import com.convity.presentation.base.BaseFragment
import com.convity.presentation.home.view.adapter.PostAdapter
import com.convity.presentation.home.viewmodel.MyPostFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPostFragment : BaseFragment() {

    private var postAdapter: PostAdapter? = null
    private lateinit var binding: FragmentMyPostBinding
    private val currentViewModel: MyPostFragmentViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_post, container, false)
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        postAdapter = PostAdapter(requireActivity())
        binding.myPostRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
            adapter = postAdapter
        }
    }

    companion object {
        fun newInstance() = MyPostFragment()
    }
}