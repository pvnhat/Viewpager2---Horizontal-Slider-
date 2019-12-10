package com.vannhat.recyclerslidetabdemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter constructor(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    val fragments = mutableListOf<Pair<Fragment, String>>()
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].first
    }


}
