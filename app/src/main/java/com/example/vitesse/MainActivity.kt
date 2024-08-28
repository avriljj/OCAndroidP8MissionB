package com.example.vitesse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vitesse.databinding.ActivityMainBinding
import com.example.vitesse.ui.candidat.AllCandidatesFragment
import com.example.vitesse.ui.favorite.FavoriteCandidatesFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the ViewPager with the fragments
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        // Link the TabLayout with the ViewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "All"
                1 -> "Favorites"
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }.attach()
    }

    private inner class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AllCandidatesFragment()
                1 -> FavoriteCandidatesFragment()
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}
