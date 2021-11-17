package com.example.aiedudemo.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.aiedudemo.fragments.TabEXPFragment
import com.example.aiedudemo.fragments.TabStudyFragment

class TabAdapter(fm: FragmentManager, private val context: Context): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> { return TabStudyFragment() }
            else ->  { return TabEXPFragment() }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> {
                "tab_01"
            }
            else ->  {
                "tab_02"
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }
}