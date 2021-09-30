package com.convity.presentation.home.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.convity.R
import com.convity.databinding.FragmentProfileBinding
import com.convity.presentation.base.BaseFragment
import com.convity.presentation.editprofile.view.EditProfileActivity
import com.convity.presentation.home.view.adapter.PopularPostsAdapter
import com.convity.presentation.home.view.adapter.ProfilePagerAdapter
import com.convity.presentation.home.viewmodel.ProfileFragmentViewModel
import com.convity.utility.setOnSafeClickListener
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    private var popularPostAdapter: PopularPostsAdapter? = null
    private lateinit var binding: FragmentProfileBinding

    private val currentViewModel: ProfileFragmentViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        initListener()
        initAdapter()
        return binding.root
    }

    private fun initListener() {
        binding.myPostTab.setOnSafeClickListener {
            setClick(binding.myPostTab, binding.savedPostTab, binding.businessDetailTab)
            binding.profileViewPager.currentItem = 0
        }
        binding.savedPostTab.setOnSafeClickListener {
            setClick(binding.savedPostTab, binding.myPostTab, binding.businessDetailTab)
            binding.profileViewPager.currentItem = 1
        }
        binding.businessDetailTab.setOnSafeClickListener {
            setClick(binding.businessDetailTab, binding.savedPostTab, binding.myPostTab)
            binding.profileViewPager.currentItem = 2
        }
        binding.editProfile.setOnSafeClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->

        })
    }

    private fun setClick(
        tabOne: AppCompatTextView,
        tabTwo: AppCompatTextView,
        tabThree: AppCompatTextView
    ) {
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.selected_tab)
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        tabOne.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        tabTwo.setTextColor(ContextCompat.getColor(requireContext(), R.color.unSelectTab))
        tabThree.setTextColor(ContextCompat.getColor(requireContext(), R.color.unSelectTab))
    }

    private fun initAdapter() {
        popularPostAdapter = PopularPostsAdapter(requireActivity())
        binding.popularRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularPostAdapter
        }

        val profilePagerAdapter = ProfilePagerAdapter(requireActivity().supportFragmentManager)
        binding.profileViewPager.adapter = profilePagerAdapter
        binding.profileViewPager.setSwipePagingEnabled(false)
        binding.profileViewPager.offscreenPageLimit = 3
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}