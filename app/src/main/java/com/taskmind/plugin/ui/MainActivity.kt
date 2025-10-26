package com.taskmind.plugin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.taskmind.plugin.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.view_pager)

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> TimelineFragment()
                    1 -> ActionsFragment()
                    else -> PredictionsFragment()
                }
            }
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Timeline"
                1 -> "Actions"
                else -> "Predictions"
            }
        }.attach()
    }
}
