package com.example.win26.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.win26.fragments.HomeFragment
import com.example.win26.fragments.SettingFragment
import com.example.win26.fragments.StatisticsFragment

class AdapterViewPager(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                StatisticsFragment()
            }
            2 -> {
                SettingFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}