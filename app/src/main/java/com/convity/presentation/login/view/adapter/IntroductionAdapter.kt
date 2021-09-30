package com.convity.presentation.login.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.convity.databinding.ItemIntroductionBinding


class IntroductionAdapter(var activity: Activity) : PagerAdapter() {
    override fun getCount(): Int {
        return 4
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val binding =
            ItemIntroductionBinding.inflate(LayoutInflater.from(activity), collection, false)

        collection.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}