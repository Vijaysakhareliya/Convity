package com.convity.presentation.home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.convity.R
import com.convity.databinding.FragmentHomeBinding
import com.convity.presentation.base.BaseFragment
import com.convity.presentation.home.view.adapter.FeedAdapter
import com.convity.presentation.home.view.dialog.LanguageBottomSheetDialog
import com.convity.presentation.home.viewmodel.HomeFragmentViewModel
import com.convity.utility.gone
import com.convity.utility.setOnSafeClickListener
import com.convity.utility.visible
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment() {


    private var feedAdapter: FeedAdapter? = null
    private lateinit var binding: FragmentHomeBinding

    private val currentViewModel: HomeFragmentViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel
    var isImage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initListener()
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        feedAdapter = FeedAdapter(requireActivity(), isImage)
        binding.feedRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = feedAdapter
        }
    }

    private fun initListener() {
        binding.switchFeed.setOnCheckedChangeListener { _, isChecked ->
            isImage = isChecked
            binding.feedRecycler.adapter!!.notifyDataSetChanged()
            if (isChecked) {
                binding.titleVideo.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                )
                binding.titlePhoto.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.headerColor
                    )
                )
            } else {
                binding.titleVideo.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.headerColor
                    )
                )
                binding.titlePhoto.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                )
            }
        }

        binding.homeHelp.setOnSafeClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.menu_help, popup.menu)
            popup.setOnMenuItemClickListener { its ->
                when (its.itemId) {
                    R.id.menuEmailSupport -> {

                    }
                    R.id.menuWhatsappSupport -> {

                    }
                    R.id.menuHelp -> {

                    }
                    R.id.menuPolicy -> {

                    }
                    R.id.menuAbout -> {

                    }
                    R.id.menuVersionDetail -> {

                    }
                    R.id.menuPrivacy -> {

                    }
                }
                true
            }
            popup.show()
        }
        binding.homeLanguage.setOnSafeClickListener {
            LanguageBottomSheetDialog.newInstance()
                .show(requireActivity().supportFragmentManager, "BOTTOM_DIALOG")
        }
        binding.searchOpen.setOnSafeClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.add(R.id.container, HomeSearchFragment.newInstance())
            transaction.addToBackStack("HomeSearchFragment")
            transaction.commit()
        }
        binding.homeCalender.setOnSafeClickListener {
            if (binding.containerCalender.calenderLayout.visibility == View.VISIBLE) {
                binding.containerCalender.calenderLayout.gone()
                binding.homeCalender.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_calendar
                    )
                )
            } else {
                binding.containerCalender.calenderLayout.visible()
                binding.homeCalender.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_calendar_active
                    )
                )
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}