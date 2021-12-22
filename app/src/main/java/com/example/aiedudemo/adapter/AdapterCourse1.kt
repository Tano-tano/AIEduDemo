package com.example.aiedudemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aiedudemo.fragments.Course1.c1p1
import com.example.aiedudemo.fragments.Course1.c1p2
import com.example.aiedudemo.fragments.Course1.c1p3

class AdapterCourse1(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    // ページ数を取得
    override fun getItemCount(): Int = 3

    // スワイプ位置によって表示するFragmentを変更
    override fun createFragment(position: Int): Fragment =
        when(position) {
            0 -> {
                c1p1()
            }
            1 -> {
                c1p2()
            }
            2 -> {
                c1p3()
            }
            else -> {
                c1p1()
            }
        }
}