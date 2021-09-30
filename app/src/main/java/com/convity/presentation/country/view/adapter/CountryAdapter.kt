package com.convity.presentation.country.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.convity.databinding.ItemCountryBinding
import com.convity.utility.setOnSafeClickListener
import com.mukesh.countrypicker.Country

class CountryAdapter(
    var countryList: List<Country>,
    var activity: Activity
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(position, countryList[position])
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    inner class ViewHolder(var binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, country: Country) {
            binding.code.text = country.dialCode
            binding.country.text = country.name

            Glide.with(activity)
                .load(country.flag)
                .into(binding.ivFlag)

            binding.root.setOnSafeClickListener {
                val intent = Intent()
                intent.putExtra("country", country.name)
                intent.putExtra("codeC", country.dialCode)
                intent.putExtra(
                    "alpha2Code",
                    country.code
                )
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()
            }
        }

    }

}