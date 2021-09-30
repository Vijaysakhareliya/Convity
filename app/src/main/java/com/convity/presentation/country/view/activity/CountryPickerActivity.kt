package com.convity.presentation.country.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.convity.R
import com.convity.databinding.ActivityCountryPickerBinding
import com.convity.presentation.country.view.adapter.CountryAdapter
import com.mukesh.countrypicker.Country
import java.util.*
import java.util.regex.Pattern
import kotlin.Comparator

class CountryPickerActivity : AppCompatActivity() {
    private lateinit var mAdapter: CountryAdapter
    private lateinit var binding: ActivityCountryPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_country_picker)
        sortList()
        initAdapter()
        initListener()
    }

    private fun initListener() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                val search: MutableList<Country> =
                    ArrayList()
                try {
                    val temp = binding.searchBar.text.toString()
                        .replace(Pattern.quote("+").toRegex(), "")
                    for (e: Country in Country.getAllCountries()) {
                        if (e.dialCode.startsWith("+$temp")) {
                            search.add(e)
                        }
                        if (e.name.trim { it <= ' ' }.startsWith(temp) || e.name
                                .trim { it <= ' ' }.toLowerCase(Locale.getDefault())
                                .startsWith(temp)
                        ) {
                            search.add(e)
                        }
                    }
                } catch (w: Exception) {
                    for (e: Country in Country.getAllCountries()) {
                        Log.d(e.name, e.dialCode)
                        if (e.name
                                .startsWith(
                                    binding.searchBar.text.toString().toLowerCase(Locale.getDefault())
                                )
                        ) {
                            search.add(e)
                        }
                    }
                }
                if (search.size == 0 && s.toString() != "") {
                    binding.noCountryData.visibility = View.VISIBLE
                } else {
                    binding.noCountryData.visibility = View.GONE
                }
                if ((s.toString() == "")) {
                    mAdapter = CountryAdapter(
                        Country.getAllCountries(),
                        this@CountryPickerActivity
                    )
                    binding.noCountryData.visibility = View.GONE
                } else {
                    mAdapter = CountryAdapter(
                        search,
                        this@CountryPickerActivity
                    )
                }
                binding.recyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun initAdapter() {
        mAdapter = CountryAdapter(
            Country.getAllCountries(),
            this
        )
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(applicationContext)
        binding.recyclerView.apply {
            layoutManager = mLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
    }

    private fun sortList() {
        Country.getAllCountries().sortWith(Comparator { country, country2 ->
            country.name.trim { it <= ' ' }
                .compareTo(country2.name.trim { it <= ' ' }, ignoreCase = true)
        })
    }
}