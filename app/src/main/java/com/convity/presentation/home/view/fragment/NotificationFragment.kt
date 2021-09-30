package com.convity.presentation.home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.convity.R
import com.convity.databinding.FragmentNotificationBinding
import com.convity.presentation.base.BaseFragment
import com.convity.presentation.home.view.adapter.NotificationAdapter
import com.convity.presentation.home.viewmodel.NotificationFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : BaseFragment() {

    private var notificationAdapter: NotificationAdapter? = null
    private lateinit var binding: FragmentNotificationBinding

    private val currentViewModel: NotificationFragmentViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        notificationAdapter = NotificationAdapter(requireActivity())
        binding.notificationRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationAdapter
        }
    }

    companion object {
        fun newInstance() = NotificationFragment()
    }
}