package com.convity.presentation.home.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.convity.R
import com.convity.databinding.FragmentLanguageBottomSheetDialogBinding
import com.convity.presentation.home.view.adapter.LanguageAdapter
import com.convity.utility.setOnSafeClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class LanguageBottomSheetDialog : BottomSheetDialogFragment() {

    private var languageAdapter: LanguageAdapter? = null
    private lateinit var binding: FragmentLanguageBottomSheetDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_language_bottom_sheet_dialog,
            container,
            false
        )
        initListener()
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        languageAdapter = LanguageAdapter(requireActivity())
        binding.languageRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = languageAdapter
        }

    }

    private fun initListener() {
        binding.closeDialog.setOnSafeClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogs = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialogs.setOnShowListener {
            val bottomSheet: FrameLayout =
                dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialogs
    }

    companion object {
        fun newInstance() = LanguageBottomSheetDialog()
    }
}