package com.convity.presentation.home.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.convity.databinding.ItemNotificationBinding

class NotificationAdapter(var activity: Activity) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNotificationBinding.inflate(
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