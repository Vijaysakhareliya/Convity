package com.convity.presentation.home.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.convity.presentation.home.view.fragment.HomeFragment
import com.convity.presentation.home.view.fragment.NotificationFragment
import com.convity.presentation.home.view.fragment.ProfileFragment
import com.convity.presentation.home.view.fragment.SearchFragment

class HomePagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> SearchFragment.newInstance()
            2 -> NotificationFragment.newInstance()
            3 -> ProfileFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Home"
            1 -> "Search"
            2 -> "Notification"
            3 -> "Profile"
            else -> "Home"
        }
    }
}