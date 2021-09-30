package com.convity.presentation.home.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.convity.presentation.home.view.fragment.BusinessDetailFragment
import com.convity.presentation.home.view.fragment.MyPostFragment
import com.convity.presentation.home.view.fragment.SavedPostFragment

class ProfilePagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyPostFragment.newInstance()
            1 -> SavedPostFragment.newInstance()
            2 -> BusinessDetailFragment.newInstance()
            else -> MyPostFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "My Post"
            1 -> "Saved Post"
            2 -> "Business Detail"
            else -> "My Post"
        }
    }
}