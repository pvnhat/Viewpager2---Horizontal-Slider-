package com.vannhat.recyclerslidetabdemo

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vannhat.recyclerslidetabdemo.slide.GroupFragment
import com.vannhat.recyclerslidetabdemo.utils.SeeThroughTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val pagerAdapter = ViewPagerAdapter(this)
        pagerAdapter.apply {
            fragments.add(Pair(GroupFragment(), getString(R.string.group)))
            fragments.add(Pair(FragmentTest1(), getString(R.string.test, 1)))
            fragments.add(Pair(FragmentTest2(), getString(R.string.test, 2)))
            fragments.add(Pair(FragmentTest3(), getString(R.string.test, 3)))
            fragments.add(Pair(FragmentTest4(), getString(R.string.test, 4)))
            fragments.add(Pair(FragmentTest5(), getString(R.string.test, 5)))
        }


        vp_content.adapter = pagerAdapter
        TabLayoutMediator(tl_tab, vp_content) { tab, position ->
            tab.text = pagerAdapter.fragments[position].second
        }.attach()

        tl_tab.getTabAt(tl_tab.selectedTabPosition)!!.customView = null
        tl_tab.getTabAt(tl_tab.selectedTabPosition)!!.customView =
            getTabLabel(pagerAdapter.fragments[tl_tab.selectedTabPosition].second)
        tl_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView = null
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab!!.customView = getTabLabel(pagerAdapter.fragments[tab.position].second)
            }

        })
    }

    private fun getTabLabel(labelText: String) = (LayoutInflater.from(this@MainActivity).inflate(
        R.layout.layout_text,
        null
    ) as SeeThroughTextView).apply {
        text = labelText
    }


    fun setSwipeableViewPager(swipeable: Boolean) {
        vp_content.isUserInputEnabled = swipeable
    }
}
