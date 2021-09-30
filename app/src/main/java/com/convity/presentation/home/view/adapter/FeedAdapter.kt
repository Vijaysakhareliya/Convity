package com.convity.presentation.home.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.convity.databinding.ItemFeedBinding
import com.convity.utility.gone
import com.convity.utility.visible

class FeedAdapter(var activity: Activity, var isImage: Boolean) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if (isImage) {
                binding.playImage.visible()
                binding.musicName.visible()
            } else {
                binding.playImage.gone()
                binding.musicName.gone()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 10
    }
}